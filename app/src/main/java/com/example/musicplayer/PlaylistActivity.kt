package com.example.musicplayer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.recyclerview.widget.GridLayoutManager
import com.example.musicplayer.databinding.ActivityPlaylistBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class PlaylistActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPlaylistBinding
    private lateinit var adapter: PlaylistViewAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.coolPink)
        binding = ActivityPlaylistBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.playlistRV.setHasFixedSize(true)
        binding.playlistRV.setItemViewCacheSize(13)
        binding.playlistRV.layoutManager = GridLayoutManager(this, 2)
        val tempList = ArrayList<String>()
        tempList.add("1")
        tempList.add("2")
        tempList.add("3")
        tempList.add("4")
        tempList.add("5")
        adapter = PlaylistViewAdapter(this, tempList)
        binding.playlistRV.adapter = adapter
        binding.backBtnPLA.setOnClickListener {
            finish()
        }
        binding.addPlaylistBtn.setOnClickListener{
            customAlertDialog()
        }
    }
    private fun customAlertDialog(){
        val customDialog = LayoutInflater.from(this).inflate(R.layout.add_playlist_dialog, binding.root, false)
        val builder = MaterialAlertDialogBuilder(this)
        builder.setView(customDialog)
            .setTitle("Playlist Details")
            .setPositiveButton("Add"){dialog, _ ->
                dialog.dismiss()
            }.show()
    }
}