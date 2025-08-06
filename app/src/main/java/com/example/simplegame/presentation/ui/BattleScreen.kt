package com.example.simplegame.presentation.ui

import android.annotation.SuppressLint
import android.os.Handler
import android.os.Looper
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
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.simplegame.application.usecase.GetRandomMonsterByRarityUseCase
import com.example.simplegame.presentation.helper.BattleViewModelFactory
import com.example.simplegame.presentation.helper.GameDelay
import com.example.simplegame.presentation.helper.SoundManager
import com.example.simplegame.viewmodel.BattleViewModel
import com.example.simplegame.viewmodel.GameViewModel


@SuppressLint("UnrememberedGetBackStackEntry")
@Composable
fun BattleScreen(
    gameViewModel: GameViewModel,
    getRandomMonsterByRarity: GetRandomMonsterByRarityUseCase, // pass manually or mock
    navController: NavController
) {
    // added to help with tieing the BattleViewModel to the BattleScreen so VM deletes when screen goes to
    val backStackEntry = remember { navController.getBackStackEntry("battle") }

    // (???) Get the context of the view/composable we are using.
    val context = LocalContext.current

    /* (???) I know im using remember to keep the state, but im not quiet sure why i need to keep
     the state for the sound manager here  */
    val soundManager = remember { SoundManager(context) }

    var showNextBattleButton by remember { mutableStateOf(false) }

    // Create factory so we can have different instances of battleViewModel created. Implemented this
    // cause onCleared triggering so was a possible assist to fix
    val battleViewModel: BattleViewModel = viewModel(
        viewModelStoreOwner = backStackEntry,
        factory = BattleViewModelFactory(getRandomMonsterByRarity, gameViewModel)
    )


    // Enable and disable button after click
    var isEnabled by battleViewModel.isEnabled


    BackHandler(enabled = true) {}

    val player = gameViewModel.player.value
    val monster = battleViewModel.monster.value
    val log = battleViewModel.battleLog

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
        battleViewModel.playBattleMusic(context)
        battleViewModel.setMusicVolume(.2f)
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
                Text("Strength: ${player?.strength?.plus(player.weapon?.strength ?: 0)}")
                Text("Defense: ${player?.defense?.plus(player.weapon?.defense ?: 0)}")
                Text("Speed: ${player?.speed?.plus(player.weapon?.speed ?: 0)}")
                if (player?.weapon?.name == null) {
                    Text("Weapon: ")
                } else {
                    Text("Weapon: ${player.weapon?.name}")
                }
                if (player?.skill1?.name == null) {
                    Text("Skill 1: ")
                } else {
                    Text("Skill 1: ${player.skill1?.name}")
                }
                if (player?.skill2?.name == null) {
                    Text("Skill 2: ")
                } else {
                    Text("Skill 2: ${player.skill2?.name}")
                }
            }

            Column(
                modifier = Modifier.padding(4.dp),
                horizontalAlignment = Alignment.Start
            ) {

                Text("Name: ${monster?.name}")
                Text("Health: ${monster?.health} / ${monster?.maxHealth}")
                Text("Strength: ${monster?.strength?.plus(monster.weapon?.strength ?: 0)}")
                Text("Defense: ${monster?.defense?.plus(monster.weapon?.defense ?: 0)}")
                Text("Speed: ${monster?.speed?.plus(monster.weapon?.speed ?: 0)}")
                if (monster?.weapon?.name == null) {
                    Text("Weapon: ")
                } else {
                    Text("Weapon: ${monster.weapon?.name}")
                }
                if (monster?.skill1?.name == null) {
                    Text("Skill 1: ")
                } else {
                    Text("Skill 1: ${monster.skill1?.name}")
                }
                if (monster?.skill2?.name == null) {
                    Text("Skill 2: ")
                } else {
                    Text("Skill 2: ${monster.skill2?.name}")
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
                        // Play sound for attack and then have a small game delay to let sound play th
                        // then show player wants going on.
                        isEnabled = false

                        soundManager.playAttackSound()
                        battleViewModel.disableActionTemporarily(1000)
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
//
                                } else {
                                    // Navigate to the your lose screen
                                    isEnabled = false
                                }
                            }
                        }
                    },
                    enabled = isEnabled
                ) {
                    Text("ATTACK")
                }
            }

            // When the battle ends, the attack button will change to move to next screen
            if (showNextBattleButton) {

                // Delay to allow game to handle stuff in background (This is needs a lil time to
                // properly allow battlesWon to increase)
                Button(
                    modifier = Modifier.padding(16.dp),
                    onClick = {
                        isEnabled = false
                        battleViewModel.disableActionTemporarily(1000)
                        showNextBattleButton = false // hide button again
                        player?.let {
                            // (EDIT) This is needing to change when we get special events ready
//                            navController.navigate("battle")
                            if (gameViewModel.battlesWon >= 20) {
                                battleViewModel.startBattle(it, 'A')
                            } else if (gameViewModel.battlesWon >= 10) {
                                battleViewModel.startBattle(it, 'X')
                                battleViewModel.stopBattleMusic()
                                battleViewModel.playBossMusic(context)
                                battleViewModel.setMusicVolume(.2f)
                            } else if (gameViewModel.battlesWon >= 5) {
                                battleViewModel.startBattle(it, 'C')
                            } else {
                                battleViewModel.startBattle(it, 'D')
                            }

                        }
                    },
                    enabled = isEnabled
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
