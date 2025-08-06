package com.example.simplegame.application.usecase

import com.example.simplegame.domain.model.Monsters
import com.example.simplegame.domain.repo.MonsterRepository

class GetRandomMonsterByRarityUseCase(
    private val repository: MonsterRepository
) {
    operator fun invoke(rarity: Char): Monsters? {
        return repository.getRandomMonsterByRarity(rarity)
    }
}