package com.example.composeattempt

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel {
        MainViewModel(
            gameMaster = get()
        )
    }
    single<GameMasterRepository> { GameMaster() }
}