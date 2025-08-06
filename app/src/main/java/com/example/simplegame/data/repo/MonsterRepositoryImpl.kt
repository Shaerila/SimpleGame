package com.example.simplegame.data.repo

import com.example.simplegame.data.repo.MonsterFactory
import com.example.simplegame.domain.model.Monsters
import com.example.simplegame.domain.repo.MonsterRepository

class MonsterRepositoryImpl : MonsterRepository {
    override fun getRandomMonsterByRarity(rarity: Char): Monsters? {
        return MonsterFactory.getRandomMonsterByRarity(rarity)
    }
}