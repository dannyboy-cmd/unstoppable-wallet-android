package io.horizontalsystems.bankwallet.modules.market.overview.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import io.horizontalsystems.bankwallet.R
import io.horizontalsystems.bankwallet.modules.market.MarketDataValue
import io.horizontalsystems.bankwallet.modules.market.TimeDuration
import io.horizontalsystems.bankwallet.modules.market.overview.MarketOverviewModule
import io.horizontalsystems.bankwallet.modules.market.topplatforms.TopPlatformViewItem
import io.horizontalsystems.bankwallet.ui.compose.ComposeAppTheme
import io.horizontalsystems.bankwallet.ui.compose.components.CoinImage
import io.horizontalsystems.bankwallet.ui.compose.components.MarketCoinFirstRow
import io.horizontalsystems.bankwallet.ui.compose.components.MarketCoinSecondRow
import io.horizontalsystems.bankwallet.ui.compose.components.MultilineClear

@Composable
fun TopPlatformsBoardView(
    board: MarketOverviewModule.TopPlatformsBoard,
    onSelectTimeDuration: (TimeDuration) -> Unit,
    onClickSeeAll: () -> Unit
) {
    TopBoardHeader(
        title = board.title,
        iconRes = board.iconRes,
        select = board.timeDurationSelect,
        onSelect = onSelectTimeDuration
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(ComposeAppTheme.colors.lawrence)
    ) {
        board.items.forEach { item ->
            TopPlatformsView(item)
        }

        SeeAllButton(onClickSeeAll)
    }

    Spacer(modifier = Modifier.height(24.dp))
}

@Composable
private fun TopPlatformsView(
    item: TopPlatformViewItem,
) {
    MultilineClear(borderBottom = true) {
        CoinImage(
            iconUrl = item.iconUrl,
            placeholder = R.drawable.coin_placeholder,
            modifier = Modifier
                .padding(end = 16.dp)
                .size(24.dp)
        )
        Column(modifier = Modifier.fillMaxWidth()) {
            MarketCoinFirstRow(item.platform.name, item.marketCap)
            Spacer(modifier = Modifier.height(3.dp))
            MarketCoinSecondRow(
                item.subtitle,
                MarketDataValue.Diff(item.marketCapDiff),
                "${item.rank}"
            )
        }
    }
}