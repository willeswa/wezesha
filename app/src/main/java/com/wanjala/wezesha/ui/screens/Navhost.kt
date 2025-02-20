package com.wanjala.wezesha.ui.screens

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.wanjala.wezesha.ui.screens.productdetail.ProductDetail
import com.wanjala.wezesha.ui.screens.productlist.ProductListScreen
import com.wanjala.wezesha.ui.screens.productsearch.SearchProduct

sealed class Screen(val route: String){
    data object ProductListScreen: Screen("product_list")
    data object ProductDetailScreen: Screen("product_details")
    data object SearchProductScreen: Screen("search_product")
}

@Composable
fun ApplicationHost(navHostController: NavHostController){
    NavHost(navController = navHostController, startDestination = "product_list") {
        composable(Screen.ProductListScreen.route) {
            ProductListScreen(navHostController = navHostController)
        }

        composable(Screen.ProductDetailScreen.route+"/{productId}") { backStackEntry ->
            val productId = backStackEntry.arguments?.getString("productId")?.toIntOrNull()
            productId?.let {
                ProductDetail(productId = it, navHostController = navHostController)
            }
        }

        composable(Screen.SearchProductScreen.route){
            SearchProduct(navHostController = navHostController)
        }

    }
}