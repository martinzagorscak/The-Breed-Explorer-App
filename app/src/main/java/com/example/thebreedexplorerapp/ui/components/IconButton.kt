package com.example.thebreedexplorerapp.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.thebreedexplorerapp.R
import com.example.thebreedexplorerapp.ui.theme.padding100
import com.example.thebreedexplorerapp.ui.theme.padding200

private val defaultIconButtonSize = 24.dp

@Composable
fun IconButton(
    @DrawableRes iconResId: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    iconButtonSize: Dp = defaultIconButtonSize,
    tint: Color = LocalContentColor.current,
) {
    Box(
        modifier = modifier
            .clip(CircleShape)
            .clickable(onClick = onClick),
    ) {
        Icon(
            painter = painterResource(id = iconResId),
            contentDescription = null,
            tint = tint,
            modifier = Modifier
                .size(iconButtonSize)
                .padding(padding100),
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun IconButtonPreview() {
    Column(verticalArrangement = Arrangement.spacedBy(padding200)) {
        IconButton(
            iconResId = R.drawable.ic_back,
            onClick = {}
        )
        IconButton(
            iconResId = R.drawable.ic_favorite,
            onClick = {}
        )
    }
}
