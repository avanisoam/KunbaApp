package com.example.kunbaapp.di

import com.example.kunbaapp.ui.family.FamilyViewModel
import com.example.kunbaapp.ui.favorite.FavoriteViewModel
import com.example.kunbaapp.ui.home.HomeViewModel
import com.example.kunbaapp.ui.node.NodeViewModel
import com.example.kunbaapp.ui.poc.Poc_FormViewModel
import com.example.kunbaapp.ui.poc.Poc_RootDetailViewModel
import com.example.kunbaapp.ui.rootDetail.RootDetailViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel{
        HomeViewModel(get(), get(), get())
    }

    viewModel{
        RootDetailViewModel(get(),get(),get())
    }

    viewModel{
        FamilyViewModel(get(),get(),get(),get())
    }

    viewModel{
        NodeViewModel(get(),get(),get())
    }

    viewModel{
        FavoriteViewModel(get(),get())
    }

    viewModel{
        Poc_RootDetailViewModel(get())
    }
    viewModel{
        Poc_FormViewModel()
    }
}