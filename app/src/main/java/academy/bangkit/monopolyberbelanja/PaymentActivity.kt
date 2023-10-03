package academy.bangkit.monopolyberbelanja

import academy.bangkit.monopolyberbelanja.databinding.ActivityPaymentBinding
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.CheckBox
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class PaymentActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPaymentBinding
    private var hargaBarang: Int = 0
    private var uangYangAkanDibayar: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupBgMusic()

        val posisi = intent.getIntExtra(EXTRA_POSISI, 0)
        when (posisi) {
            1 -> hargaBarang = 3000
            2 -> hargaBarang = 6000
            3 -> hargaBarang = 2500
            5 -> hargaBarang = 1000
            6 -> hargaBarang = 10000
            7 -> hargaBarang = 9000
            9 -> hargaBarang = 12000
            10 -> hargaBarang = 5000
            11 -> hargaBarang = 8000
            13 -> hargaBarang = 9500
            14 -> hargaBarang = 3000
            15 -> hargaBarang = 7000
        }

        binding.jmlUang.text = getString(R.string.jumlah_uang, saldo.toString())
        binding.jmlJajanan.text = jmlJajanan.toString()
        binding.hargaBarang.text = getString(R.string.harga_1s, hargaBarang.toString())

        binding.btnBayar.setOnClickListener {
            if (uangYangAkanDibayar > hargaBarang) {
                saldo -= hargaBarang
                val kembalian = uangYangAkanDibayar - hargaBarang
                AlertDialog.Builder(this).apply {
                    setTitle("Pembelian Berhasil!")
                    setMessage("Kembalian: Rp. $kembalian\nSisa Saldo: Rp. $saldo")
                    setPositiveButton("Lanjutkan Bermain") { _, _ ->
                        finish()
                    }
                    create()
                    show()
                }
            }
            else if (uangYangAkanDibayar == hargaBarang) {
                val tepuktangan: MediaPlayer = MediaPlayer.create(this, R.raw.tepuktangan)
                tepuktangan.start()
                AlertDialog.Builder(this).apply {
                    setTitle("Pembelian Berhasil!")
                    setMessage("Keren! kamu berhasil membeli barang dengan uang yang pas\nSisa Saldo: Rp. $saldo")
                    setPositiveButton("Lanjutkan Bermain") { _, _ ->
                        finish()
                    }
                    create()
                    show()
                }
            }

            else{
                Toast.makeText(
                    this,
                    "Uang Anda tidak cukup untuk membeli barang ini",
                    Toast.LENGTH_SHORT
                ).show()
            }
            jmlJajanan += 1
        }

    }

    fun onCheckBoxChecked(view: View) {
        val checkBox: CheckBox = findViewById(view.id)

        when (view.id) {
            R.id.cb_seribu -> {
                if (checkBox.isChecked) {
                    if (saldo > 1000) {
                        uangYangAkanDibayar += 1000
                    } else {
                        Toast.makeText(this, "Saldo anda tidak cukup", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    uangYangAkanDibayar -= 1000

                }
            }
            R.id.cb_duaribu -> {
                if (checkBox.isChecked) {
                    if (saldo > 2000) {
                        uangYangAkanDibayar += 2000
                    } else {
                        Toast.makeText(this, "Saldo anda tidak cukup", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    uangYangAkanDibayar -= 2000
                }
            }
            R.id.cb_limaribu -> {
                if (checkBox.isChecked) {
                    if (saldo > 5000) {
                        uangYangAkanDibayar += 5000
                    } else {
                        Toast.makeText(this, "Saldo anda tidak cukup", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    uangYangAkanDibayar -= 5000
                }
            }
            R.id.cb_sepuluhribu -> {
                if (checkBox.isChecked) {
                    if (saldo > 10000) {
                        uangYangAkanDibayar += 10000
                    } else {
                        Toast.makeText(this, "Saldo anda tidak cukup", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    uangYangAkanDibayar -= 10000
                }
            }
            R.id.cb_duapuluhribu -> {
                if (checkBox.isChecked) {
                    if (saldo > 20000) {
                        uangYangAkanDibayar += 20000
                    } else {
                        Toast.makeText(this, "Saldo anda tidak cukup", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    uangYangAkanDibayar -= 20000
                }
            }
            R.id.cb_limapuluhribu -> {
                if (checkBox.isChecked) {
                    if (saldo > 50000) {
                        uangYangAkanDibayar += 50000
                    } else {
                        Toast.makeText(this, "Saldo anda tidak cukup", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    uangYangAkanDibayar -= 50000
                }
            }
            R.id.cb_seratusribu -> {
                if (checkBox.isChecked) {
                    if (saldo > 100000) {
                        uangYangAkanDibayar += 100000
                    } else {
                        Toast.makeText(this, "Saldo anda tidak cukup", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    uangYangAkanDibayar -= 100000
                }
            }
            R.id.cb_limaratuskoin -> {
                if (checkBox.isChecked) {
                    if (saldo > 500) {
                        uangYangAkanDibayar += 500
                    } else {
                        Toast.makeText(this, "Saldo anda tidak cukup", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    uangYangAkanDibayar -= 500
                }
            }
            R.id.cb_seribukoin -> {
                if (checkBox.isChecked) {
                    if (saldo > 1000) {
                        uangYangAkanDibayar += 1000
                    } else {
                        Toast.makeText(this, "Saldo anda tidak cukup", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    uangYangAkanDibayar -= 1000
                }

            }
        }

        binding.uangYangAkanDibayar.text =
            getString(R.string.uang_yang_akan_dibayar_1s, uangYangAkanDibayar.toString())

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

    companion object {
        const val EXTRA_POSISI = "extra_posisi"
    }
}