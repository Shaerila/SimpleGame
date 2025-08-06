package com.example.simplegame.domain.model

class Player (


    name: String,
    health: Int,
    maxHealth: Int,
    strength: Int,
    defense: Int,
    speed: Int,

    weapon: Weapon? = null,
    skill1: Skill? = null,
    skill2: Skill? = null

) : Creature(name, health, maxHealth, strength, defense, speed, weapon, skill1, skill2) {

    fun equipWeapon(newWeapon: Weapon){
        weapon = newWeapon
        weapon?.let {
            println("${it.name} has been equipped!")
        }

    }
    fun equipSkill1(newSkill: Skill){
        skill1 = newSkill
        skill1?.let {
            println("${it.name} has been equipped!")
        }
    }

    fun equipSkill2(newSkill: Skill){
        skill2 = newSkill
        skill2?.let {
            println("${it.name} has been equipped!")
        }
    }
}