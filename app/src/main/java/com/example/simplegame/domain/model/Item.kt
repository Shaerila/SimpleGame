package com.example.simplegame.domain.model

import com.example.simplegame.application.usecase.RandomNumberGenerator

abstract class Item (
    val name: String,
    val description: String,
    val rarity: Char
) {
    abstract fun useItem(player: Player, monsters: Monsters): String
}


class HealthPotion : Item ("Potion Of Healing", "Heals the user for a random amount between 10 - 30 Health", 'D'){
    override fun useItem(player: Player, monsters: Monsters): String {
        //(Max health function/code needed)
        val amountHealed = RandomNumberGenerator().randomNumberBetweenRange(15,30)
        player.health += amountHealed
        return ("${player.name} used a health potion and heal ${amountHealed} health!")

    }

}