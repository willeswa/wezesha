package com.wanjala.wezesha.ui.screens.productlist

import com.wanjala.wezesha.data.repositories.ProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import com.google.common.truth.Truth.assertThat
import com.wanjala.wezesha.data.models.ProductItem
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.mockito.Mockito.`when`

@OptIn(ExperimentalCoroutinesApi::class)
class ProductListViewModelTest {

    private lateinit var viewModel: ProductListViewModel
    private val repository: ProductRepository = mock(ProductRepository::class.java)
    private val testDispatcher = UnconfinedTestDispatcher()
    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = ProductListViewModel(repository)
    }

    @After
    fun tearDown(){
        Dispatchers.resetMain()
    }


    @Test
    fun `loadProducts - updates state with products lists when successful`() = runTest {
        val listOfProducts = listOf(
            ProductItem(1, title ="Product 1", thumbnail = "Description 1", price = 10.0f),
            ProductItem(2, title = "Product 2", thumbnail = "Description 2", price= 20.0f)
        )

    `when`(repository.getProducts()).thenReturn(flowOf(ProductRepository.Result.Success(listOfProducts)))

        viewModel.loadProducts()
        testDispatcher.scheduler.advanceUntilIdle()

        val uiState = viewModel.productListUiState.value
        assertThat(uiState).isInstanceOf(ProductListViewModel.ProductListUiState.Success::class.java)
        val successState = uiState as ProductListViewModel.ProductListUiState.Success
        assertThat(successState.products).isEqualTo(listOfProducts)

    }

}