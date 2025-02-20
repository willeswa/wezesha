package com.wanjala.wezesha.ui.screens.productdetail

import PriceDisplay
import ReviewItem
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.wanjala.wezesha.R
import com.wanjala.wezesha.ui.screens.Screen
import kotlinx.coroutines.launch

@Composable
fun ProductDetail(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    productId: Int,
    viewModel: ProductDetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.productDetailUiState.collectAsState()
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        scope.launch {
            viewModel.getProductDetail(productId)
        }
    }

    when (uiState) {
        is ProductDetailViewModel.ProductDetailUiState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        is ProductDetailViewModel.ProductDetailUiState.Success -> {
            val productDetail =
                (uiState as ProductDetailViewModel.ProductDetailUiState.Success).productDetail

           Box(modifier = modifier.fillMaxWidth().padding(vertical = 16.dp)) {
               LazyColumn(modifier = modifier.fillMaxSize().padding(8.dp)) {
                   item {
                       Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically){
                           IconButton(
                               onClick = {
                                   scope.launch {
                                       navHostController.popBackStack()
                                   }
                               }
                           ) {
                               Icon(
                                   imageVector = Icons.Default.ArrowBack,
                                   contentDescription = null,
                                   modifier = modifier.padding(8.dp)
                               )
                           }
                           Button(
                               onClick = {scope.launch {
                                   navHostController.popBackStack()
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
                   }
                   item {
                       LazyRow(
                           modifier = modifier
                               .height(350.dp)
                               .fillMaxWidth(),
                           verticalAlignment = Alignment.CenterVertically,
                           horizontalArrangement = Arrangement.spacedBy(8.dp)
                       ) {
                           items(productDetail.images) { image ->
                               AsyncImage(model = ImageRequest.Builder(LocalContext.current)
                                   .data(image).crossfade(true).build(), contentDescription = null, placeholder = painterResource(
                                   R.drawable.no_image)
                               )
                           }
                       }
                   }
                   item {
                       Column (modifier = modifier.fillMaxWidth()){
                           Row (horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically, modifier = modifier.fillMaxWidth()){
                               Text(
                                   text = productDetail.title,
                                   modifier = modifier.padding(end = 32.dp).weight(1f),
                                   fontSize = 22.sp
                               )
                               PriceDisplay(price = productDetail.price.toString(), fontSize = 48.sp)
                           }
                           Spacer(modifier = modifier.padding(16.dp))
                           Text(
                               text = productDetail.description
                           )

                       }
                   }


                   item {
                       Text(text = "Review", modifier = modifier.padding(top = 16.dp),   fontWeight = FontWeight.Bold,)
                   }

                   items(productDetail.reviews) { review ->
                       ReviewItem(
                           reviewerName = review.reviewerName,
                           date = review.date,
                           rating = review.rating,
                           text = review.text
                       )
                   }
                   item {
                       Spacer(modifier = modifier.padding(20.dp))
                   }

               }

                   Button(
                       onClick = {},
                       modifier = modifier.align(Alignment.BottomCenter).fillMaxWidth()
                   ) {
                       Text(text = "Buy Now")
                   }

           }
        }

        is ProductDetailViewModel.ProductDetailUiState.Error -> {
            val errorMessage =
                (uiState as ProductDetailViewModel.ProductDetailUiState.Error).message
            // Show error message
            Box(modifier = Modifier.fillMaxSize().padding(32.dp), contentAlignment = Alignment.Center){
                Text(text = errorMessage, fontWeight = FontWeight.Bold, color = Color.Gray)
            }
        }

    }
}