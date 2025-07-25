package com.example.simplegame.presentation.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.simplegame.domain.usecase.GetRandomMonsterByRarityUseCase
import com.example.simplegame.domain.usecase.RandomNumberGenerator
import com.example.simplegame.presentation.viewmodel.BattleViewModel
import com.example.simplegame.presentation.viewmodel.GameViewModel


@Composable
fun BattleScreen(
    gameViewModel: GameViewModel,
    getRandomMonsterByRarity: GetRandomMonsterByRarityUseCase, // pass manually or mock
    navController: NavController
) {
    // Might need to change this later as it doesnt survive screen rotation type config changes
    // val viewModel: BattleViewModel = viewModel()
    val battleViewModel = remember {
        BattleViewModel(getRandomMonsterByRarity)
    }

    BackHandler(enabled = true) {}

    val player = gameViewModel.player.value
    val monster = battleViewModel.monster.value
    val log = battleViewModel.battleLog

    var showNextBattleButton by remember { mutableStateOf(false) }

    // For auto-scrolling of logs in lazy column
    // (???) I
    val logState = rememberLazyListState()

    LaunchedEffect(log.size) {
        logState.animateScrollToItem(log.size)
    }

    // Find explanation on this
    LaunchedEffect(Unit) {
        player?.let {
            battleViewModel.startBattle(it, 'D')
        }
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {

        Row(
            modifier = Modifier.padding(16.dp)
        ) {

            Column(
                modifier = Modifier.padding(4.dp),
                horizontalAlignment = Alignment.Start
            ) {


                Text("Name: ${player?.name}")
                Text("Health: ${player?.health} / ${player?.maxHealth}")
                Text("Strength: ${player?.strength}")
                Text("Defense: ${player?.defense}")
                Text("Speed: ${player?.speed}")
                if (player?.weapon?.name == null){
                    Text("Weapon: ")
                } else {
                    Text( "Weapon: ${player?.weapon?.name}")
                }
                if (player?.skill1?.name == null){
                    Text("Skill 1: ")
                } else {
                    Text("Skill 1: ${player?.skill1?.name}")
                }
                if (player?.skill2?.name == null){
                    Text("Skill 2: ")
                } else {
                    Text("Skill 2: ${player?.skill2?.name}")
                }
            }

            Column(
                modifier = Modifier.padding(4.dp),
                horizontalAlignment = Alignment.Start
            ) {

                Text("Name: ${monster?.name}")
                Text("Health: ${monster?.health} / ${monster?.maxHealth}")
                Text("Strength: ${monster?.strength}")
                Text("Defense: ${monster?.defense}")
                Text("Speed: ${monster?.speed}")
                if (monster?.weapon?.name == null){
                    Text("Weapon: ")
                } else {
                    Text("Weapon: ${monster?.weapon?.name}")
                }
                if (monster?.skill1?.name == null){
                    Text("Skill 1: ")
                } else {
                    Text("Skill 1: ${monster?.skill1?.name}")
                }
                if (monster?.skill2?.name == null){
                    Text("Skill 2: ")
                } else {
                    Text("Skill 2: ${monster?.skill2?.name}")
                }
            }
        }

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center

        ) {
            if (showNextBattleButton == false) {
                Button(
                    modifier = Modifier.padding(16.dp),
                    onClick = {
                        if (player != null) {
                            if (monster != null) {
                                battleViewModel.combat(player, monster)

                                // When combat is finished, change screens based on the result of combat
                                if (battleViewModel.combatResult == "WIN") {
                                    battleViewModel.levelUp(player)
                                    showNextBattleButton = true

                                    /* Here we should have a button that allows the user to "confirm" that battle
                                    is over and then is apply logic from the navGraph to go to next battle or
                                    to a special screen based on how the random number thing goes.
                                 */
//                                    navController.navigate("battle")

                                } else {
                                    // Navigate to the your lose screen
                                }
                            }
                        }
                    }
                ) {
                    Text("!ATTACK!")
                }
            }

            // When the battle ends, the attack button will change to move to next screen
            if (showNextBattleButton) {
                Button(
                    modifier = Modifier.padding(16.dp),
                    onClick = {
                        showNextBattleButton = false // hide button again
                        player?.let {
                            // (EDIT) This is needing to change when we get special events ready
//                            navController.navigate("battle")
                            battleViewModel.startBattle(it, 'D')
                        }
                    }
                ) {
                    Text("Press Onward")
                }
            }


            // Display the battle log to the player
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .background(Color.LightGray)
                    .height(400.dp), // Adjust the height as you like
                state = logState,
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom
            ) {

                // Understand this. Its set up to make the player and enemy actions different for understanding.
                items(log) { entry ->
                    val isPlayerAction = entry.contains(player?.name ?: "")
                    Text(
                        text = entry,
                        modifier = Modifier
                            .padding(4.dp)
                            .background(if (isPlayerAction) Color(0xFFD1E8FF) else Color(0xFFFFD1D1))
                            .padding(8.dp),
                        color = if (isPlayerAction) Color.Black else Color.DarkGray
                    )
                }
            }
        }
    }
}