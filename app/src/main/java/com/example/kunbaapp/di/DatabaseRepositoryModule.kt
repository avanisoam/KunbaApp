package com.example.kunbaapp.di

import com.example.kunbaapp.data.repository.DatabaseRepository
import com.example.kunbaapp.data.repository.contract.IDatabaseRepository
import org.koin.dsl.module

val databaseRepositoryModule = module {
    factory<IDatabaseRepository> {  DatabaseRepository(get()) }
}