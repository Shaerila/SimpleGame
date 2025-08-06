package com.example.simplegame.application.nav

import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun BattleScreenNav( navController: NavController ) {

    navController.navigate("battle")
}