package com.example.simplegame.data.model

import com.example.simplegame.domain.model.MangledClaws
import com.example.simplegame.domain.model.Monsters
import com.example.simplegame.domain.model.RazorEdge
import com.example.simplegame.domain.model.RustyDagger
import dagger.Module


object MonsterFactory {

    // Here we are creating a function that the MonsterFactory can call to make instances of our monsters
// such that we get unique instances in our MonsterFactory Object. With this, we wont have an issue with
// getting a reference to the same monsters should it be randomly selected again in MonsterFactory.

    fun createDireWolf(): Monsters = Monsters(
        "Dire Wolf",
        40,
        40,
        8,
        6,
        10,
        'D',

        weapon = MangledClaws(),
        skill1 = RazorEdge(),
        skill2 = null,

    )

    fun createGoblin(): Monsters = Monsters(
        name = "Goblin",
        health = 25,
        maxHealth = 25,
        strength = 5,
        defense = 3,
        speed = 8,
        rarity = 'D',
        weapon = RustyDagger(),
        skill1 = null,
        skill2 = null
    )


    val monsterList = listOf(
        ::createDireWolf,
        ::createGoblin
    )

    // Might need to use 'e... function' to call this function in where ever we start our battle from.
    fun getRandomMonsterByRarity(rarity: Char): Monsters{
        return monsterList.filter { it().rarity == 'D' }
            .random()
            .invoke()

    }

    // For now bosses will be X, Y and Z
    fun getRandomBossByRarity(rarity: Char): Monsters{
        return monsterList.filter { it().rarity == 'X' }
            .random()
            .invoke()
    }


}