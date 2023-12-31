package com.example.demoassignment.presentation.ui.items

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.example.demoassignment.R
import com.example.demoassignment.presentation.ui.theme.TestTheme

@Composable
fun TestAppBar(showBack: Boolean, backAction: () -> Unit = {}, darkIcon: Boolean = TestTheme.colors.isLight) {
    Row(modifier = Modifier.height(56.dp).fillMaxWidth()) {
        Surface(color = Color.Transparent) {
            if (showBack) {
                IconButton(onClick = backAction) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_back),
                        modifier = Modifier.padding(4.dp),
                        contentDescription = "",
                        tint = if (darkIcon) Color.Black else Color.White
                    )
                }
            }
        }
    }
}