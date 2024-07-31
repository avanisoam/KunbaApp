package com.example.kunbaapp.ui.poc

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.kunbaapp.R
import org.koin.androidx.compose.getViewModel


@Composable
fun Poc_Screen(
    viewModel: Poc_FormViewModel = getViewModel<Poc_FormViewModel>()
) {
    // Collecting Cold Flow from ViewModel as uiState
    val uiState by viewModel.uiState.collectAsState()
    /*
    var name by rememberSaveable {
        mutableStateOf("")
    }
     */

   // Poc_Form(name = uiState.name , onNameChange = {viewModel.onNameChange(it)})

    Column {
        Poc_Form(
            // Pass object to stateless Composable
            itemDetails = uiState.itemDetails,
            // Update itemDetail in hoisted uiState in ViewModel
            onValueChange = { viewModel.updateUiState(it) }
        )
        Button(
            // call onSaveClick() of viewModel
            onClick = { viewModel.onSaveClick() },
            enabled = true,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = stringResource(R.string.save_action))
        }
    }
}

@Composable
fun Poc_Form(
    itemDetails: ItemDetails,
    //name: String,
    //id: String,
    //price: String,
    onValueChange: (ItemDetails) -> Unit,
    enabled: Boolean = true

) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Hello ${itemDetails.toString()}",
            modifier = Modifier.padding(8.dp),
            style= MaterialTheme.typography.bodyMedium
        )
        OutlinedTextField(
            value = itemDetails.name,

            onValueChange = {
                // update in ViewModel
                onValueChange(
                    // update name property of ItemDetails (local copy)
                    itemDetails.copy(name = it)
            )
            },
            label = { Text(stringResource(R.string.firstName)) },
            enabled = enabled,
        )
        OutlinedTextField(
            value = itemDetails.price?:"",
            onValueChange = { onValueChange(itemDetails.copy(price = it))},
            label = { Text(stringResource(R.string.firstName)) },
            enabled = enabled,
        )
        OutlinedTextField(
            value = itemDetails.quantity,
            onValueChange = { onValueChange(itemDetails.copy(quantity = it))},
            label = { Text(stringResource(R.string.firstName)) },
            enabled = enabled,
        )
        if (enabled) {
            Text(
                text = stringResource(R.string.required_fields),
                modifier = Modifier.padding(start = dimensionResource(id = R.dimen.padding_medium))
            )
        }
    }
}