package com.example.simplegame.domain.model

open class Monsters (

    name: String,
    health: Int,
    maxHealth: Int,
    strength: Int,
    defense: Int,
    speed: Int,
    val rarity: Char,


    weapon: Weapon? = null,
    skill1: Skill? = null,
    skill2: Skill? = null


    // Forward to creature class? (Thought this is taking from creature class)
) : Creature(name, health, maxHealth, strength, defense, speed, weapon, skill1, skill2 )

