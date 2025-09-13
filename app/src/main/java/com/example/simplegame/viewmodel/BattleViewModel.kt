package com.example.simplegame.viewmodel


import android.content.Context
import android.media.MediaPlayer
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simplegame.R
import com.example.simplegame.domain.model.Monsters
import com.example.simplegame.domain.model.Player
import com.example.simplegame.application.usecase.GetRandomMonsterByRarityUseCase
import com.example.simplegame.application.usecase.RandomNumberGenerator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random


class BattleViewModel (
    private val getRandomMonsterByRarity: GetRandomMonsterByRarityUseCase,
    private val gameViewModel: GameViewModel

//    navController: NavController
) : ViewModel() {

//    private val _monster = MutableStateFlow<Monsters?>(null)
//    val monster: StateFlow<Monsters?> = _monster.asStateFlow()

    private val _monster = mutableStateOf<Monsters?>(null)
    val monster: State<Monsters?> get() = _monster

    private val _battleLog = mutableStateListOf<String>()
    val battleLog: List<String> get() = _battleLog

    private val _isEnabled = mutableStateOf<Boolean>(true)
    val isEnabled: MutableState<Boolean> get() = _isEnabled


    var combatResult = ""

    var turnNumber = 1

    private lateinit var mediaPlayer: MediaPlayer

    //For playing audio
    fun playBattleMusic(context: Context){
        mediaPlayer = MediaPlayer.create(context, R.raw.chiptune_background_battle_music).apply {
            isLooping = true
            start()
        }
    }

    fun playBossMusic (context: Context){
        mediaPlayer = MediaPlayer.create(context, R.raw.oresuki_bench).apply {
            isLooping = true
            start()
        }
    }

    fun setMusicVolume(volume: Float) {
        mediaPlayer.setVolume(volume, volume)
    }


    fun stopBattleMusic(){
        mediaPlayer.stop()
        mediaPlayer.release()
    }


    fun startBattle(player: Player, rarity: Char) {
        Log.d("BattleViewModel","battlesWon = ${gameViewModel.battlesWon}")
        _battleLog.clear()

        turnNumber = 1

        val newMonster = getRandomMonsterByRarity(rarity)
        _monster.value = newMonster

        _battleLog.add("A wild ${newMonster?.name} appears!")
        _battleLog.add("${player.name} draws their weapon!")
    }

    // Currently combat still continues and makes for weird interaction with hp.
    fun combat(player: Player, monster: Monsters) {
        _battleLog.add("(- - - -Turn ${turnNumber}- - - -)")
        val m = _monster.value ?: return

        var battleResult: String = ""

        if (player.speed > monster.speed) {
            playerAttack(player)
            if (m.health > 0) {
                monsterAttack(player)
            } else {
                battleResult = "WIN"
                gameViewModel.battlesWon += 1
            }
        } else {
            monsterAttack(player)
            if (player.health > 0) {
                playerAttack(player)
                if (m.health <= 0) {
                    battleResult = "WIN"
                    gameViewModel.battlesWon += 1
                }
            } else {
                battleResult = "LOSE"
            }

        }
        combatResult = battleResult
        turnNumber += 1
    }

    fun playerAttack(player: Player) {
        val monster = _monster.value ?: return
        var baseDamage = player.strength + (player.weapon?.strength ?: 0)
        val monsterBlock = Random.nextInt((monster.defense / 2).toInt(), monster.defense)
        val finalDamage = (baseDamage - monsterBlock).coerceAtLeast(0)

//        val currentMonsterHealth = monster.health - finalDamage

        if(finalDamage <= 0) {
            _battleLog.add("${player.name} strikes but ${monster.name} completely blocks that damage!")
        } else {
            monster.health -= finalDamage
            _battleLog.add("${player.name} strikes for ${finalDamage} damage!")
            Log.d("BattleViewModel", "${player.name} strikes for ${baseDamage} - ${monsterBlock} damage!")
        }


        // The skill usage for skills 1 and 2
        if (player.skill1 != null && Random.nextDouble(0.0, 100.0) <= (player.skill1?.procRate
                ?: 0.0)
        ) {
            player.let {
        //                it.skill1?.skillProc(player, monster)
                val usedSkill = it.skill1?.skillProc(player, monster)
                if (usedSkill != null) {
                    _battleLog.add(usedSkill)
                }
            }
        }
        if (player.skill2 != null && Random.nextDouble(0.0, 100.0) <= (player.skill2?.procRate
                ?: 0.0)
        ) {
            player.let {
                val usedSkill = it.skill2?.skillProc(player, monster)
                if (usedSkill != null) {
                    _battleLog.add(usedSkill)
                }
            }
        }

        /* Updates the numbers on screens for Monsters. Needed to trigger the "?livedata?" to update
        // in viewmodel so it can update the screen.

        We should probably find a better way to trigger recomposition here to update the monsters
        when they update to screen.
        */

        if (monster.health <= 0) {
            _battleLog.add("${monster.name} is defeated!")
            monster.health = 0
            if (player.health + 20 >= player.maxHealth){
                player.health = player.maxHealth
            } else {
                player.health += 20
            }
        }
    }

    private fun monsterAttack(player: Player) {
        val m = _monster.value ?: return
        var damage = m.strength + (m.weapon?.strength ?: 0)
        val playerBlock = Random.nextInt((player.defense / 2).toInt(), player.defense)
        if ((damage - playerBlock) <= 0) {
            damage = 0
            _battleLog.add("${m.name} strikes but their damage was completely blocked!")
        } else {
            player.health -= (damage - playerBlock)
            _battleLog.add("${m.name} strikes for ${damage - playerBlock} damage!")
        }





        if (m.skill1 != null && Random.nextDouble(0.0, 100.0) <= (m.skill1?.procRate ?: 0.0)) {
            m.let {
                val usedSkill = it.skill1?.skillProc(player, m)
                if (usedSkill != null) {
                    _battleLog.add(usedSkill)
                }
            }
        }
        if (m.skill2 != null && Random.nextDouble(0.0, 100.0) <= (m.skill2?.procRate ?: 0.0)) {
            m.let {
                val usedSkill = it.skill2?.skillProc(player, m)
                if (usedSkill != null) {
                    _battleLog.add(usedSkill)
                }
            }
        }

        if (player.health <= 0) {
            player.health = 0
            _battleLog.add("${m.name} has defeated you!")
            stopBattleMusic()
            gameViewModel.playerAlive = false

        } else {
//            monsterAttack(player)
        }
    }
    fun levelUpRate (num: Int): Int{
        if(num <= 6){
            return 1
        } else if(num > 6 && num <= 9){
            return 2
        } else {
            return 3
        }
    }

    fun levelUp(player: Player) {

        val maxHealthIncrease = RandomNumberGenerator().randomNumberTo5()
        val strengthIncrease = levelUpRate(RandomNumberGenerator().randomNumberTo10())
        val defenseIncrease = levelUpRate(RandomNumberGenerator().randomNumberTo10())
        val speedIncrease = levelUpRate(RandomNumberGenerator().randomNumberTo10())


        player.maxHealth += maxHealthIncrease
        player.strength += strengthIncrease
        player.defense += defenseIncrease
        player.speed += speedIncrease


        _battleLog.add("${player.name} has grown from their battle!")
        _battleLog.add("${player.name} max health has increased by $maxHealthIncrease")
        _battleLog.add("${player.name} strength has increased by $strengthIncrease")
        _battleLog.add("${player.name} defense has increased by $defenseIncrease")
        _battleLog.add("${player.name} speed has increased by $speedIncrease")

    }


    fun disableActionTemporarily(delayMillis: Long) {
        _isEnabled.value = false
        viewModelScope.launch(Dispatchers.IO) {
            delay(delayMillis)
            _isEnabled.value = true
        }
        Handler(Looper.getMainLooper()).postDelayed({
        }, delayMillis)
    }

    override fun onCleared() {
        super.onCleared()

        Log.d("BattleViewModel", "onCleared called — ViewModel is being destroyed.")

    }
}