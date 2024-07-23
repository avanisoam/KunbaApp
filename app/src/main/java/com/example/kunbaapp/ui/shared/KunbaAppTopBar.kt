package com.example.kunbaapp.ui.shared

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.kunbaapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KunbaAppTopBar(
    title: Int,
    canNavigateBack: Boolean,
    showFilter: Boolean = false,
    navigateUp: () -> Unit = {},
    resetFilter: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    CenterAlignedTopAppBar(
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                Image(
                    modifier = Modifier
                        .size(dimensionResource(R.dimen.image_size))
                        .padding(dimensionResource(R.dimen.padding_small)),
                    painter = painterResource(R.drawable.family_tree_logo),
                    contentDescription = null
                )
                Text(
                    text = stringResource(id = title),
                    style = MaterialTheme.typography.displayMedium
                )
            }
        },
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        },

        actions = {
            if(showFilter) {
                IconButton(
                    onClick = resetFilter
                ) {
                    Icon(
                        painterResource(id = R.drawable.undo),
                        contentDescription = null
                    )
                }
            }
        },
        modifier = modifier
    )
}