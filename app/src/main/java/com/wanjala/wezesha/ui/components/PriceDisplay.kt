import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.TextUnit

@Composable
fun PriceDisplay(
    modifier: Modifier = Modifier,
    fontSize: TextUnit = 30.sp,
    price: String
) {
    val wholeNumber by rememberSaveable {
        mutableStateOf(price.substringBefore("."))
    }

    val cents by rememberSaveable {
        mutableStateOf(price.substringAfter("."))
    }

    var columnHeight by remember { mutableStateOf(0.dp) }
    val density = LocalDensity.current

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.Bottom
    ) {
        Text(
            text = wholeNumber,
            style = TextStyle(
                fontSize = fontSize,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier
                .padding(end = 4.dp)
                .heightIn(min = columnHeight)
        )

        Column(
            modifier = Modifier
                .padding(bottom = 8.dp)
                .onGloballyPositioned { coordinates ->
                    columnHeight = with(density) { coordinates.size.height.toDp() }
                }
        ) {
            Text(
                text = ".$cents",
                style = TextStyle(
                    fontSize = fontSize*.3,
                    fontWeight = FontWeight.Normal
                )
            )
            Text(
                text = "KES",
                style = TextStyle(
                    fontSize = fontSize*.25,
                    fontWeight = FontWeight.Normal
                )
            )
        }
    }
}