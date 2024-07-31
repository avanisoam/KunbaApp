package com.example.kunbaapp.ui.poc

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.kunbaapp.ui.node.NodeUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class Poc_FormViewModel : ViewModel() {

    //var name : String by mutableStateOf("")

    //var uiState by mutableStateOf(Poc_FormUiState())
    //private set

    // Created Flow in ViewModel to be collected in UI
    private val _uiState = MutableStateFlow(Poc_FormUiState())
    val uiState: StateFlow<Poc_FormUiState> = _uiState.asStateFlow()

    init {
        // Data Mocked that can come either from API or DB
        val mockItem = Item(
            id = 1,
            name = "Avani",
            price = 110.40,
            quantity = 10
        )

        // Convert Item to ItemDetail
        // Update UIState that will emit value via Flow
        _uiState.update {
            it.copy(
                itemDetails = mockItem.toItemDetails()
            )
        }
    }

    /*
    fun onNameChange(value: String) {
        _uiState.update {
            it.copy(
                name = value
            )
        }
    }
     */

    fun updateUiState(itemDetails: ItemDetails) {
        _uiState.update {
            it.copy(
                // updated uiState's itemDetail property from value received from UI
                itemDetails = itemDetails,
                // Verify received value and update flag
                isEntryValid = validateInput(itemDetails)
            )
        }
    }

    fun onSaveClick() {
        Log.d("ItemDetails", "Before: ${_uiState.value.itemDetails.toString()}")
        Log.d("ItemDetails", "IsValid: ${_uiState.value.isEntryValid.toString()}")
        if (_uiState.value.isEntryValid) {
            val item = _uiState.value.itemDetails.toItem()

            // TODO: Send converted item to API or DB
            Log.d("ItemDetails", "After: ${item.toString()}")
        }
    }

    private fun validateInput(uiState: ItemDetails = _uiState.value.itemDetails): Boolean {
        return with(uiState) {
            name.isNotBlank() && price.isNotBlank() ?: false && quantity.isNotBlank()
        }
    }

}

data class Poc_FormUiState(
    val itemDetails: ItemDetails = ItemDetails(),
    val isEntryValid: Boolean = false
)

data class ItemDetails(
    val id: Int = 0,
    val name: String = "",
    val price: String = "",
    val quantity: String = "",
)


data class Item(
    val id: Int = 0,
    val name: String?,
    val price: Double?,
    val quantity: Int?
)

fun Item.toItemDetails(): ItemDetails = ItemDetails(
    id = id,
    name = name ?: "",
    price = price.toString(),
    quantity = quantity.toString()
)

fun ItemDetails.toItem(): Item = Item(
    id = id,
    name = name,
    price = price.toDoubleOrNull() ?: 0.0,
    quantity = quantity.toIntOrNull() ?: 0
)

