package com.composables.jetpackcomposetemplate.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
fun ContentCard(
    modifier: Modifier = Modifier,
    title: @Composable () -> Unit = {},
    subtitle: @Composable () -> Unit = {},
    text: @Composable () -> Unit,
    onClick: () -> Unit,
    enabled: Boolean = true,
) {
    Card(modifier = modifier, enabled = enabled, onClick = onClick) {
        Column(Modifier.fillMaxSize()) {
            Column(Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                CompositionLocalProvider(LocalTextStyle provides MaterialTheme.typography.headlineLarge) {
                    title()
                }
                CompositionLocalProvider(LocalTextStyle provides MaterialTheme.typography.bodyLarge) {
                    subtitle()
                }
                Spacer(Modifier.height(8.dp))
                CompositionLocalProvider(LocalTextStyle provides MaterialTheme.typography.bodyLarge) {
                    text()
                }
            }
        }
    }
}

@Composable
fun ContentCard(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    enabled: Boolean = true,
    imageUrl: String,
    imageContentDescription: String?,
) {
    val context = LocalContext.current

    Card(modifier = modifier, enabled = enabled, onClick = onClick) {
        Column(Modifier.fillMaxWidth()) {
            AsyncImage(model = ImageRequest.Builder(context)
                .data(imageUrl)
                .crossfade(true)
                .build(),
                contentDescription = imageContentDescription,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .clip(MaterialTheme.shapes.medium)
                    .aspectRatio(16 / 9f))
        }
    }
}

@Composable
fun ContentCard(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    enabled: Boolean = true,
    imageUrl: String,
    imageContentDescription: String?,
    title: String,
    subtitle: String,
    maxLines: Int = Int.MAX_VALUE,
    textOverflow: TextOverflow = TextOverflow.Clip
) {
    val context = LocalContext.current

    Card(modifier = modifier, enabled = enabled, onClick = onClick) {
        Column(Modifier.fillMaxWidth()) {
            AsyncImage(model = ImageRequest.Builder(context)
                .data(imageUrl)
                .crossfade(true)
                .build(),
                contentDescription = imageContentDescription,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .clip(MaterialTheme.shapes.medium)
                    .aspectRatio(16 / 9f))
            Column(Modifier.padding(16.dp)) {
                Text(title,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    maxLines = maxLines,
                    overflow = textOverflow
                )
                Spacer(Modifier.height(8.dp))
                Text(subtitle, style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}

