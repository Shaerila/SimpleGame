package com.example.simplegame.presentation.ui

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.simplegame.viewmodel.GameViewModel

@Composable
fun PostBattleEvent(
    gameViewModel: GameViewModel,
    navController: NavController
    ) {

    BackHandler(enabled = true) {}


    navController.navigate("batt")
//    Button() {
//
//    }


}