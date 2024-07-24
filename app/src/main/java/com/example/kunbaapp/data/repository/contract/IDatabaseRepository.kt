package com.example.kunbaapp.data.repository.contract

import com.example.kunbaapp.data.models.entity.Favorite
import com.example.kunbaapp.utils.EntityType
import kotlinx.coroutines.flow.Flow

interface IDatabaseRepository {

    /**
     * Insert item in the database
     */
    suspend fun addFavorite(favorite: Favorite)

    /**
     * Retrieve all the items from the the given database.
     */
    fun getFavorites(): Flow<List<Favorite>>

    /**
     * Retrieve all the items by BreedName from the the given database.
     */
    fun getFavoriteById(id: Int): Favorite

    fun getFavoriteByType(type: EntityType): List<Favorite>

    suspend fun getFavoriteByTypeAndRefId(type: EntityType, refId: Int): Favorite?

    /**
     * Delete item from the database
     */
    suspend fun removeFavorite(favorite: Favorite)

}