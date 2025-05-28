package com.example.simplegame.domain.model

open class Creature(

    val name: String,
    var health: Int,
    var maxHealth: Int,
    var strength: Int,
    var defense: Int,
    var speed: Int,

    var weapon: Weapon? = null,
    var skill1: Skill? = null,
    var skill2: Skill? = null,

)

