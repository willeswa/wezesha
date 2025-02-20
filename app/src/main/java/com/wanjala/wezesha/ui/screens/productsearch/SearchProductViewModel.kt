package com.wanjala.wezesha.ui.screens.productsearch

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
class SearchProductViewModel @Inject constructor(private val repository: ProductRepository): ViewModel() {

    private val _productSearchUiState = MutableStateFlow<ProductSearchUiState>(ProductSearchUiState.Idle)
    val productSearchUiState: StateFlow<ProductSearchUiState> = _productSearchUiState

    sealed class ProductSearchUiState {
        data object Loading : ProductSearchUiState()
        data class Success(val products: List<ProductItem>) : ProductSearchUiState()
        data class Error(val message: String) : ProductSearchUiState()
        data object Idle: ProductSearchUiState()
    }

    fun searchProducts(query: String) {
        viewModelScope.launch {
            repository.searchProducts(query)
                .collectLatest { result ->
                    when (result) {
                        is ProductRepository.Result.Loading -> {
                            _productSearchUiState.value = ProductSearchUiState.Loading
                        }
                        is ProductRepository.Result.Success -> {
                            _productSearchUiState.value = ProductSearchUiState.Success(result.data)
                        }
                        is ProductRepository.Result.Error -> {
                            _productSearchUiState.value = ProductSearchUiState.Error(result.exception.message ?: "Unknown error")
                        }
                    }
                }
        }
    }

}