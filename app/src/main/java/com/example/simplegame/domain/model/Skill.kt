package com.example.simplegame.domain.model

import com.example.simplegame.application.usecase.RandomNumberGenerator


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


// Monsters Buffs
class RazorEdge : Skill("Razor Edge", 40.0, 'M') {
    override fun skillProc(player: Player, monsters: Monsters): String {
        monsters.speed += (monsters.speed.plus(monsters.weapon?.speed ?: 0) * .3).toInt()
        return("${monsters.name} eyes glare with focus. Speed has increased " +
                "by ${(monsters.speed.plus(monsters.weapon?.speed ?: 0) * .3).toInt()}")
    }
}

class DivideToGrow : Skill("Divide To Grow", 10.0, 'M') {
    override fun skillProc(player: Player, monsters: Monsters): String {
        monsters.maxHealth *= 2
        monsters.health *= 2
        monsters.strength += 5
        monsters.defense += 5
        monsters.speed += 5

        return("${monsters.name} split in two... but then recombined? It seems to have made it grow stronger somehow!")
    }
}

class GhostForm : Skill("Ghost Form", 100.0, 'M') {
    override fun skillProc(player: Player, monsters: Monsters): String {

        if (RandomNumberGenerator().randomNumberTo10() >= 5){
            monsters.defense = 1000
            return("${monsters.name} seems to be in its ghostly form making it impossible to hit!")

        } else {
            monsters.defense = 0
            return("${monsters.name} is coming out of its ghost form! Now's the time to strike!")
        }
    }
}

class Confession : Skill("Confession", 100.0, 'M') {
    override fun skillProc(player: Player, monsters: Monsters): String {

        monsters.strength *= 2

        return ("${monsters.name} aura rages as you watch a cute girl sit and confessed her love for... THE BOYS BEST FRIEND?!?" +
                "This seems to make the bench grow stronger!?")
    }
}

// Monsters Attacks
class SwiftStrike : Skill("Swift Strike", 40.0, 'M') {
    override fun skillProc(player: Player, monsters: Monsters): String {
        player.health -= (monsters.speed.plus(monsters.weapon?.speed ?: 0) * .25).toInt()
        return("${monsters.name} moves at blinding speed dealing ${(monsters.speed.plus(monsters.weapon?.speed ?: 0) * .25).toInt()} damage ")
    }
}

class Clobber : Skill("SLAM", 15.0, 'M') {
    override fun skillProc(player: Player, monsters: Monsters): String {
        player.health -= (monsters.strength.plus(monsters.weapon?.strength ?: 0) * .50).toInt()
        return("${monsters.name} takes there ${monsters.weapon?.name} with both hand and SLAMS it into" +
                " you dealing ${monsters.strength.plus(monsters.weapon?.strength ?: 0) * .50} damage!")
    }

}



// Monster Heals
class EtherealWail : Skill("Ethereal Wail", 30.0, 'M') {
    override fun skillProc(player: Player, monsters: Monsters): String {
        player.health -= (monsters.strength * .35).toInt()
        //(???) Think about adding a function that checks if hp healed goes over hp max

        if ((monsters.health + (monsters.maxHealth * .35).toInt()) > monsters.maxHealth){
            monsters.health = monsters.maxHealth
        } else {
            monsters.health += (monsters.maxHealth * .35).toInt()
        }
        return("${monsters.name} lets out a ghastly wail! It saps your of their strength dealing " +
                "and healing ${monsters.strength * .35} damage!")
    }

}



// ----------------------------
// Player Skills
class SecondWind : Skill("Second Wind", 20.0, 'C') {
    override fun skillProc(player: Player, monsters: Monsters): String {

        if ((player.health + (player.maxHealth * .1).toInt()) > player.maxHealth){
            player.health = player.maxHealth
        } else {
            player.health += (player.maxHealth * .1).toInt()
        }
        return("${player.name} catches their breath and heals ${(player.maxHealth * .1).toInt()} hp to ${player.health}!")
    }
}

class SneakAttack: Skill("Sneak Attack", 45.0, 'C'){
    override fun skillProc(player: Player, monster: Monsters): String {
        monster.health -= (player.speed.plus(player.weapon?.speed ?: 0) * .3).toInt()
        return("${player.name} caught ${monster.name} by surprise dealing ${(player.speed.plus(player.weapon?.speed ?: 0) * .3).toInt()} damage!")
    }
}

class Bash: Skill("Bash", 25.0, 'C'){
    override fun skillProc(player: Player, monster: Monsters): String {
        monster.health -= (player.strength.plus(player.weapon?.strength ?: 0) * .50).toInt()
        return("${player.name} gathers their strength and swing with great force dealing ${(player.strength.plus(player.weapon?.strength ?: 0) * .50).toInt()} damage!")
    }
}

// Buffs

// Attacks

// Heals

