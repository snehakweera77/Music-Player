package com.example.musicplayer

import android.app.Notification
import android.app.Service
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import android.support.v4.media.session.MediaSessionCompat
import androidx.core.app.NotificationCompat

class MusicService:Service() {
    private var myBinder = MyBinder()
    var mediaPlayer:MediaPlayer?=null
    private lateinit var mediaSession: MediaSessionCompat
    override fun onBind(p0: Intent?): IBinder? {
        mediaSession = MediaSessionCompat(baseContext, "My Music")
        return myBinder
    }
    inner class MyBinder:Binder(){
        fun currentService():MusicService{
            return this@MusicService
        }
    }
    fun showNotification(){
        val notification = NotificationCompat.Builder(baseContext, ApplicationClass.CHANNEL_ID)
            .setContentTitle(PlayerActivity.musicListPA[PlayerActivity.songPosition].title)
            .setContentText(PlayerActivity.musicListPA[PlayerActivity.songPosition].artist)
            .setSmallIcon(R.drawable.ic_playlist_icon)
            .setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.splash_screen))
            .setStyle(androidx.media.app.NotificationCompat.MediaStyle().setMediaSession(mediaSession.sessionToken))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setOnlyAlertOnce(true)
            .addAction(R.drawable.ic_previous_icon, "Previous", null)
            .addAction(R.drawable.play_icon, "Play", null)
            .addAction(R.drawable.ic_next_icon, "Next", null)
            .addAction(R.drawable.ic_exit_icon, "Exit", null)
            .build()
        startForeground(5, notification)
    }
}