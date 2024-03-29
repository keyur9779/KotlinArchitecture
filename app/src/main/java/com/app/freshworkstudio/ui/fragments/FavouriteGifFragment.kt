package com.app.freshworkstudio.ui.fragments

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.app.freshworkstudio.R
import com.app.freshworkstudio.databinding.FragmentFavBinding
import com.app.freshworkstudio.model.entity.GifFavourite
import com.app.freshworkstudio.ui.adapter.GifFavListAdapter
import com.app.freshworkstudio.ui.viewDataModels.FavouriteGifViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 *  fragment containing a recyclerview to load all gif from local room.
 */
@AndroidEntryPoint
class FavouriteGifFragment : BaseFragment<FragmentFavBinding>(R.layout.fragment_fav) {


    // initialized view model
    private val vm: FavouriteGifViewModel by viewModels()

    // initialized adapter while getting used first time
    private val gifListAdapter: GifFavListAdapter by lazy {
        GifFavListAdapter(onAdapterPositionClicked())
    }

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle? ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        return binding {
            adapter = gifListAdapter
            viewModel = vm
        }.root
    }

    /*
    * when configuration chagnes we should not re create the fragment , just handle the ui changes
    * */
    override fun onConfigurationChanged(newConfig: Configuration) {
        onConfigurationChanged(newConfig, binding.recyclerView.layoutManager as GridLayoutManager)
        super.onConfigurationChanged(newConfig)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vm.localGifList.observe(viewLifecycleOwner) {
            binding.errorText.visibility = if (it.isEmpty()) VISIBLE else GONE
            gifListAdapter.addGif(it)
        }
    }

    /*
    *  callback to save item in db for fav.
    * */
    private fun onAdapterPositionClicked(): (Any) -> Unit {
        return { vm.deleteItem(it as GifFavourite) }
    }

    companion object {
        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        @JvmStatic
        fun newInstance(): FavouriteGifFragment {
            return FavouriteGifFragment()
        }
    }
}