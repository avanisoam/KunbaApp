package com.example.kunbaapp.ui.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kunbaapp.data.models.entity.Favorite
import com.example.kunbaapp.data.repository.contract.IDatabaseRepository
import com.example.kunbaapp.utils.EntityType
import kotlinx.coroutines.launch

class FavoriteViewModel(
    private val databaseRepository: IDatabaseRepository
) : ViewModel() {

    fun loadData() {
        viewModelScope.launch {

            val root = Favorite(
                id = 0,
                type = EntityType.Root,
                refId = 1,
            )
            databaseRepository.addFavorite(root)

            val family = Favorite(
                id = 0,
                type = EntityType.Family,
                refId = 1,
            )
            databaseRepository.addFavorite(family)

            val node = Favorite(
                id = 0,
                type = EntityType.Node,
                refId = 1,
            )
            databaseRepository.addFavorite(node)
        }
    }

    init {
        loadData()
    }
}