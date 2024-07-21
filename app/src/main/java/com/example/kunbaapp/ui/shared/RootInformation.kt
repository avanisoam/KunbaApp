package com.example.kunbaapp.ui.shared

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.example.kunbaapp.R

@Composable
fun RootInformation(
    dogName: String,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = dogName.capitalize(),
            style = MaterialTheme.typography.displayMedium,
            modifier = Modifier.padding(top = dimensionResource(R.dimen.padding_small))
        )
    }
}