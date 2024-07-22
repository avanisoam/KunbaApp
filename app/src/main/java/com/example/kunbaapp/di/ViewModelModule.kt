package com.example.kunbaapp.di

import com.example.kunbaapp.ui.family.FamilyViewModel
import com.example.kunbaapp.ui.home.HomeViewModel
import com.example.kunbaapp.ui.rootDetail.RootDetailViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel{
        HomeViewModel(get())
    }

    viewModel{
        RootDetailViewModel(get(),get())
    }

    viewModel{
        FamilyViewModel(get(),get())
    }
}