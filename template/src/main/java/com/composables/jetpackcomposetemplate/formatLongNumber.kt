package co.composables.jetpackcomposetemplate

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import java.text.DecimalFormat

@Composable
fun formatLongNumber(number: Long): String {
    val formatter = remember { DecimalFormat("#,###") }
    return formatter.format(number)
}