package com.wanjala.wezesha.ui.components

import PriceDisplay
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.wanjala.wezesha.R

@Composable
fun ProductListItem(
    modifier: Modifier = Modifier,
    title: String,
    thumbnail: String,
    price: String,
    onClick: () -> Unit
) {

    Card(
        modifier = modifier
            .padding(4.dp)
            .fillMaxWidth()
            .height(265.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(8.dp),
        onClick = onClick
    ) {
        Column(
            modifier = modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                modifier = modifier.size(148.dp),
                contentScale = ContentScale.Crop,
                model = ImageRequest.Builder(LocalContext.current).crossfade(true).data(thumbnail).build(),
                contentDescription = null,
                placeholder = painterResource(
                    R.drawable.no_image
                )
            )
            Spacer(modifier = modifier.padding(4.dp))
            PriceDisplay(price = price, modifier = modifier.fillMaxWidth())
            Text(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                text = title,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                fontSize = 14.sp,
                fontWeight = FontWeight.Light
            )
        }
    }
}