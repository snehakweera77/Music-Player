package com.example.musicplayer

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlin.system.exitProcess

class NotificationReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        when(intent?.action){
            ApplicationClass.PREVIOUS -> prevNextSong(true, context!!)
            ApplicationClass.PLAY -> if (PlayerActivity.isPlaying) pauseMusic() else playMusic()
            ApplicationClass.NEXT -> prevNextSong(false, context!!)
            ApplicationClass.EXIT ->{
                exitApplication()
            }
        }
    }
    private fun playMusic(){
        PlayerActivity.isPlaying = true
        PlayerActivity.musicService!!.mediaPlayer!!.start()
        PlayerActivity.musicService!!.showNotification(R.drawable.ic_pause_icon)
        PlayerActivity.binding.playPauseBtn.setIconResource(R.drawable.ic_pause_icon)
        NowPlaying.binding.playPauseBtnNP.setIconResource(R.drawable.ic_pause_icon)
    }
    private fun pauseMusic(){
        PlayerActivity.isPlaying = false
        PlayerActivity.musicService!!.mediaPlayer!!.pause()
        PlayerActivity.musicService!!.showNotification(R.drawable.ic_play_icon)
        PlayerActivity.binding.playPauseBtn.setIconResource(R.drawable.ic_play_icon)
        NowPlaying.binding.playPauseBtnNP.setIconResource(R.drawable.ic_play_icon)
    }
    private fun prevNextSong(increment: Boolean, context: Context){
        setSongPosition(increment)
        PlayerActivity.musicService!!.createMediaPlayer()

        Glide.with(context)
            .load(PlayerActivity.musicListPA[PlayerActivity.songPosition].artUri)
            .apply(RequestOptions().placeholder(R.drawable.ic_back_icon).centerCrop())
            .into(PlayerActivity.binding.songImgPA)
        PlayerActivity.binding.songNamePA.text = PlayerActivity.musicListPA[PlayerActivity.songPosition].title
        Glide.with(context)
            .load(PlayerActivity.musicListPA[PlayerActivity.songPosition].artUri)
            .apply(RequestOptions().placeholder(R.drawable.ic_back_icon).centerCrop())
            .into(PlayerActivity.binding.songImgPA)
        NowPlaying.binding.songNameNP.text = PlayerActivity.musicListPA[PlayerActivity.songPosition].title

        playMusic()
        PlayerActivity.fIndex = favouriteChecker(PlayerActivity.musicListPA[PlayerActivity.songPosition].id)
        if (PlayerActivity.isFavourite) PlayerActivity.binding.favouriteBtnPA.setImageResource(R.drawable.ic_favorite_icon)
        else PlayerActivity.binding.favouriteBtnPA.setImageResource(R.drawable.ic_favorite_empty_icon)

    }

}