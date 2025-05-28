package com.example.simplegame.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.simplegame.domain.model.Player

@Composable
fun PlayerStatsScreen(player: Player?, onBack: () -> Unit, navController: NavController) {
    if (player == null) {
        Text("Player Data Not Found")
        return
    }


    Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Think about if we needs a "DisplayStats" function
        Text("Name: ${player.name}")
        Text("Health: ${player.health} / ${player.maxHealth}")
        Text("Strength: ${player.strength}")
        Text("Defense: ${player.defense}")
        Text("Speed: ${player.speed}")
        if (player.weapon == null){
            Text("Weapon: ")
        } else {
            Text("Weapon: ${player.weapon?.name}")
        }
        if (player.skill1 == null){
            Text("Skill 1: ")
        } else {
            Text("Skill 1: ${player.skill1?.name}")
        }
        if (player.skill2 == null){
            Text("Skill 2: ")
        } else {
            Text("Skill 2: ${player.skill2?.name}")
        }


        Spacer(Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {

            Button(
                modifier = Modifier.fillMaxWidth(0.5F),
                onClick = onBack
            ) {
                Text("Back To Class Select")
            }

            Spacer(Modifier.width(4.dp))

            Button(
                // A little confused as to why doing this max width gives half the row instead of using
                // 0.5F
                modifier = Modifier.fillMaxWidth(),
                onClick = { navController.navigate("battle") }
            ) {
                Text("Start The Battle")
            }
        }
    }
}