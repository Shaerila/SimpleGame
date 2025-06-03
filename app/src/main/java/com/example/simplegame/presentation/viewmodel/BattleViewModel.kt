package com.example.simplegame.presentation.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.simplegame.domain.model.Monsters
import com.example.simplegame.domain.model.Player
import com.example.simplegame.domain.model.SecondWind
import com.example.simplegame.domain.model.SneakAttack
import com.example.simplegame.domain.usecase.GetRandomMonsterByRarityUseCase
import com.example.simplegame.presentation.nav.PostBattleEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import kotlin.random.Random


class BattleViewModel constructor(
    private val getRandomMonsterByRarity: GetRandomMonsterByRarityUseCase
//    navController: NavController
) : ViewModel() {

//    private val _monster = MutableStateFlow<Monsters?>(null)
//    val monster: StateFlow<Monsters?> = _monster.asStateFlow()

    private val _monster = mutableStateOf<Monsters?>(null)
    val monster: State<Monsters?> get() = _monster

    private val _battleLog = mutableStateListOf<String>()
    val battleLog: List<String> get() = _battleLog

    var combatResult = ""


    fun startBattle(player: Player, rarity: Char) {
        val newMonster = getRandomMonsterByRarity(rarity)
        _monster.value = newMonster

        _battleLog.add("A wild ${newMonster?.name} appears!")
        _battleLog.add("${player.name} draws their weapon!")



        player.equipSkill1(SecondWind())
        player.equipSkill2(SneakAttack())

    }

    // Currently combat still continues and makes for weird interation with hp.
    fun combat(player: Player, monster: Monsters){
        val m = _monster.value ?: return

        var battleResult: String = ""

        if (player.speed > monster.speed){
            playerAttack(player)
            if (m.health > 0){
                monsterAttack(player)
            } else {
                battleResult = "WIN"
            }
        } else {
            monsterAttack(player)
            if (player.health > 0){
                playerAttack(player)
                if ( m.health <= 0) {
                    battleResult = "WIN"
                    GameViewModel().battlesWon += 1
                }
            } else {
                battleResult = "LOSE"
            }

        }
        combatResult = battleResult
    }

    fun playerAttack(player: Player) {
        val monster = _monster.value ?: return
        val damage = player.strength + (player.weapon?.strength ?: 0)
        val monsterBlock = Random.nextInt((monster.defense / 2).toInt(), monster.defense)
        monster.health -= (damage - monsterBlock)


        _battleLog.add("${player.name} attacks ${monster.name} for ${damage - monsterBlock} damage!")

        // The skill usage for skills 1 and 2
        if (player.skill1 != null && Random.nextDouble(0.0, 100.0) <= (player.skill1?.procRate ?: 0.0)){
            player?.let {
//                it.skill1?.skillProc(player, monster)
                val usedSkill = it.skill1?.skillProc(player, monster)
                if (usedSkill != null) {
                    _battleLog.add(usedSkill)
                }
            }
        }
        if (player.skill2 != null && Random.nextDouble(0.0, 100.0) <= (player.skill2?.procRate ?: 0.0)){
            player?.let {
                val usedSkill = it.skill2?.skillProc(player, monster)
                if (usedSkill != null) {
                    _battleLog.add(usedSkill)
                }
            }
        }

        // Updates the numbers on screens for Monsters. Needed to trigger the "?livedata?" to update
        // in viewmodel so it can update the screen.
        val updated = Monsters(
            name = monster.name,
            health = monster.health,
            maxHealth = monster.maxHealth,
            strength = monster.strength,
            defense = monster.defense,
            speed = monster.speed,
            rarity = monster.rarity,
            weapon = monster.weapon,
            skill1 = monster.skill1,
            skill2 = monster.skill2,

        )
        _monster.value = updated

        if (monster.health <= 0) {
            _battleLog.add("${monster.name} is defeated!")
//            navController.navigate("battle")
            // put navigation code to go to next screen

//            PostBattleEvent(gameViewModel = , navController = navController)
        }
    }

    private fun monsterAttack(player: Player) {
        val m = _monster.value ?: return
        val damage = m.strength + (m.weapon?.strength ?: 0)
        val playerBlock = Random.nextInt((player.defense / 2).toInt(), player.defense)
        player.health -= (damage - playerBlock)


        _battleLog.add("${m.name} strikes for ${damage - playerBlock} damage!")

        if (m.skill1 != null && Random.nextDouble(0.0, 100.0) <= (m.skill1?.procRate ?: 0.0)){
            m?.let {
                val usedSkill = it.skill1?.skillProc(player, m)
                if (usedSkill != null) {
                    _battleLog.add(usedSkill)
                }
            }
        }
        if (m.skill2 != null && Random.nextDouble(0.0, 100.0) <= (m.skill2?.procRate ?: 0.0)){
            m?.let {
                val usedSkill = it.skill2?.skillProc(player, m)
                if (usedSkill != null) {
                    _battleLog.add(usedSkill)
                }
            }
        }

        if (player.health <= 0) {
            _battleLog.add("${m.name} has defeated ${player.name}!")
        } else {
//            monsterAttack(player)
        }
    }
}