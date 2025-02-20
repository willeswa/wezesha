package com.wanjala.wezesha.ui.screens.productlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wanjala.wezesha.data.models.ProductItem
import com.wanjala.wezesha.data.repositories.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductListViewModel @Inject constructor(
    private val productRepository: ProductRepository
): ViewModel() {

    private val _productListUiState = MutableStateFlow<ProductListUiState>(ProductListUiState.Loading)
    val productListUiState: StateFlow<ProductListUiState> = _productListUiState

    fun loadProducts() {
        viewModelScope.launch {
            productRepository.getProducts()
                .collectLatest { result ->
                    when (result){
                        is ProductRepository.Result.Loading -> {
                            _productListUiState.value = ProductListUiState.Loading
                        }
                        is ProductRepository.Result.Success -> {
                            _productListUiState.value = ProductListUiState.Success(result.data)

                        }
                        is ProductRepository.Result.Error -> {
                            _productListUiState.value = ProductListUiState.Error(result.exception.message ?: "Unknown error")
                        }
                    }
                }
        }
    }

    sealed class ProductListUiState {
        data object Loading : ProductListUiState()
        data class Success(val products: List<ProductItem>) : ProductListUiState()
        data class Error(val message: String) : ProductListUiState()
    }

    init {
        loadProducts()
    }
}