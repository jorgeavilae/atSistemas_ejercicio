package com.example.data.di

import com.example.data.di.providers.*
import org.koin.dsl.module

val dataModule = module {
    single { provideOkHttpClient(get()) }
    single { provideMockInterceptor(get()) }
    single { provideGson() }
    single { provideRetrofit(get(), get()) }
    single { provideTransactionApi(get()) }
    single { provideBankDatabase(get()) }
    single { provideTransactionRepository(get(), get()) }
}