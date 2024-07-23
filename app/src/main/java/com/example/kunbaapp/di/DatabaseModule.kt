package com.example.kunbaapp.di

import android.app.Application
import androidx.room.Room
import com.example.kunbaapp.data.database.FavoriteDao
import com.example.kunbaapp.data.database.KunbaLocalDatabase
import com.example.kunbaapp.utils.EntityTypeConverter
import org.koin.dsl.module

fun provideDataBase(application: Application): KunbaLocalDatabase =
    Room.databaseBuilder(
        application,
        KunbaLocalDatabase::class.java,
        "kunba_local_database"
    )
        .fallbackToDestructiveMigration()
        .addTypeConverter(EntityTypeConverter())
        .build()

fun provideDao(kunbaDb: KunbaLocalDatabase): FavoriteDao =
    kunbaDb.favoriteDao()

val dataBaseModule = module {
    single { provideDataBase(get()) }
    single { provideDao(get()) }
}