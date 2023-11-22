@file:OptIn(ExperimentalMaterialApi::class)

package com.smilias.productlist.presentation.products

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.smilias.productlist.ui.theme.LocalSpacing

@Composable
fun ProductsRoute(
    onProductClick: (Long) -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    modifier: Modifier = Modifier,
    viewModel: ProductsScreenViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsState()
    ProductsScreen(
        state = state,
        onProductClick = onProductClick,
        onRefreshGesture = viewModel::refresh,
        onShowSnackbar = onShowSnackbar,
        modifier = modifier
    )
}

@Composable
fun ProductsScreen(
    state: State<ProductsScreenState>,
    onProductClick: (Long) -> Unit,
    onRefreshGesture: () -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    modifier: Modifier = Modifier,
) {

    val pullRefreshState = rememberPullRefreshState(refreshing = state.value.refreshing, onRefresh = onRefreshGesture)
    val dimens = LocalSpacing.current

    LaunchedEffect(key1 = state.value.error){
        state.value.error?.let {
            onShowSnackbar(it, null)
        }
    }
    Box(modifier = modifier
        .pullRefresh(pullRefreshState)
        .fillMaxSize()
        .padding(dimens.spaceSmall)){
        LazyColumn(modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)){
            items(state.value.products.size){index ->
                ProductItem(product = state.value.products[index], onProductClick = onProductClick)
            }
        }
        PullRefreshIndicator(
            state.value.refreshing,
            pullRefreshState,
            Modifier.align(Alignment.TopCenter)
        )
    }

}