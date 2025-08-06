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
import com.example.simplegame.domain.model.Bash
import com.example.simplegame.domain.model.Player
import com.example.simplegame.domain.model.SecondWind
import com.example.simplegame.domain.model.SneakAttack
import com.example.simplegame.domain.model.SwordAndWoodenShield
import com.example.simplegame.viewmodel.GameViewModel

@Composable
fun EquipStartSkill(player: Player?, gameViewModel: GameViewModel, navController: NavController) {

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
                        gameViewModel.player.value?.equipSkill1(SneakAttack())
                        navController.navigate("battle")

                    }
                ) {
                    Text(
                        text = "Equip Skill Sneak Attack",
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
                        gameViewModel.player.value?.equipSkill1(SecondWind())
                        navController.navigate("battle")
                    }
                ) {
                    Text(
                        text = "Equip Skill Second Wind",
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
                        gameViewModel.player.value?.equipSkill1(Bash())
                        navController.navigate("battle")
                    }
                ) {
                    Text(
                        text = "Equip Skill Bash",
                        softWrap = true,
                        maxLines = 1
                    )
                }
            }
        }

        Spacer(Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "*Sneak Attack*: After attacking, have a chance to deal damage equal to 30% of your speed. Proc Rate = 45%")
        }

        Spacer(Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "*Second Wind*: After attacking, have a chance to heal for 10% of your Max Health. Proc Rate = 20%")
        }

        Spacer(Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "*Bash*: After attacking, have a chance to deal damage equal to 50% of your strength. Proc Rate = 25%")
        }
    }


}