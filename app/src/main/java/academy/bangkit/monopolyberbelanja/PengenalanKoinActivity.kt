package academy.bangkit.monopolyberbelanja

import academy.bangkit.monopolyberbelanja.databinding.ActivityPengenalanKoinBinding
import academy.bangkit.monopolyberbelanja.databinding.ActivityPengenalanUangBinding
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager

class PengenalanKoinActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPengenalanKoinBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPengenalanKoinBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupBgMusic()

        val limaratusrupiah: MediaPlayer = MediaPlayer.create(this, R.raw.limaratusrupiah)
        val seribukoin: MediaPlayer = MediaPlayer.create(this, R.raw.seribu)

        binding.limaratusrupiah.setOnClickListener {
            limaratusrupiah.start()
        }
        binding.seribukoin.setOnClickListener {
            seribukoin.start()
        }
        binding.btnPrev.setOnClickListener {
            Intent(this, PengenalanUangActivity::class.java).also {
                startActivity(it)
                finish()
            }
        }

    }

    private fun setupBgMusic() {
        if(isBgMusicPlay){
            showPlayButton(true)
        }
        else{
            showPlayButton(false)
        }

        binding.play.setOnClickListener {
            val action = "STOP"
            val myService = Intent(this, BackgroundSoundService::class.java)
            myService.action = action
            startService(myService)

            showPlayButton(false)
            isBgMusicPlay = false
        }

        binding.mute.setOnClickListener {
            val action = "PLAY"
            val myService = Intent(this, BackgroundSoundService::class.java)
            myService.action = action
            startService(myService)

            showPlayButton(true)
            isBgMusicPlay = true
        }
    }

    override fun onResume() {
        super.onResume()
        if(isBgMusicPlay){
            showPlayButton(true)
        }
        else{
            showPlayButton(false)
        }
    }

    private fun showPlayButton(playing: Boolean){
        if (playing){
            binding.mute.visibility = View.GONE
            binding.play.visibility = View.VISIBLE
        }
        else{
            binding.mute.visibility = View.VISIBLE
            binding.play.visibility = View.GONE
        }

    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }
}