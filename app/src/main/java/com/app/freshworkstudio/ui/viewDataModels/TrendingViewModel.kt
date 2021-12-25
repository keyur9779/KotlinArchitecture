package com.app.freshworkstudio.ui.viewDataModels

import androidx.databinding.Bindable
import androidx.lifecycle.viewModelScope
import com.app.freshworkstudio.data.repository.GiphyTrendingRepository
import com.app.freshworkstudio.model.GiphyResponseModel
import com.skydoves.bindables.BindingViewModel
import com.skydoves.bindables.asBindingProperty
import com.skydoves.bindables.bindingProperty
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@HiltViewModel
class TrendingViewModel @Inject constructor(val giphyTrendingRepository: GiphyTrendingRepository) :
    BindingViewModel() {

    @get:Bindable
    var isLoading: Boolean by bindingProperty(false)
        private set

    private val moviePageStateFlow: MutableStateFlow<Int> = MutableStateFlow(1)
    private val movieListFlow = moviePageStateFlow.flatMapLatest {
        isLoading = true
        giphyTrendingRepository.loadTrendingGif(it) {
            isLoading = false
        }
    }

    @get:Bindable
    val gifList: GiphyResponseModel by movieListFlow.asBindingProperty(
        viewModelScope,
        GiphyResponseModel()
    )

    fun postMoviePage(page: Int) = moviePageStateFlow.tryEmit(page)
}