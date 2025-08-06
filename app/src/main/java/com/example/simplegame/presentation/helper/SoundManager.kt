package com.example.simplegame.presentation.helper
import android.content.Context
import android.media.AudioAttributes
import android.media.SoundPool
import com.example.simplegame.R
import com.example.simplegame.application.usecase.RandomNumberGenerator


// Sound is for sound effect that play in game
class SoundManager(context: Context) {

    private val audioAttributes = AudioAttributes.Builder()
        .setUsage(AudioAttributes.USAGE_GAME)
        .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
        .build()

    private val soundPool = SoundPool.Builder()
        .setMaxStreams(5)
        .setAudioAttributes(audioAttributes)
        .build()

    private val attackSound1Id: Int
    private val attackSound2Id: Int


    init {
        attackSound1Id = soundPool.load(context, R.raw.attack_sound_1, 1)
        attackSound2Id = soundPool.load(context, R.raw.attack_sound_2, 1)

    }

    fun playAttackSound() {
        if(RandomNumberGenerator().randomNumberTo10() >= 5) {
            soundPool.play(attackSound1Id, 1f, 1f, 1, 0, 1f)
        } else {
            soundPool.play(attackSound2Id, 1f, 1f, 1, 0, 1f)
        }
    }

    fun release() {
        soundPool.release()
    }
}