package com.example.simplegame.application.usecase

import com.example.simplegame.domain.model.Player

class RandomNumberGenerator() {

    fun randomNumberTo3(): Int{
        val randomValue = (1..3).random()
        return randomValue
    }

    fun randomNumberTo5(): Int {
        val randomValue = (1..5).random()
        return randomValue
    }


    fun randomNumberTo10(): Int {
        val randomValue = (1..10).random()
        return randomValue
    }

    fun randomNumberTo100(): Int {
        val randomValue = (1..100).random()
        return randomValue
    }

    fun randomNumberBetweenRange(first: Int, second: Int): Int {
        val randomValue = (first..second).random()
        return randomValue

    }



}