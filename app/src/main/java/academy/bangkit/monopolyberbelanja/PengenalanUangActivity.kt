package academy.bangkit.monopolyberbelanja

import academy.bangkit.monopolyberbelanja.databinding.ActivityPengenalanUangBinding
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity


class PengenalanUangActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPengenalanUangBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPengenalanUangBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupBgMusic()

        val seribu: MediaPlayer = MediaPlayer.create(this@PengenalanUangActivity, R.raw.seribu)
        val duaribu: MediaPlayer = MediaPlayer.create(this@PengenalanUangActivity, R.raw.duaribu)
        val limaribu: MediaPlayer = MediaPlayer.create(this@PengenalanUangActivity, R.raw.limaribu)
        val sepuluhribu: MediaPlayer = MediaPlayer.create(this@PengenalanUangActivity, R.raw.sepuluhribu)
        val duapuluhribu: MediaPlayer = MediaPlayer.create(this@PengenalanUangActivity, R.raw.duapuluhribu)
        val limapuluhribu: MediaPlayer = MediaPlayer.create(this@PengenalanUangActivity, R.raw.limapuluhribu)
        val seratusribu: MediaPlayer = MediaPlayer.create(this@PengenalanUangActivity, R.raw.seratusribu)

        binding.seribu.setOnClickListener {
            seribu.start()
        }
        binding.duaribu.setOnClickListener {
            duaribu.start()
        }
        binding.limaribu.setOnClickListener {
            limaribu.start()
        }
        binding.sepuluhribu.setOnClickListener {
            sepuluhribu.start()
        }
        binding.duapuluhribu.setOnClickListener {
            duapuluhribu.start()
        }
        binding.limapuluhribu.setOnClickListener {
            limapuluhribu.start()
        }
        binding.seratusribu.setOnClickListener {
            seratusribu.start()
        }
        binding.btnNext.setOnClickListener {
            Intent(this, PengenalanKoinActivity::class.java).also {
                startActivity(it)
                finish()
            }
        }
        binding.btnPrev.setOnClickListener {
            Intent(this, MenuActivity::class.java).also {
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