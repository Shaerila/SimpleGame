package com.example.simplegame.presentation.ui

import androidx.activity.compose.BackHandler
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
import com.example.simplegame.domain.model.Dagger
import com.example.simplegame.domain.model.LongSword
import com.example.simplegame.domain.model.Player
import com.example.simplegame.domain.model.SwordAndWoodenShield
import com.example.simplegame.viewmodel.GameViewModel

@Composable
fun EquipStartWeapon(player: Player?, gameViewModel: GameViewModel, navController: NavController) {

    BackHandler(enabled = true) {}

    Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Think about if we needs a "DisplayStats" function
        Text("Name: ${player?.name}")
        Text("Health: ${player?.health} / ${player?.maxHealth}")
        Text("Strength: ${player?.strength?.plus(player.weapon?.strength ?: 0)}")
        Text("Defense: ${player?.defense?.plus(player.weapon?.defense ?: 0)}")
        Text("Speed: ${player?.speed?.plus(player.weapon?.speed ?: 0)}")
        if (player?.weapon == null) {
            Text("Weapon: ")
        } else {
            Text("Weapon: ${player.weapon?.name}")
        }
        if (player?.skill1 == null) {
            Text("Skill 1: ")
        } else {
            Text("Skill 1: ${player.skill1?.name}")
        }
        if (player?.skill2 == null) {
            Text("Skill 2: ")
        } else {
            Text("Skill 2: ${player.skill2?.name}")
        }

        Spacer(Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column {
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        gameViewModel.player.value?.equipWeapon(SwordAndWoodenShield())
                        navController.navigate("startEquipSkill")
                    }
                ) {
                    Text(
                        text = "Equip Sword & Wooden Shield",
                        softWrap = true,
                        maxLines = 1
                    )
                }

                Spacer(Modifier.height(8.dp))

                Button(
                    // A little confused as to why doing this max width gives half the row instead of using
                    // 0.5F
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        gameViewModel.player.value?.equipWeapon(Dagger())
                        navController.navigate("startEquipSkill")
                    }
                ) {
                    Text("Equip Dagger")
                }

                Spacer(Modifier.height(8.dp))

                Button(
                    // A little confused as to why doing this max width gives half the row instead of using
                    // 0.5F
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        gameViewModel.player.value?.equipWeapon(LongSword())
                        navController.navigate("startEquipSkill")
                    }
                ) {
                    Text("Equip Long Sword")
                }
            }
        }
    }
}