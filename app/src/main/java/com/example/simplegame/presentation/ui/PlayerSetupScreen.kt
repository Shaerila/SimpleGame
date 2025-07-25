package com.example.simplegame.presentation.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayerSetupScreen(onStartGame: (String, String) -> Unit) {

    BackHandler(enabled = true) {}

    var playerName by remember { mutableStateOf("") }
    var selectedCharacter by remember { mutableStateOf("Warrior") }
    val characterOptions = listOf("Warrior", "Rogue", "Classless")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Enter your name:", fontSize = 20.sp)
        TextField(
            value = playerName,
            onValueChange = { playerName = it },
            label = { Text("Player Name") }
        )

        Text("Choose your character:", fontSize = 20.sp)
        characterOptions.forEach { character ->
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = (character == selectedCharacter),
                    onClick = { selectedCharacter = character }
                )
                Text(
                    text = character,
                    modifier = Modifier.clickable { selectedCharacter = character })
            }
        }

        Button(
            onClick = { onStartGame(playerName, selectedCharacter) },
            enabled = playerName.isNotBlank()
        ) {
            Text("View Class Stats")
            // Add Navigation To Stats Screen

        }
    }
}