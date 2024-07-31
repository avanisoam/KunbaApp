package com.example.kunbaapp.ui.shared.Nodes

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.kunbaapp.R
import com.example.kunbaapp.data.models.dto.NodeDto
import com.example.kunbaapp.data.models.dto.NodeDtos.AddNodeDto
import com.example.kunbaapp.data.models.dto.NodeDtos.UpdateNodeDto
import com.example.kunbaapp.ui.shared.FilterDropdown
import org.koin.core.component.getScopeName
import java.util.Currency
import java.util.Locale


@Composable
fun UpdateNodeBody(
    node: UpdateNodeDto,
    onItemValueChange: (UpdateNodeDto) -> Unit,
    onSaveClick: (UpdateNodeDto) -> Unit,
    isEntryValid: Boolean,
    selectedValue: String,
    //filterGender: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium)),
        modifier = modifier.verticalScroll(rememberScrollState())
            .padding(dimensionResource(id = R.dimen.padding_medium))
    ) {
        ItemUpdateForm(
            node = node,
            onValueChange = onItemValueChange,
            modifier = Modifier.fillMaxWidth(),
            selectedValue = selectedValue,
            //filterGender = filterGender
        )
        Button(
            onClick = { onSaveClick(node) },
            enabled = true,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = stringResource(R.string.save_action))
        }
    }
}

@Composable
fun ItemUpdateForm(
    node: UpdateNodeDto,
    modifier: Modifier = Modifier,
    onValueChange: (UpdateNodeDto) -> Unit = {},
    selectedValue: String,
    //filterGender: (String) -> Unit,
    enabled: Boolean = true
) {
    Column(
        modifier = modifier,//.verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium))
    ) {
        /*
        Text(
            text = "Hello ${node.toString()}",
            modifier = Modifier.padding(8.dp),
            style= MaterialTheme.typography.bodyMedium
        )

         */

        OutlinedTextField(
            value = node.firstName,
            onValueChange = { onValueChange(node.copy(firstName = it)) },
            label = { Text(stringResource(R.string.firstName)) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            ),
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )

        OutlinedTextField(
            value = node.lastName,
            onValueChange = { onValueChange(node.copy(lastName = it)) },
            label = { Text(stringResource(R.string.lastName)) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            ),
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        FilterDropdown(
            distinctValues = listOf("Male","Female"),
            filterValue = selectedValue.ifEmpty { "Gender" },
            onSelect = {onValueChange(node.copy(gender = it))},
            //label = "Gender",
            modifier = Modifier.fillMaxWidth()
                .border(BorderStroke(1.dp, Color.Black)),
            colors = TextFieldDefaults.colors(
                focusedTextColor = MaterialTheme.colorScheme.secondaryContainer,
                unfocusedTextColor = MaterialTheme.colorScheme.secondaryContainer,
                disabledTextColor = MaterialTheme.colorScheme.secondaryContainer,
            ),
        )
        /*
        OutlinedTextField(
            value = node.gender,
            onValueChange = { onValueChange(node.copy(gender = it)) },
            label = { Text(stringResource(R.string.gender)) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            ),
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )

         */

        OutlinedTextField(
            value = node.dateOfBirth,
            onValueChange = { onValueChange(node.copy(dateOfBirth = it)) },
            label = { Text(stringResource(R.string.dateOfBirth)) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            ),
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = node.placeOfBirth,
            onValueChange = { onValueChange(node.copy(placeOfBirth = it)) },
            label = { Text(stringResource(R.string.placeOfBirth)) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            ),
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = node.image_Url,
            onValueChange = { onValueChange(node.copy(image_Url = it)) },
            label = { Text(stringResource(R.string.imageUrl)) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            ),
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )

        if (enabled) {
            Text(
                text = stringResource(R.string.required_fields),
                modifier = Modifier.padding(start = dimensionResource(id = R.dimen.padding_medium))
            )
        }
    }
}

