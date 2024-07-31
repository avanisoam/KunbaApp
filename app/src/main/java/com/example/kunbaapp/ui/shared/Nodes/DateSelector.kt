package com.example.kunbaapp.ui.shared.Nodes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DatePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.kunbaapp.utils.DateUtils

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateSelector() {
    val dateState = rememberDatePickerState()
    val millisToLocalDate = dateState.selectedDateMillis?.let {
        DateUtils().convertMillisToLocalDate(it)
    }
    val dateToString = millisToLocalDate?.let {
        DateUtils().dateToString(millisToLocalDate)
    } ?: ""
    Column {
        DatePicker(
            state = dateState
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            text = dateToString
        )
    }
}