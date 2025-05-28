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
    4,
    0,
    4,
    'M'
)

class RustyDagger: Weapon(
    "Rusty Dagger",
    4,
    0,
    2,
    'M'
)


// Rarity D Class
class ShortSword: Weapon(
    "Short Sword",
    6,
    0,
    3,
    'D'
)

class LongSword: Weapon(
    "Long Sword",
    9,
    1,
    0,
    'D'
)

class Dagger: Weapon(
    "Dagger",
    4,
    0,
    6,
    'D'
)

// Rarity C Class
class SteelSword: Weapon(
    "Steel Sword",
    5,
    0,
    1,
    'D'
)

class GreatSword: Weapon(
    "Great Sword",
    16,
    3,
    0,
    'C'
)

class DualDaggers: Weapon(
    "Dual Daggers",
    9,
    0,
    12,
    'D'
)

// Rarity B Class

//Rarity A Class