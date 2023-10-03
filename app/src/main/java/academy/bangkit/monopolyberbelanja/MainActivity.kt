package academy.bangkit.monopolyberbelanja

import academy.bangkit.monopolyberbelanja.databinding.ActivityMainBinding
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupView()

        setupBgMusic()

        binding.btnStart.setOnClickListener{
            Intent(this@MainActivity, MenuActivity::class.java).also {
                startActivity(it)
            }
        }

        binding.btnExit.setOnClickListener{
            finish()
            exitProcess(0)
        }

    }

    private fun setupBgMusic() {
        val action = "PLAY"
        val myService = Intent(this@MainActivity, BackgroundSoundService::class.java)
        myService.action = action
        startService(myService)

        if(isBgMusicPlay){
            showPlayButton(true)
        }
        else{
            showPlayButton(false)
        }

        binding.play.setOnClickListener {
            val action = "STOP"
            val myService = Intent(this@MainActivity, BackgroundSoundService::class.java)
            myService.action = action
            startService(myService)

            showPlayButton(false)
            isBgMusicPlay = false
        }

        binding.mute.setOnClickListener {
            val action = "PLAY"
            val myService = Intent(this@MainActivity, BackgroundSoundService::class.java)
            myService.action = action
            startService(myService)

            showPlayButton(true)
            isBgMusicPlay = true
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

    override fun onResume() {
        super.onResume()
        if(isBgMusicPlay){
            showPlayButton(true)
        }
        else{
            showPlayButton(false)
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