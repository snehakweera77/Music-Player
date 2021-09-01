package com.example.musicplayer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.musicplayer.databinding.ActivityAboutBinding

class AboutActivity : AppCompatActivity() {
    lateinit var binding: ActivityAboutBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.coolPinkNav)
        binding = ActivityAboutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = "About"
        binding.aboutText.text = aboutText()
    }
    private fun aboutText(): String{
        return "Developed By: Sneha Kweera" +
                "\n\nIf you want to provide feedback, I will love to hear that."
    }
}