package com.example.themoviedb.presentation.trending_screen

import android.view.Gravity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import coil.request.ImageRequest
import com.example.themoviedb.R

@Composable
fun TrendingItem(title: String, posterPath: String?, backgroundPath: String?){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .height(300.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
    ) {
        Box(modifier = Modifier
            .fillMaxSize()
        ) {
            SubcomposeAsyncImage(
                modifier = Modifier
                    .fillMaxSize(),
                model = ImageRequest.Builder(LocalContext.current)
                    .data("https://image.tmdb.org/t/p/w780$backgroundPath")
                    .crossfade(true)
                    .build(),
                contentDescription = stringResource(R.string.login),
                contentScale = ContentScale.Crop,
                colorFilter = ColorFilter.colorMatrix(ColorMatrix().apply{setToScale(0.5f,0.5f,0.5f,1f)}),
            )
            {
                val state = painter.state
                if (state is AsyncImagePainter.State.Error) {
                    Image(
                        modifier = Modifier
                            .fillMaxSize(),
                        painter = painterResource(id = R.drawable.no_image),
                        contentDescription = "",
                        contentScale = ContentScale.Crop,
                    )
                } else {
                    SubcomposeAsyncImageContent()
                }
            }
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .height(100.dp)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black
                            )
                        )
                    )
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                SubcomposeAsyncImage(
                    modifier = Modifier
                        .width(150.dp)
                        .weight(3.5f)
                        .clip(RoundedCornerShape(10.dp)),
                    model = ImageRequest.Builder(LocalContext.current)
                        .data("https://image.tmdb.org/t/p/w342$posterPath")
                        .crossfade(true)
                        .build(),
                    contentDescription = stringResource(R.string.login),
                    contentScale = ContentScale.Crop
                ) {
                    val state = painter.state
                    if (state is AsyncImagePainter.State.Error) {
                        Image(
                            modifier = Modifier
                                .width(150.dp)
                                .clip(RoundedCornerShape(10.dp)),
                            painter = painterResource(id = R.drawable.no_image),
                            contentDescription = "",
                            contentScale = ContentScale.Crop,
                        )
                    }
                    else if (state is AsyncImagePainter.State.Loading) {
                        CircularProgressIndicator()
                    }
                    else {
                        SubcomposeAsyncImageContent()
                    }
                }
                Box(modifier = Modifier
                    .fillMaxSize()
                    .weight(1f), contentAlignment = Alignment.Center){
                    val offset = Offset(4.0f, 5.0f)
                    Text(
                        modifier = Modifier.align(alignment = Alignment.Center),
                        text = title,
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        style = TextStyle(
                            shadow = Shadow(
                                color = Color.Black, offset = offset, blurRadius = 3f
                            )
                        )
                    )
                }
            }
        }
    }
}