package com.wanjala.wezesha.ui.screens.productsearch

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.wanjala.wezesha.ui.components.SearchItem
import kotlinx.coroutines.launch

@Composable
fun SearchProduct(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    viewModel: SearchProductViewModel = hiltViewModel()
) {
    val uiState by viewModel.productSearchUiState.collectAsState()
    var query by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    val focusReqester = remember { FocusRequester() }

    val focusManager = LocalFocusManager.current

    LaunchedEffect(Unit) {
        scope.launch {
            focusReqester.requestFocus()
        }
    }

    LaunchedEffect(query) {
        if (query.isNotEmpty()) {
            scope.launch {
                viewModel.searchProducts(query)
            }
        }
    }



    Column(modifier = modifier.padding(top = 32.dp, start = 8.dp, end = 8.dp, bottom = 16.dp)) {
        OutlinedTextField(
            value = query,
            onValueChange = { query = it },
            placeholder = { Text("Search Products") },
            modifier = modifier
                .fillMaxWidth()
                .focusRequester(focusReqester),
            shape = CircleShape,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    focusManager.clearFocus()
                    scope.launch {
                        viewModel.searchProducts(query)
                    }
                }
            )
        )
        when (uiState) {
            is SearchProductViewModel.ProductSearchUiState.Idle -> {

            }

            is SearchProductViewModel.ProductSearchUiState.Loading -> {
                Text(text = "Loading...")
            }

            is SearchProductViewModel.ProductSearchUiState.Success -> {
                val products =
                    (uiState as SearchProductViewModel.ProductSearchUiState.Success).products
                if (query.isNotEmpty()) {
                    LazyColumn(modifier = modifier.padding(top = 16.dp)) {
                        items(products) { product ->
                            SearchItem(
                                image = product.thumbnail,
                                title = product.title,
                                productId = product.id,
                                navHostController = navHostController
                            )
                        }
                    }
                }
            }

            is SearchProductViewModel.ProductSearchUiState.Error -> {
                Text(text = "Error: ${(uiState as SearchProductViewModel.ProductSearchUiState.Error).message}")
            }
        }
    }
}