package com.example.kunbaapp.di

import android.app.Application
import androidx.room.Room
import com.example.kunbaapp.data.database.FamilyDao
import com.example.kunbaapp.data.database.FavoriteDao
import com.example.kunbaapp.data.database.KunbaLocalDatabase
import com.example.kunbaapp.data.database.NodeDao
import com.example.kunbaapp.data.database.RootRegisterDao
import com.example.kunbaapp.data.models.entity.ChildrenFamilyTypeConvertor
import com.example.kunbaapp.data.models.entity.NodeTypeConvertor
import com.example.kunbaapp.utils.EntityTypeConverter
import org.koin.dsl.module

fun provideDataBase(application: Application): KunbaLocalDatabase =
    Room.databaseBuilder(
        application,
        KunbaLocalDatabase::class.java,
        "kunba_local_database"
    )
        .fallbackToDestructiveMigration()
        .addTypeConverter(NodeTypeConvertor())
        .addTypeConverter(EntityTypeConverter())
        .addTypeConverter(ChildrenFamilyTypeConvertor())
        .build()

fun provideDao(kunbaDb: KunbaLocalDatabase): FavoriteDao =
    kunbaDb.favoriteDao()

fun provideRootRegisterDao(kunbaDb: KunbaLocalDatabase): RootRegisterDao =
    kunbaDb.rootRegisterDao()

fun provideNodeDao(kunbaDb: KunbaLocalDatabase): NodeDao =
    kunbaDb.nodeDao()

fun provideFamilyDao(kunbaDb: KunbaLocalDatabase): FamilyDao =
    kunbaDb.familyDao()

val dataBaseModule = module {
    single { provideDataBase(get()) }
    single { provideDao(get()) }
    single { provideRootRegisterDao(get()) }
    single { provideNodeDao(get()) }
    single { provideFamilyDao(get()) }
}