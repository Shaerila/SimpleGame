package com.example.simplegame.viewmodel

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import com.example.simplegame.domain.model.Player
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel
class GameViewModel: ViewModel() {

    // Stores the player object
    private val _player = mutableStateOf<Player?>(null)

    // You will need to make a separate getter function for calculation and formatting. I assume this
    // means i will need for things like increasing values on level up and damage calculations via combat.
    val player: State<Player?> get() = _player

    // Needed but not sure if set up correctly
    var battlesWon = 0

    var playerAlive = true



    fun createPlayer(name: String, startingClass: String ){
        val player = when (startingClass){
            "Knight" -> Player(name, 50, 50, 7, 10, 6, null, null)
            "Rouge" -> Player(name, 45, 45, 7, 7, 13, null, null)
            "Warrior" -> Player(name, 50, 50, 10, 7, 8, null, null)
            "Classless" -> Player(name, 60, 60, 8, 8, 8,null, null)
            else -> Player(name, 40, 40, 8,9,14, null, null)
        }
        _player.value = player
    }

}