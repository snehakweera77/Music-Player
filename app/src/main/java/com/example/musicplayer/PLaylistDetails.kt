package com.example.musicplayer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.musicplayer.databinding.ActivityPlaylistDetailsBinding

class PLaylistDetails : AppCompatActivity() {
    lateinit var binding: ActivityPlaylistDetailsBinding
    lateinit var adapter: MusicAdapter
    companion object {
        var currentPlaylistPos: Int = -1
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.coolPink)
        binding = ActivityPlaylistDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        currentPlaylistPos = intent.extras?.get("index") as Int
        binding.playlistDetailsRV.setItemViewCacheSize(10)
        binding.playlistDetailsRV.setHasFixedSize(true)
        binding.playlistDetailsRV.layoutManager = LinearLayoutManager(this)
        PlaylistActivity.musicPlaylist.ref[currentPlaylistPos].playlist.shuffle()
        adapter = MusicAdapter(this, PlaylistActivity.musicPlaylist.ref[currentPlaylistPos].playlist, pLaylistDetails = true)
        binding.playlistDetailsRV.adapter = adapter
        binding.backBtnPLA.setOnClickListener {  finish()}
    }

    override fun onResume() {
        super.onResume()
        binding.playlistNamePD.text = PlaylistActivity.musicPlaylist.ref[currentPlaylistPos].name
        binding.moreInfoPD.text = "Total ${adapter.itemCount} songs. \n\n" +
                "Created On:\n${PlaylistActivity.musicPlaylist.ref[currentPlaylistPos].createdOn}\n\n" +
                " -- ${PlaylistActivity.musicPlaylist.ref[currentPlaylistPos].createdBy}"
        if (adapter.itemCount > 0) {
            Glide.with(this)
                .load(PlaylistActivity.musicPlaylist.ref[currentPlaylistPos].playlist[0].artUri)
                .apply(RequestOptions().placeholder(R.drawable.ic_back_icon).centerCrop())
                .into(binding.playlistImgPD)
            binding.shuffleBtnPD.visibility = View.VISIBLE
        }

    }
}