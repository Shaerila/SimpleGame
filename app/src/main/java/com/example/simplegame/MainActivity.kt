package com.example.simplegame

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.simplegame.data.repo.MonsterRepositoryImpl
import com.example.simplegame.viewmodel.GameViewModel
import com.example.simplegame.application.usecase.GetRandomMonsterByRarityUseCase
import com.example.simplegame.application.usecase.RandomNumberGenerator
import com.example.simplegame.presentation.ui.BattleScreen
import com.example.simplegame.presentation.ui.EquipStartSkill
import com.example.simplegame.presentation.ui.EquipStartWeapon
import com.example.simplegame.presentation.ui.PlayerSetupScreen
import com.example.simplegame.presentation.ui.PlayerStatsScreen


class MainActivity : ComponentActivity() {

    // Use this to get the players stats that were stored via observer in the GameViewModel
    // once the "PlayerSetupScreen" function has been called in the "onCreate" function
//    val player = gameViewModel.player.value

    val monsterRepo = MonsterRepositoryImpl()
    val getRandomMonsterByRarity = GetRandomMonsterByRarityUseCase(monsterRepo)

    val gameViewModel: GameViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {


            val navController = rememberNavController()

            NavHost(navController, startDestination = "setup") {
                composable("setup") {
                    PlayerSetupScreen { name, startingClass ->
                        gameViewModel.createPlayer(
                            name,
                            startingClass
                        )
                        navController.navigate("stats")
                    }
                }
                composable("stats") {
                    PlayerStatsScreen(
                        player = gameViewModel.player.value,
                        onBack = { navController.popBackStack() },
                        navController
                    )

                }
                composable("battle") {
                    BattleScreen(
                        gameViewModel = gameViewModel,
                        getRandomMonsterByRarity = getRandomMonsterByRarity,
                        navController
                    )
                }
                composable("startEquipSkill") {
                    EquipStartSkill(
                        player = gameViewModel.player.value,
                        gameViewModel,
                        navController
                    )
                }

                composable("startEquipWeapon") {
                    EquipStartWeapon(
                        player = gameViewModel.player.value,
                        gameViewModel,
                        navController
                    )
                }
            }
        }
    }
}



