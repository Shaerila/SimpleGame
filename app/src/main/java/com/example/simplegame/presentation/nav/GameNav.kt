//package com.example.simplegame.presentation.nav
//
//import androidx.compose.material3.Button
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.navigation.NavController
//import androidx.navigation.compose.NavHost
//import androidx.navigation.compose.composable
//import com.example.simplegame.presentation.ui.BattleScreen
//import com.example.simplegame.presentation.ui.PlayerSetupScreen
//import com.example.simplegame.presentation.ui.PlayerStatsScreen
//
//@Composable
//fun GameNav (navController: NavController) {
//
//    NavHost(navController, startDestination = "setup") {
//        composable("setup") {
//            PlayerSetupScreen { name, startingClass ->
//                gameViewModel.createPlayer(
//                    name,
//                    startingClass
//                )
//                navController.navigate("stats")
//            }
//        }
//        composable("stats") {
//            PlayerStatsScreen(
//                player = gameViewModel.player.value,
//                onBack = { navController.popBackStack() }
//            )
//            Button(
//                onClick = { navController.navigate("battle") },
//            ) {
//                Text("Start The Battle")
//                // Add Navigation To Stats Screen
//
//            }
//
//        }
//        composable("battle") {
//            BattleScreen(
//                gameViewModel = gameViewModel,
//                getRandomMonsterByRarity = getRandomMonsterByRarity
//            )
//        }
//    }
//}