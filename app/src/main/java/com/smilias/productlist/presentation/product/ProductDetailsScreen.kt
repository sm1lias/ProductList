package com.smilias.productlist.presentation.product

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.smilias.productlist.ui.theme.LocalSpacing

@Composable
fun ProductDetailsRoute(
    onShowSnackbar: suspend (String, String?) -> Boolean,
    modifier: Modifier = Modifier,
    viewModel: ProductDetailsScreenViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsState()
    ProductDetailsScreen(
        state = state,
        onShowSnackbar = onShowSnackbar,
        modifier = modifier
    )

}

@Composable
fun ProductDetailsScreen(
    state: State<ProductDetailsScreenState>,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    modifier: Modifier = Modifier,
) {
    state.value.product?.let {

        val dimens = LocalSpacing.current
        Box(modifier = modifier.fillMaxSize()) {
            Column {
                AsyncImage(
                    model = ImageRequest.Builder(
                        LocalContext.current
                    ).data(it.image)
                        .crossfade(100)
                        .build(),
                    contentScale = ContentScale.FillWidth,
                    contentDescription = it.name,
                    modifier = Modifier
                        .fillMaxWidth()
                )
                Column(modifier = modifier.padding(dimens.spaceSmall)) {
                    Row(
                        modifier = modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = it.name)
                        Text(text = it.price)
                    }
                    Spacer(modifier = Modifier.height(dimens.spaceMedium))
                    Text(text = it.description)
                }
            }
        }
    }

}