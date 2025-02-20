package com.wanjala.wezesha.ui.screens.productdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wanjala.wezesha.data.models.ProductDetail
import com.wanjala.wezesha.data.repositories.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(private val productRepository: ProductRepository): ViewModel() {
    private val _productDetailUiState = MutableStateFlow<ProductDetailUiState>(ProductDetailUiState.Loading)
    val productDetailUiState: StateFlow<ProductDetailUiState> = _productDetailUiState

    sealed class ProductDetailUiState {
        data object Loading : ProductDetailUiState()
        data class Success(val productDetail: ProductDetail) : ProductDetailUiState()
        data class Error(val message: String) : ProductDetailUiState()
    }

    fun getProductDetail(productId: Int) {
        viewModelScope.launch {
            productRepository.getProductDetail(productId)
                .collectLatest { result ->
                    when (result) {
                        is ProductRepository.Result.Loading -> {
                            _productDetailUiState.value = ProductDetailUiState.Loading
                        }

                        is ProductRepository.Result.Success -> {
                            _productDetailUiState.value = ProductDetailUiState.Success(result.data)

                        }

                        is ProductRepository.Result.Error -> {
                            _productDetailUiState.value = ProductDetailUiState.Error(
                                result.exception.message ?: "Unknown error"
                            )
                        }
                    }
                }

        }
    }
}