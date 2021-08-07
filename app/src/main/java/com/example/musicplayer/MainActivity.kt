package com.example.musicplayer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.musicplayer.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_MusicPlayer)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.shuffleBtn.setOnClickListener{
            startActivity(Intent(this@MainActivity, PlayerActivity::class.java))
        }
        binding.favouriteBtn.setOnClickListener{
            startActivity(Intent(this@MainActivity, FavouriteActivity::class.java))
        }
        binding.playlistBtn.setOnClickListener{
            startActivity(Intent(this@MainActivity, PlaylistActivity::class.java))
        }
    }
}