package com.example.simplegame.domain.model


// Using Abstract class, this is the "shell". With this we are saying that we have no specific
// 'Skill', but that each skill is its own individual that all have a proc function to proc it.
abstract class Skill(
    val name: String,
    val procRate: Double,
    val rarity: Char
) {
    abstract fun skillProc(player: Player, monsters: Monsters): String

}

// Monster Skills
class RazorEdge : Skill("Razor Edge", 70.0, 'M') {
    override fun skillProc(player: Player, monster: Monsters): String {
        monster.speed += (monster.speed * .3).toInt()
        return("${monster.name} eyes glare with focus. Speed has increased by ${(monster.speed * .4).toInt()}")
    }
}

// Buffs

// Attacks

// Heals


// ----------------------------
// Player Skills
class SecondWind : Skill("Second Wind", 75.0, 'C') {
    override fun skillProc(player: Player, monsters: Monsters): String {

        if ((player.health + (player.maxHealth * .1).toInt()) > player.maxHealth){
            player.health = player.maxHealth
        } else {
            player.health += (player.maxHealth * .1).toInt()
        }
        return("${player.name} catches their breath and heals ${(player.maxHealth * .1).toInt()} hp to ${player.health}!")
    }
}

class SneakAttack: Skill("Sneak Attack", 75.0, 'C'){
    override fun skillProc(player: Player, monster: Monsters): String {
        monster.health -= (player.speed * .3).toInt()
        return("${player.name} caught ${monster.name} by surprise dealing ${(player.speed * .3).toInt()} damage!")
    }
}

// Buffs

// Attacks

// Heals

