package com.example.musicplayer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.example.musicplayer.databinding.ActivityPlaylistBinding
import com.example.musicplayer.databinding.AddPlaylistDialogBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class PlaylistActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPlaylistBinding
    private lateinit var adapter: PlaylistViewAdapter

    companion object {
        var musicPlaylist: MusicPlaylist = MusicPlaylist()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(MainActivity.currentTheme[MainActivity.themeIndex])
        binding = ActivityPlaylistBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.playlistRV.setHasFixedSize(true)
        binding.playlistRV.setItemViewCacheSize(13)
        binding.playlistRV.layoutManager = GridLayoutManager(this, 2)
        adapter = PlaylistViewAdapter(this, musicPlaylist.ref)
        binding.playlistRV.adapter = adapter
        binding.backBtnPLA.setOnClickListener {
            finish()
        }
        binding.addPlaylistBtn.setOnClickListener {
            customAlertDialog()
        }
    }

    private fun customAlertDialog() {
        val customDialog =
            LayoutInflater.from(this).inflate(R.layout.add_playlist_dialog, binding.root, false)
        val binder = AddPlaylistDialogBinding.bind(customDialog)
        val builder = MaterialAlertDialogBuilder(this)
        builder.setView(customDialog)
            .setTitle("Playlist Details")
            .setPositiveButton("Add") { dialog, _ ->
                val playlistName = binder.playlistName.text
                val createdBy = binder.yourName.text
                if (playlistName != null && createdBy != null) {
                    if (playlistName.isNotEmpty() && createdBy.isNotEmpty()) {
                        addPlaylist(playlistName.toString(), createdBy.toString())
                    }
                }
                dialog.dismiss()
            }.show()
    }

    private fun addPlaylist(name: String, createdBy: String) {
        var playlistExists = false
        for (i in musicPlaylist.ref) {
            if (name.equals(i.name)) {
                playlistExists = true
                break
            }
        }
        if (playlistExists)
            Toast.makeText(this, "Playlist Exists", Toast.LENGTH_SHORT).show()
        else {
            val tempList = Playlist()
            tempList.name = name
            tempList.playlist = ArrayList()
            tempList.createdBy = createdBy
            val calendar = Calendar.getInstance().time
            val sdf = SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH)
            tempList.createdOn = sdf.format(calendar)
            musicPlaylist.ref.add(tempList)
            adapter.refreshPlaylist()
        }
    }

    override fun onResume() {
        super.onResume()
        adapter.notifyDataSetChanged()
    }
}