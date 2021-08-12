package com.example.musicplayer

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.musicplayer.databinding.ActivityPlayerBinding

class PlayerActivity : AppCompatActivity() {
    companion object {
        lateinit var musicListPA : ArrayList<Music>
        var songPosition: Int = 0
        var mediaPlayer: MediaPlayer?= null
        var isPlaying: Boolean = false
    }
    private lateinit var binding: ActivityPlayerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.coolPink)
        binding = ActivityPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initializeLayout()
        binding.playPauseBtn.setOnClickListener{
            if (isPlaying == true)
                pauseMusic()
            else
                playMusic()
        }
        binding.previousBtn.setOnClickListener{
            prevNextSong(true)
        }
        binding.nextBtn.setOnClickListener{
            prevNextSong(false)
        }
        }
    private fun setLayout() {
        Glide.with(this)
            .load(musicListPA[songPosition].artUri)
            .apply(RequestOptions().placeholder(R.drawable.ic_back_icon).centerCrop())
            .into(binding.songImgPA)
        binding.songNamePA.text = musicListPA[songPosition].title
    }
    private fun createMediaPlayer(){
        try {
            if (mediaPlayer == null) mediaPlayer = MediaPlayer()
            mediaPlayer!!.reset()
            mediaPlayer!!.setDataSource(musicListPA[songPosition].path)
            mediaPlayer!!.prepare()
            mediaPlayer!!.start()
            isPlaying = true
            binding.playPauseBtn.setIconResource(R.drawable.ic_pause_icon)
        } catch (e: Exception){return}
    }
    private fun initializeLayout(){
        PlayerActivity.songPosition = intent.getIntExtra("index", 0)
        when(intent.getStringExtra("class")){
            "MusicAdapter" -> {
                musicListPA = ArrayList()
                musicListPA.addAll(MainActivity.MusicListMA)
                setLayout()
                createMediaPlayer()
            }
            "MainActivity" -> {
                musicListPA = ArrayList()
                musicListPA.addAll(MainActivity.MusicListMA)
                musicListPA.shuffle()
                setLayout()
                createMediaPlayer()
            }
    }
}
private fun playMusic() {
    binding.playPauseBtn.setIconResource(R.drawable.ic_pause_icon)
    isPlaying = true
    mediaPlayer!!.start()
}

    private fun pauseMusic() {
        binding.playPauseBtn.setIconResource(R.drawable.ic_play_icon)
        isPlaying = false
        mediaPlayer!!.pause()
    }
    private fun prevNextSong(increment: Boolean){
        if (increment){
            setSongPosition(increment)
            setLayout()
            createMediaPlayer()
        }
        else{
            setSongPosition(increment)
            setLayout()
            createMediaPlayer()
        }
    }
    private fun setSongPosition(increment: Boolean){
        if (increment){
            if (musicListPA.size - 1 == songPosition)
                songPosition = 0
            else
                ++songPosition
        }
        else{
            if (0 == songPosition)
                songPosition = musicListPA.size - 1
            else
                --songPosition
        }
    }
}