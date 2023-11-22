package com.smilias.productlist.presentation.products

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.smilias.productlist.domain.model.Product
import com.smilias.productlist.ui.theme.LocalSpacing

@Composable
fun ProductItem(
    product: Product,
    onProductClick: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    val dimens = LocalSpacing.current

    Card(
        modifier = modifier.clickable { onProductClick(product.id) },
        elevation = CardDefaults.cardElevation(dimens.spaceExtraSmall)
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(bottom = dimens.spaceMedium),
            verticalArrangement = Arrangement.spacedBy(dimens.spaceMedium)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(
                    LocalContext.current
                ).data(product.thumbnail)
                    .crossfade(1000)
                    .build(),
                contentDescription = product.name,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally)
                    .clickable { onProductClick(product.id) }
            )
            Row(modifier = modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween) {
                Text(text = product.name)
                Text(text = product.price)
            }
        }
    }

}
