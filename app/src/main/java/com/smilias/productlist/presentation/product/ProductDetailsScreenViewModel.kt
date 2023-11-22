package com.smilias.productlist.presentation.product

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smilias.productlist.domain.usecase.GetProductUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailsScreenViewModel @Inject constructor(
    private val getProductUseCase: GetProductUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state: MutableStateFlow<ProductDetailsScreenState> = MutableStateFlow(ProductDetailsScreenState())
    val state: StateFlow<ProductDetailsScreenState> = _state

    init {
        viewModelScope.launch {
            savedStateHandle.get<Long>("id")?.let { id ->
                _state.value = state.value.copy(product = getProductUseCase(id))
             }
        }
    }


}