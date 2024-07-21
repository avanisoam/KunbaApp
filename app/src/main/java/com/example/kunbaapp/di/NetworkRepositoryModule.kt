package com.example.kunbaapp.di

import com.example.kunbaapp.data.repository.ApiRepository
import com.example.kunbaapp.data.repository.contract.IApiRepository
import org.koin.dsl.module

val dataSourceModule = module {
    factory<IApiRepository> {  ApiRepository(get()) }
}