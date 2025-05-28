package com.example.simplegame

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.simplegame.data.repo.MonsterRepositoryImpl
import com.example.simplegame.presentation.viewmodel.GameViewModel
import com.example.simplegame.domain.model.Player
import com.example.simplegame.domain.usecase.GetRandomMonsterByRarityUseCase
import com.example.simplegame.presentation.ui.BattleScreen
import com.example.simplegame.presentation.ui.PlayerSetupScreen
import com.example.simplegame.presentation.ui.PlayerStatsScreen
import com.example.simplegame.presentation.viewmodel.BattleViewModel

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

//            MaterialTheme {
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
            }
        }
    }
}



