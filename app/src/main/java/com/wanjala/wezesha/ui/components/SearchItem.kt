package com.wanjala.wezesha.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.wanjala.wezesha.ui.screens.Screen
import kotlinx.coroutines.launch

@Composable
fun SearchItem(
    modifier: Modifier = Modifier,
    image: String,
    title: String,
    productId: Int,
    navHostController: NavHostController
){
    val scope = rememberCoroutineScope()

    Card(modifier = modifier.height(60.dp).padding(4.dp), onClick = {
        scope.launch {
            navHostController.navigate("${Screen.ProductDetailScreen.route}/${productId}")
        }
    }) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box (modifier = modifier.background(color = Color.Transparent, shape = CircleShape).size(50.dp).padding(8.dp)){
                AsyncImage(model = ImageRequest.Builder(LocalContext.current).data(image).build(), contentDescription = null)
            }
            Text(text = title, modifier = modifier.weight(1f))
        }
    }
}