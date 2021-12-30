package com.app.freshworkstudio.ui.uiActivity

import android.annotation.SuppressLint
import android.os.Bundle
import android.transition.Slide
import android.view.Gravity
import android.view.Window
import androidx.databinding.ViewDataBinding
import com.app.freshworkstudio.utils.DataUtils
import com.google.android.material.snackbar.Snackbar
import com.skydoves.bindables.BindingActivity


/**
 * Base activity class used to remove duplicate code from main fragment.
 *
 * Use this to write your common code related to ui
 * */
open class BaseActivity<T : ViewDataBinding>(rLayout: Int) : BindingActivity<T>(rLayout) {


    override fun onCreate(savedInstanceState: Bundle?) {
        with(window) {
            requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS)
            // set an start animation transition


            enterTransition = Slide(Gravity.RIGHT).apply {
                excludeTarget(android.R.id.statusBarBackground, true);
                excludeTarget(android.R.id.navigationBarBackground, true);
            }
            // set an exit transition
            exitTransition = Slide(Gravity.LEFT).apply {
                excludeTarget(android.R.id.statusBarBackground, true);
                excludeTarget(android.R.id.navigationBarBackground, true);
            }
        }
        super.onCreate(savedInstanceState)
    }

    fun showError(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    }

    @SuppressLint("MissingSuperCall")
    override fun onDestroy() {
        super.onDestroy()
        DataUtils.condition = ""
    }

}