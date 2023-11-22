package com.smilias.productlist.presentation.products

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smilias.productlist.common.Resource
import com.smilias.productlist.domain.usecase.GetProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductsScreenViewModel @Inject constructor(
    private val getProductsUseCase: GetProductsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(ProductsScreenState())
    val state: StateFlow<ProductsScreenState> = _state

    init {
        getProducts(false)
    }

    private fun getProducts(fromApi: Boolean) {
        viewModelScope.launch {
            getProductsUseCase(fromApi).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        result.data?.let {
                            _state.value =
                                state.value.copy(products = it, refreshing = false, error = null, isLoading = false)
                        }
                    }

                    is Resource.Error -> {
                        _state.value = state.value.copy(error = result.message, refreshing = false)
                    }

                    is Resource.Loading -> {
                        _state.value = state.value.copy(isLoading = true, error = null)
                    }
                }
            }
        }
    }

    fun refresh() {
        _state.value = _state.value.copy(refreshing = true)
        getProducts(true)
    }
}