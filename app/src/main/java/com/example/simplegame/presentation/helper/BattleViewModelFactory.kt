package com.example.simplegame.presentation.helper

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.simplegame.application.usecase.GetRandomMonsterByRarityUseCase
import com.example.simplegame.viewmodel.BattleViewModel
import com.example.simplegame.viewmodel.GameViewModel

class BattleViewModelFactory(
    private val useCase: GetRandomMonsterByRarityUseCase,
    private val gameViewModel: GameViewModel
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BattleViewModel::class.java)) {
            return BattleViewModel(
                useCase,
                gameViewModel
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}