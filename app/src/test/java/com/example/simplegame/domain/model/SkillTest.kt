package com.example.simplegame.domain.model


import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals

class SkillTest {

    @Test
    fun testRazorEdge() {

        // Create the
        val player = Player("testHero", 100, 100, 10, 10, 10)
        val monster = Monsters("Goblin", 20, 20, 5, 10, 10, 'D')


        val razorEdge = RazorEdge()

        val result = razorEdge.skillProc(player, monster)

        val expectedSpeed = 13
        val expectedMessage = "Goblin eyes glare with focus. Speed has increased by 3"

        assertEquals(expectedSpeed, monster.speed)
        assertEquals(expectedMessage, result)
    }


}