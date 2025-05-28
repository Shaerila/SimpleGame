package com.example.simplegame.domain.repo

import com.example.simplegame.domain.model.Monsters

interface MonsterRepository {
    fun getRandomMonsterByRarity(rarity: Char): Monsters?
}