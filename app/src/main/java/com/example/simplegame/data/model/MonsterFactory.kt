package com.example.simplegame.data.model

import com.example.simplegame.domain.model.MangledClaws
import com.example.simplegame.domain.model.Monsters
import com.example.simplegame.domain.model.RazorEdge
import com.example.simplegame.domain.model.RustyDagger
import com.example.simplegame.domain.model.ShortSword
import dagger.Module


object MonsterFactory {

// Here we are creating a function that the MonsterFactory can call to make instances of our monsters
// such that we get unique instances in our MonsterFactory Object. With this, we wont have an issue with
// getting a reference to the same monsters should it be randomly selected again in MonsterFactory.

    fun createWolf(): Monsters = Monsters(
        "Dire Wolf",
        35,
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
        defense = 4,
        speed = 8,
        rarity = 'D',

        weapon = RustyDagger(),
        skill1 = null,
        skill2 = null
    )

    fun createBandit(): Monsters = Monsters(
        name = "Bandit",
        health = 40,
        maxHealth = 40,
        strength = 5,
        defense = 7,
        speed = 9,
        rarity = 'D',

        weapon = ShortSword(),
        skill1 = null,
        skill2 = null
    )

    fun createSlime(): Monsters = Monsters(
        name = "Slime",
        health = 15,
        maxHealth = 15,
        strength = 3,
        defense = 4,
        speed = 5,
        rarity = 'D',

        weapon = null,
        skill1 = null,
        skill2 = null
    )

    fun createHawk(): Monsters = Monsters(
        name = "Slime",
        health = 21,
        maxHealth = 21,
        strength = 6,
        defense = 4,
        speed = 12,
        rarity = 'D',

        weapon = MangledClaws(),
        skill1 = RazorEdge(),
        skill2 = null
    )


    val monsterList = listOf(
        ::createWolf,
        ::createGoblin,
        ::createBandit,
        ::createSlime,
        ::createHawk
    )

    // Might need to use 'e... function' to call this function in where ever we start our battle from.
    fun getRandomMonsterByRarity(rarity: Char): Monsters{
        return monsterList.filter {
            it().rarity == 'D'
        }
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