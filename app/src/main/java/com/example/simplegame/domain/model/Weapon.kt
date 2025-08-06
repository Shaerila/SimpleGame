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


// Rarity D Class
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
    1,
    2,
    'D'
)

class LongSword: Weapon(
    "Long Sword",
    8,
    1,
    0,
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
    strength = 5,
    defense = 0,
    speed = 2,
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
    'C'
)

class SwordAndShield: Weapon(
    "Sword and Shield",
    strength = 12,
    defense = 8,
    speed = 2,
    'C'
)
// Rarity B Class

//Rarity A Class