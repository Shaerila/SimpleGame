package com.example.simplegame.data.repo

import com.example.simplegame.domain.model.*

object MonsterFactory {

// Here we are creating a function that the MonsterFactory can call to make instances of our monsters
// such that we get unique instances in our MonsterFactory Object. With this, we wont have an issue with
// getting a reference to the same monsters should it be randomly selected again in MonsterFactory.

    fun createWolf(): Monsters = Monsters(
        "Dire Wolf",
        45,
        45,
        6,
        7,
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
        strength = 7,
        defense = 4,
        speed = 8,
        rarity = 'D',

        weapon = RustyDagger(),
        skill1 = null,
        skill2 = null
    )

    fun createBandit(): Monsters = Monsters(
        name = "Bandit",
        health = 50,
        maxHealth = 50,
        strength = 7,
        defense = 8,
        speed = 9,
        rarity = 'D',

        weapon = ShortSword(),
        skill1 = null,
        skill2 = null
    )

    fun createSlime(): Monsters = Monsters(
        name = "Slime",
        health = 20,
        maxHealth = 20,
        strength = 5,
        defense = 5,
        speed = 5,
        rarity = 'D',

        weapon = null,
        skill1 = DivideToGrow(),
        skill2 = null
    )

    fun createHawk(): Monsters = Monsters(
        name = "Hawk",
        health = 32,
        maxHealth = 32,
        strength = 7,
        defense = 6,
        speed = 12,
        rarity = 'D',

        weapon = MangledClaws(),
        skill1 = RazorEdge(),
        skill2 = SwiftStrike()
    )


    // 'C' Class Monsters

    fun createTroll(): Monsters = Monsters(
        name = "Troll",
        health = 70,
        maxHealth = 70,
        strength = 19,
        defense = 10,
        speed = 13,
        rarity = 'C',

        weapon = Club(),
        skill1 = Clobber(),
        skill2 = null
    )

    fun createCursedKnight(): Monsters = Monsters(
        name = "Cursed Knight",
        health = 80,
        maxHealth = 80,
        strength = 16,
        defense = 17,
        speed = 10,
        rarity = 'C',

        weapon = SwordAndShield(),
        skill1 = null,
        skill2 = null
    )

    fun createWraith(): Monsters = Monsters(
        name = "Wraith",
        health = 40,
        maxHealth = 40,
        strength = 20,
        defense = 10,
        speed = 26,
        rarity = 'C',

        weapon = null,
        skill1 = EtherealWail(),
        skill2 = GhostForm()
    )


    // 'X' Class Bosses
    fun createOresukiBench(): Monsters = Monsters(
        name = "Oresuki Bench",
        health = 100,
        maxHealth = 100,
        strength = 1,
        defense = 100,
        speed = 100,
        rarity = 'X',

        weapon = null,
        skill1 = Confession(),
        skill2 = null
    )
    val monsterList = listOf(
        ::createWolf,
        ::createGoblin,
        ::createBandit,
        ::createSlime,
        ::createHawk,
        ::createTroll,
        ::createCursedKnight,
        ::createWraith,
        ::createOresukiBench
    )

    // Might need to use 'e... function' to call this function in where ever we start our battle from.
    fun getRandomMonsterByRarity(rarity: Char): Monsters {
        return monsterList.filter {
            it().rarity == rarity
        }
            .random()
            .invoke()

    }

    // For now bosses will be X, Y and Z
    fun getRandomBossByRarity(rarity: Char): Monsters {
        return monsterList.filter { it().rarity == 'X' }
            .random()
            .invoke()
    }


}