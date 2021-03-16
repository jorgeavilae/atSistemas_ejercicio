package com.example.atsistemas_ejercicio.di

import com.example.atsistemas_ejercicio.home_activity.HomeViewModelActivity
import com.example.atsistemas_ejercicio.home_activity.detail.vm.DDetailViewModel
import com.example.atsistemas_ejercicio.home_activity.list.vm.ListViewModel
import com.example.atsistemas_ejercicio.home_activity.profile.vm.ProfileViewModel
import com.example.atsistemas_ejercicio.utils.SharedTransactionVM
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val uiModule = module {
    viewModel { HomeViewModelActivity() }
    viewModel { ListViewModel(get()) }
    viewModel { ProfileViewModel(get()) }
    viewModel { DDetailViewModel(get()) }
    viewModel { SharedTransactionVM() }
}