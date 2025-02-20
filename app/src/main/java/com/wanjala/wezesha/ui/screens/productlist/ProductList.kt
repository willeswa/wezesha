package com.wanjala.wezesha.ui.screens.productlist

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.wanjala.wezesha.ui.components.ProductListItem
import com.wanjala.wezesha.ui.screens.Screen
import kotlinx.coroutines.launch

@Composable
fun ProductListScreen(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    viewModel: ProductListViewModel = hiltViewModel()
){
    val uiState by viewModel.productListUiState.collectAsState()
    val scope = rememberCoroutineScope()

    Column (modifier = modifier.padding(horizontal = 8.dp, vertical = 16.dp)){
        when(uiState){
            is ProductListViewModel.ProductListUiState.Loading -> {
                // Show loading indicator
               Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                   CircularProgressIndicator()
               }
            }

            is ProductListViewModel.ProductListUiState.Success -> {
                val products = (uiState as ProductListViewModel.ProductListUiState.Success).products
                // Display the list of products
                Column(modifier = modifier.padding(top = 16.dp)) {
                    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically){
                        Button(
                            onClick = {scope.launch {
                                navHostController.navigate(Screen.SearchProductScreen.route){
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }},
                            modifier = modifier.weight(1f),
                            border = BorderStroke(width = 1.dp, color = Color.Black.copy(alpha = 0.2f)),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.White,
                                contentColor = Color.Black.copy(0.3f)
                            )
                        ) {
                            Text(text = "Find Products")
                        }
                    }
                    LazyVerticalGrid (columns = GridCells.Fixed(2), modifier = Modifier.fillMaxSize()){
                        items(products){product ->
                            ProductListItem(
                                title = product.title,
                                thumbnail = product.thumbnail,
                                price = product.price.toString()
                            ) {
                                navHostController.navigate("${Screen.ProductDetailScreen.route}/${product.id}")
                            }
                        }
                    }

                }
            }
            is ProductListViewModel.ProductListUiState.Error -> {
                val errorMessage = (uiState as ProductListViewModel.ProductListUiState.Error).message
                // Show error message
                Box(modifier = Modifier.fillMaxSize().padding(32.dp), contentAlignment = Alignment.Center){
                    Text(text = errorMessage, fontWeight = FontWeight.Bold, color = Color.Gray)
                }
            }
        }
    }
}