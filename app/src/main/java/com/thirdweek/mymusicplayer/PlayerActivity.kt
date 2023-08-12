package com.thirdweek.mymusicplayer

import android.media.MediaPlayer
import android.media.audiofx.Visualizer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import com.chibde.visualizer.BarVisualizer
import com.thirdweek.mymusicplayer.databinding.ActivityPlayerBinding
import java.io.File

class PlayerActivity : AppCompatActivity() {
    private lateinit var binding : ActivityPlayerBinding
    private lateinit var btnPlay : Button
    private lateinit var btnNext : Button
    private lateinit var btnPrevious : Button
    private lateinit var btnRewind : Button
    private lateinit var btnForward : Button
    private lateinit var songName : TextView
    private lateinit var imageSng : ImageView
    private lateinit var visualizer: BarVisualizer
    private lateinit var seekSongBar : SeekBar
    private lateinit var songStart : TextView
    private lateinit var songEnd : TextView
    private lateinit var mediaPlayer : MediaPlayer


     var EXTRA_NAME :String = ""
    var positions : Int = 0
    var mySongs : ArrayList<File> = arrayListOf()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        btnPlay = binding.pause
        btnNext = binding.next
        btnRewind = binding.rewind
        btnPrevious = binding.previous
        btnForward = binding.forward
        songName = binding.txtSong
        imageSng = binding.img
        visualizer = binding.wave
        seekSongBar = binding.seekBar
        songStart = binding.txtSongStart
        songEnd = binding.txtSongEnd

        if(mediaPlayer != null){
            mediaPlayer.start()
            mediaPlayer.release()
        }

        val songTitle :String =intent.getStringExtra("SongName") ?: ""

    }
}