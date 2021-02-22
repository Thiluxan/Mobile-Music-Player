package com.example.myapplication

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.ImageButton
import android.widget.SeekBar

class MainActivity : AppCompatActivity() {
    lateinit var runnable: Runnable
    private var handler = Handler()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var mediaPlayer = MediaPlayer.create(this,R.raw.music)
        var playButton = findViewById<ImageButton>(R.id.play)
        var nextButton = findViewById<ImageButton>(R.id.next)
        var previousButton = findViewById<ImageButton>(R.id.previous)
        var seekBar = findViewById<SeekBar>(R.id.progree)

        seekBar.progress = 0
        seekBar.max = mediaPlayer.duration

        nextButton.setOnClickListener {
            var currentPosition = mediaPlayer.currentPosition
            mediaPlayer.pause()
            mediaPlayer.seekTo(currentPosition+1000)
            mediaPlayer.start()
        }

        previousButton.setOnClickListener {
            var currentPosition = mediaPlayer.currentPosition
            mediaPlayer.pause()
            mediaPlayer.seekTo(currentPosition-1000)
            mediaPlayer.start()
        }

        playButton.setOnClickListener {
            if(!mediaPlayer.isPlaying){
                mediaPlayer.start()
                playButton.setImageResource(R.drawable.ic_baseline_pause_circle_filled_24)
            }
            else{
                mediaPlayer.pause()
                playButton.setImageResource(R.drawable.ic_baseline_play_arrow_24)
            }
        }

        seekBar.setOnSeekBarChangeListener(object:SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if(fromUser){
                    mediaPlayer.seekTo(progress)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }

        })

        runnable = Runnable {
            seekBar.progress = mediaPlayer.currentPosition
            handler.postDelayed(runnable,1000)
        }
        handler.postDelayed(runnable,1000)

        mediaPlayer.setOnCompletionListener{
            playButton.setImageResource(R.drawable.ic_baseline_play_arrow_24)
            seekBar.progress = 0
        }

    }
}