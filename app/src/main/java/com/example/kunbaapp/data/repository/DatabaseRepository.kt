package com.example.kunbaapp.data.repository

import com.example.kunbaapp.data.database.FavoriteDao
import com.example.kunbaapp.data.models.entity.Favorite
import com.example.kunbaapp.data.repository.contract.IDatabaseRepository
import com.example.kunbaapp.utils.EntityType
import kotlinx.coroutines.flow.Flow

class DatabaseRepository(private val favoriteDao: FavoriteDao): IDatabaseRepository {
    override suspend fun addFavorite(favorite: Favorite) = favoriteDao.insertFavorite(favorite)

    override fun getFavorites(): Flow<List<Favorite>> = favoriteDao.getAllFavorites()

    override fun getFavoriteById(id: Int): Favorite = favoriteDao.getFavoriteById(id)

    override fun getFavoriteByType(type: EntityType): List<Favorite> = favoriteDao.getFavoriteByType(type)
    override suspend fun getFavoriteByTypeAndRefId(type: EntityType, refId: Int): Favorite? =favoriteDao.getFavoriteByRefIdAndType(type, refId)

    override suspend fun removeFavorite(favorite: Favorite) = favoriteDao.deleteFavorite(favorite)
}