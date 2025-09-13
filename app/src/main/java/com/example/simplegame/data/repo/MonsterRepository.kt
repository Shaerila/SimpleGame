package com.example.simplegame.data.repo

import com.example.simplegame.domain.model.Monsters

interface MonsterRepository {
    fun getRandomMonsterByRarity(rarity: Char): Monsters?
}