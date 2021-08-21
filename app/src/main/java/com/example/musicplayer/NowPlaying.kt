package com.example.musicplayer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.musicplayer.databinding.FragmentNowPlayingBinding

class NowPlaying : Fragment() {
    companion object {
        lateinit var  binding: FragmentNowPlayingBinding
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_now_playing, container, false)
        binding = FragmentNowPlayingBinding.bind(view)
        binding.root.visibility = View.INVISIBLE
        binding.playPauseBtnNP.setOnClickListener{
            if (PlayerActivity.isPlaying)
                pauseMusic()
            else
                playMusic()
        }
        binding.nextBtnNP.setOnClickListener {
            setSongPosition(true)
            PlayerActivity.musicService!!.createMediaPlayer()
            Glide.with(this)
                .load(PlayerActivity.musicListPA[PlayerActivity.songPosition].artUri)
                .apply(RequestOptions().placeholder(R.drawable.ic_back_icon).centerCrop())
                .into(PlayerActivity.binding.songImgPA)
            binding.songNameNP.text = PlayerActivity.musicListPA[PlayerActivity.songPosition].title
            PlayerActivity.musicService!!.showNotification(R.drawable.ic_pause_icon)

            playMusic()
        }
        return view
    }

    override fun onResume() {
        super.onResume()
        if (PlayerActivity.musicService != null) {
            binding.root.visibility = View.VISIBLE
            binding.songNameNP.isSelected = true
            Glide.with(this)
                .load(PlayerActivity.musicListPA[PlayerActivity.songPosition].artUri)
                .apply(RequestOptions().placeholder(R.drawable.ic_back_icon).centerCrop())
                .into(PlayerActivity.binding.songImgPA)
        binding.songNameNP.text = PlayerActivity.musicListPA[PlayerActivity.songPosition].title
            if (PlayerActivity.isPlaying)
                binding.playPauseBtnNP.setIconResource(R.drawable.ic_pause_icon)
            else
                binding.playPauseBtnNP.setIconResource(R.drawable.ic_play_icon)
        }
    }
    private fun playMusic() {
        PlayerActivity.musicService!!.mediaPlayer!!.start()
        binding.playPauseBtnNP.setIconResource(R.drawable.ic_pause_icon)
        PlayerActivity.musicService!!.showNotification(R.drawable.ic_pause_icon)
        PlayerActivity.binding.nextBtn.setIconResource(R.drawable.ic_pause_icon)
        PlayerActivity.isPlaying = true
    }
    private fun pauseMusic() {
        PlayerActivity.musicService!!.mediaPlayer!!.pause()
        binding.playPauseBtnNP.setIconResource(R.drawable.ic_play_icon)
        PlayerActivity.musicService!!.showNotification(R.drawable.ic_play_icon)
        PlayerActivity.binding.nextBtn.setIconResource(R.drawable.ic_play_icon)
        PlayerActivity.isPlaying = false
    }


}