package com.example.simplegame

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.simplegame.data.repo.MonsterRepositoryImpl
import com.example.simplegame.presentation.viewmodel.GameViewModel
import com.example.simplegame.domain.usecase.GetRandomMonsterByRarityUseCase
import com.example.simplegame.domain.usecase.RandomNumberGenerator
import com.example.simplegame.presentation.ui.BattleScreen
import com.example.simplegame.presentation.ui.PlayerSetupScreen
import com.example.simplegame.presentation.ui.PlayerStatsScreen


class MainActivity : ComponentActivity() {

    // Use this to get the players stats that were stored via observer in the GameViewModel
    // once the "PlayerSetupScreen" function has been called in the "onCreate" function
//    val player = gameViewModel.player.value

    val monsterRepo = MonsterRepositoryImpl()
    val getRandomMonsterByRarity = GetRandomMonsterByRarityUseCase(monsterRepo)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val gameViewModel: GameViewModel by viewModels()


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
                if ( RandomNumberGenerator().randomNumberTo10() >= 5 ){

                } else {

                }
            }
        }
    }
}



