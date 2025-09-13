package com.example.simplegame.domain.model

open class Weapon (
    val name: String,
    val strength: Int,
    val defense: Int,
    val speed: Int,
    val rarity: Char

)

// Monster Weapons
class MangledClaws: Weapon(
    "Mangled Claws",
    6,
    0,
    4,
    'M'
)

class RustyDagger: Weapon(
    "Rusty Dagger",
    5,
    0,
    2,
    'M'
)


// Rarity D Class ( Stats of '9 point' )
class SwordAndWoodenShield: Weapon(
    "Sword And Wooden Shield",
    4,
    4,
    1,
    'D'
)

class ShortSword: Weapon(
    "Short Sword",
    6,
    2,
    1,
    'D'
)

class LongSword: Weapon(
    "Long Sword",
    7,
    1,
    1,
    'D'
)

class Dagger: Weapon(
    "Dagger",
    3,
    0,
    6,
    'D'
)
class Club: Weapon(
    "Club",
    strength = 6,
    defense = 3,
    speed = 0,
    'D'
)


// Rarity C Class ( Stats of '18 point' )
class SteelSword: Weapon(
    "Steel Sword",
    9,
    5,
    4,
    'D'
)

class GreatSword: Weapon(
    "Great Sword",
    15,
    3,
    0,
    'C'
)

class DualDaggers: Weapon(
    "Dual Daggers",
    7,
    0,
    11,
    'C'
)

class SwordAndShield: Weapon(
    "Sword and Shield",
    strength = 10,
    defense = 8,
    speed = 0,
    'C'
)
// Rarity B Class ( Stats of '30 point' )

//Rarity A Class ( Stats of '50 point' )