package academy.bangkit.monopolyberbelanja

import academy.bangkit.monopolyberbelanja.databinding.ActivityMonopolyBinding
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

//private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class MonopolyActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMonopolyBinding
    private lateinit var viewModel: MonopolyViewModel

    private var posisi: Int = 0
    private var batasPutaran: Int = 8

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMonopolyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupBgMusic()

        binding.jmlUang.text = getString(R.string.jumlah_uang, saldo.toString())
        binding.jmlJajanan.text = jmlJajanan.toString()


        var hasilDadu: Int
        binding.kocokDadu.setOnClickListener {
            hasilDadu = rollDice()
            playAnimation(hasilDadu)
        }

        binding.mistery.setOnClickListener {
            binding.kocokDadu.visibility = View.INVISIBLE
            binding.kocokDadu.isClickable = false
            binding.imageViewDice.visibility = View.INVISIBLE
            binding.mistery.alpha = 0f
            binding.klikBuka.alpha = 0f
            binding.penawaran.alpha = 0f

            when (posisi) {
                1 -> binding.imgMakanan.setImageResource(R.drawable.apel)
                2 -> binding.imgMakanan.setImageResource(R.drawable.eskrim)
                3 -> binding.imgMakanan.setImageResource(R.drawable.telor)
                5 -> binding.imgMakanan.setImageResource(R.drawable.permen)
                6 -> binding.imgMakanan.setImageResource(R.drawable.cupcake)
                7 -> binding.imgMakanan.setImageResource(R.drawable.popice)
                9 -> binding.imgMakanan.setImageResource(R.drawable.coklat)
                10 -> binding.imgMakanan.setImageResource(R.drawable.susu)
                11 -> binding.imgMakanan.setImageResource(R.drawable.mie)
                13 -> binding.imgMakanan.setImageResource(R.drawable.ciki)
                14 -> binding.imgMakanan.setImageResource(R.drawable.jeruk)
                15 -> binding.imgMakanan.setImageResource(R.drawable.minuman)
            }
            when(posisi){
                0 -> {
                    Toast.makeText(this, "Posisi anda di Start sehingga tidak ada hadiah maupun penawaran untuk kamu", Toast.LENGTH_LONG).show()

                    binding.kocokDadu.visibility = View.VISIBLE
                    binding.kocokDadu.alpha = 1F
                    binding.kocokDadu.isClickable = true
                    binding.imageViewDice.visibility = View.VISIBLE
                }
                4 -> {
                    saldo += 12000
                    AlertDialog.Builder(this).apply {
                        setTitle("Horee!")
                        setMessage("Selamat kamu mendapatkan hadiah uang sebesar Rp. 12.000")
                        setPositiveButton("Lanjutkan bermain") { dialog, _ ->
                            dialog.cancel()
                        }
                        create()
                        show()
                    }
                    binding.jmlUang.text = saldo.toString()
                    binding.kocokDadu.visibility = View.VISIBLE
                    binding.kocokDadu.alpha = 1F
                    binding.kocokDadu.isClickable = true
                    binding.imageViewDice.visibility = View.VISIBLE
                }
                8 -> {
                    saldo += 15000
                    AlertDialog.Builder(this).apply {
                        setTitle("Horee!")
                        setMessage("Selamat kamu mendapatkan hadiah uang sebesar Rp. 15.000")
                        setPositiveButton("Lanjutkan bermain") { dialog, _ ->
                            dialog.cancel()
                        }
                        create()
                        show()
                    }
                    binding.jmlUang.text = saldo.toString()
                    binding.kocokDadu.visibility = View.VISIBLE
                    binding.kocokDadu.alpha = 1F
                    binding.kocokDadu.isClickable = true
                    binding.imageViewDice.visibility = View.VISIBLE
                }
                12 -> {
                    saldo += 8000
                    AlertDialog.Builder(this).apply {
                        setTitle("Horee!")
                        setMessage("Selamat kamu mendapatkan hadiah uang sebesar Rp. 8.000")
                        setPositiveButton("Lanjutkan bermain") { dialog, _ ->
                            dialog.cancel()
                        }
                        create()
                        show()
                    }
                    binding.jmlUang.text = saldo.toString()
                    binding.kocokDadu.visibility = View.VISIBLE
                    binding.kocokDadu.alpha = 1F
                    binding.kocokDadu.isClickable = true
                    binding.imageViewDice.visibility = View.VISIBLE
                }
                else -> {
                    binding.imgMakanan.visibility = View.VISIBLE
                    binding.apakahMauBeli.visibility = View.VISIBLE
                    binding.btnYa.visibility = View.VISIBLE
                    binding.btnTidak.visibility = View.VISIBLE
                }
            }

        }

        binding.btnTidak.setOnClickListener {
            binding.imgMakanan.visibility = View.GONE
            binding.apakahMauBeli.visibility = View.GONE
            binding.btnYa.visibility = View.GONE
            binding.btnTidak.visibility = View.GONE

            binding.kocokDadu.visibility = View.VISIBLE
            binding.kocokDadu.alpha = 1F
            binding.kocokDadu.isClickable = true
            binding.imageViewDice.visibility = View.VISIBLE
        }

        binding.btnYa.setOnClickListener {
            val intent = Intent(this@MonopolyActivity, PaymentActivity::class.java)
            intent.putExtra(PaymentActivity.EXTRA_POSISI, posisi)
            startActivity(intent)

            binding.imgMakanan.visibility = View.GONE
            binding.apakahMauBeli.visibility = View.GONE
            binding.btnYa.visibility = View.GONE
            binding.btnTidak.visibility = View.GONE

            binding.kocokDadu.visibility = View.VISIBLE
            binding.kocokDadu.alpha = 1F
            binding.kocokDadu.isClickable = true
            binding.imageViewDice.visibility = View.VISIBLE
        }
    }

    override fun onResume() {
        super.onResume()
        binding.jmlUang.text = getString(R.string.jumlah_uang, saldo.toString())
        binding.jmlJajanan.text = jmlJajanan.toString()

        if(isBgMusicPlay){
            showPlayButton(true)
        }
        else{
            showPlayButton(false)
        }
    }

    private fun rollDice(): Int {
        val randomNumber: Int = (1..6).random()
        Log.d("MonopolyActivity", randomNumber.toString())
        when (randomNumber) {
            1 -> binding.imageViewDice.setImageResource(R.drawable.dice1)
            2 -> binding.imageViewDice.setImageResource(R.drawable.dice2)
            3 -> binding.imageViewDice.setImageResource(R.drawable.dice3)
            4 -> binding.imageViewDice.setImageResource(R.drawable.dice4)
            5 -> binding.imageViewDice.setImageResource(R.drawable.dice5)
            6 -> binding.imageViewDice.setImageResource(R.drawable.dice6)
        }
        Toast.makeText(this, "Angka Dadu: $randomNumber", Toast.LENGTH_SHORT).show()

        return randomNumber
    }

    private fun playAnimation(randomNumber: Int) {
        val showKocokDadu =
            ObjectAnimator.ofFloat(binding.kocokDadu, View.ALPHA, 1f).setDuration(500)
        val hideKocokDadu =
            ObjectAnimator.ofFloat(binding.kocokDadu, View.ALPHA, 1f, 0f).setDuration(500)
        val p0 = ObjectAnimator.ofFloat(binding.posisi0, View.ALPHA, 1f).setDuration(500)
        val p0t = ObjectAnimator.ofFloat(binding.posisi0, View.ALPHA, 1f, 0f).setDuration(500)
        val p1 = ObjectAnimator.ofFloat(binding.posisi1, View.ALPHA, 1f).setDuration(500)
        val p1t = ObjectAnimator.ofFloat(binding.posisi1, View.ALPHA, 1f, 0f).setDuration(500)
        val p2 = ObjectAnimator.ofFloat(binding.posisi2, View.ALPHA, 1f).setDuration(500)
        val p2t = ObjectAnimator.ofFloat(binding.posisi2, View.ALPHA, 1f, 0f).setDuration(500)
        val p3 = ObjectAnimator.ofFloat(binding.posisi3, View.ALPHA, 1f).setDuration(500)
        val p3t = ObjectAnimator.ofFloat(binding.posisi3, View.ALPHA, 1f, 0f).setDuration(500)
        val p4 = ObjectAnimator.ofFloat(binding.posisi4, View.ALPHA, 1f).setDuration(500)
        val p4t = ObjectAnimator.ofFloat(binding.posisi4, View.ALPHA, 1f, 0f).setDuration(500)
        val p5 = ObjectAnimator.ofFloat(binding.posisi5, View.ALPHA, 1f).setDuration(500)
        val p5t = ObjectAnimator.ofFloat(binding.posisi5, View.ALPHA, 1f, 0f).setDuration(500)
        val p6 = ObjectAnimator.ofFloat(binding.posisi6, View.ALPHA, 1f).setDuration(500)
        val p6t = ObjectAnimator.ofFloat(binding.posisi6, View.ALPHA, 1f, 0f).setDuration(500)
        val p7 = ObjectAnimator.ofFloat(binding.posisi7, View.ALPHA, 1f).setDuration(500)
        val p7t = ObjectAnimator.ofFloat(binding.posisi7, View.ALPHA, 1f, 0f).setDuration(500)
        val p8 = ObjectAnimator.ofFloat(binding.posisi8, View.ALPHA, 1f).setDuration(500)
        val p8t = ObjectAnimator.ofFloat(binding.posisi8, View.ALPHA, 1f, 0f).setDuration(500)
        val p9 = ObjectAnimator.ofFloat(binding.posisi9, View.ALPHA, 1f).setDuration(500)
        val p9t = ObjectAnimator.ofFloat(binding.posisi9, View.ALPHA, 1f, 0f).setDuration(500)
        val p10 = ObjectAnimator.ofFloat(binding.posisi10, View.ALPHA, 1f).setDuration(500)
        val p10t = ObjectAnimator.ofFloat(binding.posisi10, View.ALPHA, 1f, 0f).setDuration(500)
        val p11 = ObjectAnimator.ofFloat(binding.posisi11, View.ALPHA, 1f).setDuration(500)
        val p11t = ObjectAnimator.ofFloat(binding.posisi11, View.ALPHA, 1f, 0f).setDuration(500)
        val p12 = ObjectAnimator.ofFloat(binding.posisi12, View.ALPHA, 1f).setDuration(500)
        val p12t = ObjectAnimator.ofFloat(binding.posisi12, View.ALPHA, 1f, 0f).setDuration(500)
        val p13 = ObjectAnimator.ofFloat(binding.posisi13, View.ALPHA, 1f).setDuration(500)
        val p13t = ObjectAnimator.ofFloat(binding.posisi13, View.ALPHA, 1f, 0f).setDuration(500)
        val p14 = ObjectAnimator.ofFloat(binding.posisi14, View.ALPHA, 1f).setDuration(500)
        val p14t = ObjectAnimator.ofFloat(binding.posisi14, View.ALPHA, 1f, 0f).setDuration(500)
        val p15 = ObjectAnimator.ofFloat(binding.posisi15, View.ALPHA, 1f).setDuration(500)
        val p15t = ObjectAnimator.ofFloat(binding.posisi15, View.ALPHA, 1f, 0f).setDuration(500)
        val penawaran = ObjectAnimator.ofFloat(binding.penawaran, View.ALPHA, 1f).setDuration(500)
        val klikOpen = ObjectAnimator.ofFloat(binding.klikBuka, View.ALPHA, 1f).setDuration(500)
        val mistery = ObjectAnimator.ofFloat(binding.mistery, View.ALPHA, 1f).setDuration(500)

        var posisiSaatIni = posisi + randomNumber
        if (posisiSaatIni > 15) {
            val sisa = posisiSaatIni - 16
            posisiSaatIni = 0 + sisa

            if(batasPutaran <= 0){
                AlertDialog.Builder(this).apply {
                    setTitle("Horee!!!")
                    setMessage("Selamat kamu telah menyelesaikan permainan ini!")
                    setPositiveButton("Kembali ke halaman utama") { _, _ ->
                        val intent = Intent(context, MainActivity::class.java)
                        intent.flags =
                            Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(intent)
                        finish()
                    }
                    create()
                    show()
                }
            }

            batasPutaran -= 1
        }

        if (posisi == 0) {
            if (randomNumber == 1) {
                AnimatorSet().apply {
                    playSequentially(hideKocokDadu, p0t, p1, penawaran, klikOpen, mistery)
                    start()
                }
            } else if (randomNumber == 2) {
                AnimatorSet().apply {
                    playSequentially(hideKocokDadu, p0t, p1, p1t, p2, penawaran, klikOpen, mistery)
                    start()
                }
            } else if (randomNumber == 3) {
                AnimatorSet().apply {
                    playSequentially(
                        hideKocokDadu,
                        p0t,
                        p1,
                        p1t,
                        p2,
                        p2t,
                        p3,
                        penawaran,
                        klikOpen,
                        mistery
                    )
                    start()
                }
            } else if (randomNumber == 4) {
                AnimatorSet().apply {
                    playSequentially(
                        hideKocokDadu,
                        p0t,
                        p1,
                        p1t,
                        p2,
                        p2t,
                        p3,
                        p3t,
                        p4,
                        penawaran, klikOpen, mistery
                    )
                    start()
                }
            } else if (randomNumber == 5) {
                AnimatorSet().apply {
                    playSequentially(
                        hideKocokDadu,
                        p0t,
                        p1,
                        p1t,
                        p2,
                        p2t,
                        p3,
                        p3t,
                        p4,
                        p4t,
                        p5,
                        penawaran, klikOpen, mistery
                    )
                    start()
                }
            } else if (randomNumber == 6) {
                AnimatorSet().apply {
                    playSequentially(
                        hideKocokDadu,
                        p0t,
                        p1,
                        p1t,
                        p2,
                        p2t,
                        p3,
                        p3t,
                        p4,
                        p4t,
                        p5,
                        p5t,
                        p6,
                        penawaran, klikOpen, mistery
                    )
                    start()
                }
            }
        } else if (posisi == 1) {
            if (randomNumber == 1) {
                AnimatorSet().apply {
                    playSequentially(hideKocokDadu, p1t, p2, penawaran, klikOpen, mistery)
                    start()
                }
            } else if (randomNumber == 2) {
                AnimatorSet().apply {
                    playSequentially(hideKocokDadu, p1t, p2, p2t, p3, penawaran, klikOpen, mistery)
                    start()
                }
            } else if (randomNumber == 3) {
                AnimatorSet().apply {
                    playSequentially(
                        hideKocokDadu,
                        p1t,
                        p2,
                        p2t,
                        p3,
                        p3t,
                        p4,
                        penawaran,
                        klikOpen,
                        mistery
                    )
                    start()
                }
            } else if (randomNumber == 4) {
                AnimatorSet().apply {
                    playSequentially(
                        hideKocokDadu,
                        p1t,
                        p2,
                        p2t,
                        p3,
                        p3t,
                        p4,
                        p4t,
                        p5,
                        penawaran, klikOpen, mistery
                    )
                    start()
                }
            } else if (randomNumber == 5) {
                AnimatorSet().apply {
                    playSequentially(
                        hideKocokDadu,
                        p1t,
                        p2,
                        p2t,
                        p3,
                        p3t,
                        p4,
                        p4t,
                        p5,
                        p5t,
                        p6,
                        penawaran, klikOpen, mistery
                    )
                    start()
                }
            } else if (randomNumber == 6) {
                AnimatorSet().apply {
                    playSequentially(
                        hideKocokDadu,
                        p1t,
                        p2,
                        p2t,
                        p3,
                        p3t,
                        p4,
                        p4t,
                        p5,
                        p5t,
                        p6,
                        p6t,
                        p7,
                        penawaran, klikOpen, mistery
                    )
                    start()
                }
            }

        } else if (posisi == 2) {
            if (randomNumber == 1) {
                AnimatorSet().apply {
                    playSequentially(hideKocokDadu, p2t, p3, penawaran, klikOpen, mistery)
                    start()
                }
            } else if (randomNumber == 2) {
                AnimatorSet().apply {
                    playSequentially(hideKocokDadu, p2t, p3, p3t, p4, penawaran, klikOpen, mistery)
                    start()
                }
            } else if (randomNumber == 3) {
                AnimatorSet().apply {
                    playSequentially(
                        hideKocokDadu,
                        p2t,
                        p3,
                        p3t,
                        p4,
                        p4t,
                        p5,
                        penawaran,
                        klikOpen,
                        mistery
                    )
                    start()
                }
            } else if (randomNumber == 4) {
                AnimatorSet().apply {
                    playSequentially(
                        hideKocokDadu,
                        p2t,
                        p3,
                        p3t,
                        p4,
                        p4t,
                        p5,
                        p5t,
                        p6,
                        penawaran, klikOpen, mistery
                    )
                    start()
                }
            } else if (randomNumber == 5) {
                AnimatorSet().apply {
                    playSequentially(
                        hideKocokDadu,
                        p2t,
                        p3,
                        p3t,
                        p4,
                        p4t,
                        p5,
                        p5t,
                        p6,
                        p6t,
                        p7,
                        penawaran, klikOpen, mistery
                    )
                    start()
                }
            } else if (randomNumber == 6) {
                AnimatorSet().apply {
                    playSequentially(
                        hideKocokDadu,
                        p2t,
                        p3,
                        p3t,
                        p4,
                        p4t,
                        p5,
                        p5t,
                        p6,
                        p6t,
                        p7,
                        p7t,
                        p8,
                        penawaran, klikOpen, mistery
                    )
                    start()
                }
            }
        } else if (posisi == 3) {
            if (randomNumber == 1) {
                AnimatorSet().apply {
                    playSequentially(hideKocokDadu, p3t, p4, penawaran, klikOpen, mistery)
                    start()
                }
            } else if (randomNumber == 2) {
                AnimatorSet().apply {
                    playSequentially(hideKocokDadu, p3t, p4, p4t, p5, penawaran, klikOpen, mistery)
                    start()
                }
            } else if (randomNumber == 3) {
                AnimatorSet().apply {
                    playSequentially(
                        hideKocokDadu,
                        p3t,
                        p4,
                        p4t,
                        p5,
                        p5t,
                        p6,
                        penawaran,
                        klikOpen,
                        mistery
                    )
                    start()
                }
            } else if (randomNumber == 4) {
                AnimatorSet().apply {
                    playSequentially(
                        hideKocokDadu,
                        p3t,
                        p4,
                        p4t,
                        p5,
                        p5t,
                        p6,
                        p6t,
                        p7,
                        penawaran, klikOpen, mistery
                    )
                    start()
                }
            } else if (randomNumber == 5) {
                AnimatorSet().apply {
                    playSequentially(
                        hideKocokDadu,
                        p3t,
                        p4,
                        p4t,
                        p5,
                        p5t,
                        p6,
                        p6t,
                        p7,
                        p7t,
                        p8,
                        penawaran, klikOpen, mistery
                    )
                    start()
                }
            } else if (randomNumber == 6) {
                AnimatorSet().apply {
                    playSequentially(
                        hideKocokDadu,
                        p3t,
                        p4,
                        p4t,
                        p5,
                        p5t,
                        p6,
                        p6t,
                        p7,
                        p7t,
                        p8,
                        p8t,
                        p9,
                        penawaran, klikOpen, mistery
                    )
                    start()
                }
            }
        } else if (posisi == 4) {
            if (randomNumber == 1) {
                AnimatorSet().apply {
                    playSequentially(hideKocokDadu, p4t, p5, penawaran, klikOpen, mistery)
                    start()
                }
            } else if (randomNumber == 2) {
                AnimatorSet().apply {
                    playSequentially(hideKocokDadu, p4t, p5, p5t, p6, penawaran, klikOpen, mistery)
                    start()
                }
            } else if (randomNumber == 3) {
                AnimatorSet().apply {
                    playSequentially(
                        hideKocokDadu,
                        p4t,
                        p5,
                        p5t,
                        p6,
                        p6t,
                        p7,
                        penawaran,
                        klikOpen,
                        mistery
                    )
                    start()
                }
            } else if (randomNumber == 4) {
                AnimatorSet().apply {
                    playSequentially(
                        hideKocokDadu,
                        p4t,
                        p5,
                        p5t,
                        p6,
                        p6t,
                        p7,
                        p7t,
                        p8,
                        penawaran, klikOpen, mistery
                    )
                    start()
                }
            } else if (randomNumber == 5) {
                AnimatorSet().apply {
                    playSequentially(
                        hideKocokDadu,
                        p4t,
                        p5,
                        p5t,
                        p6,
                        p6t,
                        p7,
                        p7t,
                        p8,
                        p8t,
                        p9,
                        penawaran, klikOpen, mistery
                    )
                    start()
                }
            } else if (randomNumber == 6) {
                AnimatorSet().apply {
                    playSequentially(
                        hideKocokDadu,
                        p4t,
                        p5,
                        p5t,
                        p6,
                        p6t,
                        p7,
                        p7t,
                        p8,
                        p8t,
                        p9,
                        p9t,
                        p10,
                        penawaran, klikOpen, mistery
                    )
                    start()
                }
            }
        } else if (posisi == 5) {
            if (randomNumber == 1) {
                AnimatorSet().apply {
                    playSequentially(hideKocokDadu, p5t, p6, penawaran, klikOpen, mistery)
                    start()
                }
            } else if (randomNumber == 2) {
                AnimatorSet().apply {
                    playSequentially(hideKocokDadu, p5t, p6, p6t, p7, penawaran, klikOpen, mistery)
                    start()
                }
            } else if (randomNumber == 3) {
                AnimatorSet().apply {
                    playSequentially(
                        hideKocokDadu,
                        p5t,
                        p6,
                        p6t,
                        p7,
                        p7t,
                        p8,
                        penawaran,
                        klikOpen,
                        mistery
                    )
                    start()
                }
            } else if (randomNumber == 4) {
                AnimatorSet().apply {
                    playSequentially(
                        hideKocokDadu,
                        p5t,
                        p6,
                        p6t,
                        p7,
                        p7t,
                        p8,
                        p8t,
                        p9,
                        penawaran, klikOpen, mistery
                    )
                    start()
                }
            } else if (randomNumber == 5) {
                AnimatorSet().apply {
                    playSequentially(
                        hideKocokDadu,
                        p5t,
                        p6,
                        p6t,
                        p7,
                        p7t,
                        p8,
                        p8t,
                        p9,
                        p9t,
                        p10,
                        penawaran, klikOpen, mistery
                    )
                    start()
                }
            } else if (randomNumber == 6) {
                AnimatorSet().apply {
                    playSequentially(
                        hideKocokDadu,
                        p5t,
                        p6,
                        p6t,
                        p7,
                        p7t,
                        p8,
                        p8t,
                        p9,
                        p9t,
                        p10,
                        p10t,
                        p11,
                        penawaran, klikOpen, mistery
                    )
                    start()
                }
            }
        } else if (posisi == 6) {
            if (randomNumber == 1) {
                AnimatorSet().apply {
                    playSequentially(hideKocokDadu, p6t, p7, penawaran, klikOpen, mistery)
                    start()
                }
            } else if (randomNumber == 2) {
                AnimatorSet().apply {
                    playSequentially(hideKocokDadu, p6t, p7, p7t, p8, penawaran, klikOpen, mistery)
                    start()
                }
            } else if (randomNumber == 3) {
                AnimatorSet().apply {
                    playSequentially(
                        hideKocokDadu,
                        p6t,
                        p7,
                        p7t,
                        p8,
                        p8t,
                        p9,
                        penawaran,
                        klikOpen,
                        mistery
                    )
                    start()
                }
            } else if (randomNumber == 4) {
                AnimatorSet().apply {
                    playSequentially(
                        hideKocokDadu,
                        p6t,
                        p7,
                        p7t,
                        p8,
                        p8t,
                        p9,
                        p9t,
                        p10,
                        penawaran, klikOpen, mistery
                    )
                    start()
                }
            } else if (randomNumber == 5) {
                AnimatorSet().apply {
                    playSequentially(
                        hideKocokDadu,
                        p6t,
                        p7,
                        p7t,
                        p8,
                        p8t,
                        p9,
                        p9t,
                        p10,
                        p10t,
                        p11,
                        penawaran, klikOpen, mistery
                    )
                    start()
                }
            } else if (randomNumber == 6) {
                AnimatorSet().apply {
                    playSequentially(
                        hideKocokDadu,
                        p6t,
                        p7,
                        p7t,
                        p8,
                        p8t,
                        p9,
                        p9t,
                        p10,
                        p10t,
                        p11,
                        p11t,
                        p12,
                        penawaran, klikOpen, mistery
                    )
                    start()
                }
            }
        } else if (posisi == 7) {
            if (randomNumber == 1) {
                AnimatorSet().apply {
                    playSequentially(hideKocokDadu, p7t, p8, penawaran, klikOpen, mistery)
                    start()
                }
            } else if (randomNumber == 2) {
                AnimatorSet().apply {
                    playSequentially(hideKocokDadu, p7t, p8, p8t, p9, penawaran, klikOpen, mistery)
                    start()
                }
            } else if (randomNumber == 3) {
                AnimatorSet().apply {
                    playSequentially(
                        hideKocokDadu,
                        p7t,
                        p8,
                        p8t,
                        p9,
                        p9t,
                        p10,
                        penawaran,
                        klikOpen,
                        mistery
                    )
                    start()
                }
            } else if (randomNumber == 4) {
                AnimatorSet().apply {
                    playSequentially(
                        hideKocokDadu,
                        p7t,
                        p8,
                        p8t,
                        p9,
                        p9t,
                        p10,
                        p10t,
                        p11,
                        penawaran, klikOpen, mistery
                    )
                    start()
                }
            } else if (randomNumber == 5) {
                AnimatorSet().apply {
                    playSequentially(
                        hideKocokDadu,
                        p7t,
                        p8,
                        p8t,
                        p9,
                        p9t,
                        p10,
                        p10t,
                        p11,
                        p11t,
                        p12,
                        penawaran, klikOpen, mistery
                    )
                    start()
                }
            } else if (randomNumber == 6) {
                AnimatorSet().apply {
                    playSequentially(
                        hideKocokDadu,
                        p7t,
                        p8,
                        p8t,
                        p9,
                        p9t,
                        p10,
                        p10t,
                        p11,
                        p11t,
                        p12,
                        p12t,
                        p13,
                        penawaran, klikOpen, mistery
                    )
                    start()
                }
            }
        } else if (posisi == 8) {
            if (randomNumber == 1) {
                AnimatorSet().apply {
                    playSequentially(hideKocokDadu, p8t, p9, penawaran, klikOpen, mistery)
                    start()
                }
            } else if (randomNumber == 2) {
                AnimatorSet().apply {
                    playSequentially(hideKocokDadu, p8t, p9, p9t, p10, penawaran, klikOpen, mistery)
                    start()
                }
            } else if (randomNumber == 3) {
                AnimatorSet().apply {
                    playSequentially(
                        hideKocokDadu,
                        p8t,
                        p9,
                        p9t,
                        p10,
                        p10t,
                        p11,
                        penawaran,
                        klikOpen,
                        mistery
                    )
                    start()
                }
            } else if (randomNumber == 4) {
                AnimatorSet().apply {
                    playSequentially(
                        hideKocokDadu,
                        p8t,
                        p9,
                        p9t,
                        p10,
                        p10t,
                        p11,
                        p11t,
                        p12,
                        penawaran, klikOpen, mistery
                    )
                    start()
                }
            } else if (randomNumber == 5) {
                AnimatorSet().apply {
                    playSequentially(
                        hideKocokDadu,
                        p8t,
                        p9,
                        p9t,
                        p10,
                        p10t,
                        p11,
                        p11t,
                        p12,
                        p12t,
                        p13,
                        penawaran, klikOpen, mistery
                    )
                    start()
                }
            } else if (randomNumber == 6) {
                AnimatorSet().apply {
                    playSequentially(
                        hideKocokDadu,
                        p8t,
                        p9,
                        p9t,
                        p10,
                        p10t,
                        p11,
                        p11t,
                        p12,
                        p12t,
                        p13,
                        p13t,
                        p14,
                        penawaran, klikOpen, mistery
                    )
                    start()
                }
            }
        } else if (posisi == 9) {
            if (randomNumber == 1) {
                AnimatorSet().apply {
                    playSequentially(hideKocokDadu, p9t, p10, penawaran, klikOpen, mistery)
                    start()
                }
            } else if (randomNumber == 2) {
                AnimatorSet().apply {
                    playSequentially(
                        hideKocokDadu,
                        p9t,
                        p10,
                        p10t,
                        p11,
                        penawaran,
                        klikOpen,
                        mistery
                    )
                    start()
                }
            } else if (randomNumber == 3) {
                AnimatorSet().apply {
                    playSequentially(
                        hideKocokDadu,
                        p9t,
                        p10,
                        p10t,
                        p11,
                        p11t,
                        p12,
                        penawaran,
                        klikOpen,
                        mistery
                    )
                    start()
                }
            } else if (randomNumber == 4) {
                AnimatorSet().apply {
                    playSequentially(
                        hideKocokDadu,
                        p9t,
                        p10,
                        p10t,
                        p11,
                        p11t,
                        p12,
                        p12t,
                        p13,
                        penawaran, klikOpen, mistery
                    )
                    start()
                }
            } else if (randomNumber == 5) {
                AnimatorSet().apply {
                    playSequentially(
                        hideKocokDadu,
                        p9t,
                        p10,
                        p10t,
                        p11,
                        p11t,
                        p12,
                        p12t,
                        p13,
                        p13t,
                        p14,
                        penawaran, klikOpen, mistery
                    )
                    start()
                }
            } else if (randomNumber == 6) {
                AnimatorSet().apply {
                    playSequentially(
                        hideKocokDadu,
                        p9t,
                        p10,
                        p10t,
                        p11,
                        p11t,
                        p12,
                        p12t,
                        p13,
                        p13t,
                        p14,
                        p14t,
                        p15, penawaran, klikOpen, mistery
                    )
                    start()
                }
            }
        } else if (posisi == 10) {
            if (randomNumber == 1) {
                AnimatorSet().apply {
                    playSequentially(hideKocokDadu, p10t, p11, penawaran, klikOpen, mistery)
                    start()
                }
            } else if (randomNumber == 2) {
                AnimatorSet().apply {
                    playSequentially(
                        hideKocokDadu,
                        p10t,
                        p11,
                        p11t,
                        p12,
                        penawaran,
                        klikOpen,
                        mistery
                    )
                    start()
                }
            } else if (randomNumber == 3) {
                AnimatorSet().apply {
                    playSequentially(
                        hideKocokDadu,
                        p10t,
                        p11,
                        p11t,
                        p12,
                        p12t,
                        p13,
                        penawaran,
                        klikOpen,
                        mistery
                    )
                    start()
                }
            } else if (randomNumber == 4) {
                AnimatorSet().apply {
                    playSequentially(
                        hideKocokDadu,
                        p10t,
                        p11,
                        p11t,
                        p12,
                        p12t,
                        p13,
                        p13t,
                        p14,
                        penawaran, klikOpen, mistery
                    )
                    start()
                }
            } else if (randomNumber == 5) {
                AnimatorSet().apply {
                    playSequentially(
                        hideKocokDadu,
                        p10t,
                        p11,
                        p11t,
                        p12,
                        p12t,
                        p13,
                        p13t,
                        p14,
                        p14t,
                        p15,
                        penawaran, klikOpen, mistery
                    )
                    start()
                }
            } else if (randomNumber == 6) {
                AnimatorSet().apply {
                    playSequentially(
                        hideKocokDadu,
                        p10t,
                        p11,
                        p11t,
                        p12,
                        p12t,
                        p13,
                        p13t,
                        p14,
                        p14t,
                        p15,
                        p15t,
                        p0,
                        penawaran, klikOpen, mistery
                    )
                    start()
                }
            }
        } else if (posisi == 11) {
            if (randomNumber == 1) {
                AnimatorSet().apply {
                    playSequentially(hideKocokDadu, p11t, p12, penawaran, klikOpen, mistery)
                    start()
                }
            } else if (randomNumber == 2) {
                AnimatorSet().apply {
                    playSequentially(
                        hideKocokDadu,
                        p11t,
                        p12,
                        p12t,
                        p13,
                        penawaran,
                        klikOpen,
                        mistery
                    )
                    start()
                }
            } else if (randomNumber == 3) {
                AnimatorSet().apply {
                    playSequentially(
                        hideKocokDadu,
                        p11t,
                        p12,
                        p12t,
                        p13,
                        p13t,
                        p14,
                        penawaran,
                        klikOpen,
                        mistery
                    )
                    start()
                }
            } else if (randomNumber == 4) {
                AnimatorSet().apply {
                    playSequentially(
                        hideKocokDadu,
                        p11t,
                        p12,
                        p12t,
                        p13,
                        p13t,
                        p14,
                        p14t,
                        p15,
                        penawaran, klikOpen, mistery
                    )
                    start()
                }
            } else if (randomNumber == 5) {
                AnimatorSet().apply {
                    playSequentially(
                        hideKocokDadu,
                        p11t,
                        p12,
                        p12t,
                        p13,
                        p13t,
                        p14,
                        p14t,
                        p15,
                        p15t,
                        p0,
                        penawaran, klikOpen, mistery
                    )
                    start()
                }
            } else if (randomNumber == 6) {
                AnimatorSet().apply {
                    playSequentially(
                        hideKocokDadu,
                        p11t,
                        p12,
                        p12t,
                        p13,
                        p13t,
                        p14,
                        p14t,
                        p15,
                        p15t,
                        p0,
                        p0t,
                        p1,
                        penawaran, klikOpen, mistery
                    )
                    start()
                }
            }
        } else if (posisi == 12) {
            if (randomNumber == 1) {
                AnimatorSet().apply {
                    playSequentially(hideKocokDadu, p12t, p13, penawaran, klikOpen, mistery)
                    start()
                }
            } else if (randomNumber == 2) {
                AnimatorSet().apply {
                    playSequentially(
                        hideKocokDadu,
                        p12t,
                        p13,
                        p13t,
                        p14,
                        penawaran,
                        klikOpen,
                        mistery
                    )
                    start()
                }
            } else if (randomNumber == 3) {
                AnimatorSet().apply {
                    playSequentially(
                        hideKocokDadu,
                        p12t,
                        p13,
                        p13t,
                        p14,
                        p14t,
                        p15,
                        penawaran,
                        klikOpen,
                        mistery
                    )
                    start()
                }
            } else if (randomNumber == 4) {
                AnimatorSet().apply {
                    playSequentially(
                        hideKocokDadu,
                        p12t,
                        p13,
                        p13t,
                        p14,
                        p14t,
                        p15,
                        p15t,
                        p0,
                        penawaran, klikOpen, mistery
                    )
                    start()
                }
            } else if (randomNumber == 5) {
                AnimatorSet().apply {
                    playSequentially(
                        hideKocokDadu,
                        p12t,
                        p13,
                        p13t,
                        p14,
                        p14t,
                        p15,
                        p15t,
                        p0,
                        p0t,
                        p1,
                        penawaran, klikOpen, mistery
                    )
                    start()
                }
            } else if (randomNumber == 6) {
                AnimatorSet().apply {
                    playSequentially(
                        hideKocokDadu,
                        p12t,
                        p13,
                        p13t,
                        p14,
                        p14t,
                        p15,
                        p15t,
                        p0,
                        p0t,
                        p1,
                        p1t,
                        p2,
                        penawaran, klikOpen, mistery
                    )
                    start()
                }
            }
        } else if (posisi == 13) {
            if (randomNumber == 1) {
                AnimatorSet().apply {
                    playSequentially(hideKocokDadu, p13t, p14, penawaran, klikOpen, mistery)
                    start()
                }
            } else if (randomNumber == 2) {
                AnimatorSet().apply {
                    playSequentially(
                        hideKocokDadu,
                        p13t,
                        p14,
                        p14t,
                        p15,
                        penawaran,
                        klikOpen,
                        mistery
                    )
                    start()
                }
            } else if (randomNumber == 3) {
                AnimatorSet().apply {
                    playSequentially(
                        hideKocokDadu,
                        p13t,
                        p14,
                        p14t,
                        p15,
                        p15t,
                        p0,
                        penawaran,
                        klikOpen,
                        mistery
                    )
                    start()
                }
            } else if (randomNumber == 4) {
                AnimatorSet().apply {
                    playSequentially(
                        hideKocokDadu,
                        p13t,
                        p14,
                        p14t,
                        p15,
                        p15t,
                        p0,
                        p0t,
                        p1,
                        penawaran, klikOpen, mistery
                    )
                    start()
                }
            } else if (randomNumber == 5) {
                AnimatorSet().apply {
                    playSequentially(
                        hideKocokDadu,
                        p13t,
                        p14,
                        p14t,
                        p15,
                        p15t,
                        p0,
                        p0t,
                        p1,
                        p1t,
                        p2,
                        penawaran, klikOpen, mistery
                    )
                    start()
                }
            } else if (randomNumber == 6) {
                AnimatorSet().apply {
                    playSequentially(
                        hideKocokDadu,
                        p13t,
                        p14,
                        p14t,
                        p15,
                        p15t,
                        p0,
                        p0t,
                        p1,
                        p1t,
                        p2,
                        p2t,
                        p3,
                        penawaran, klikOpen, mistery
                    )
                    start()
                }
            }
        } else if (posisi == 14) {
            if (randomNumber == 1) {
                AnimatorSet().apply {
                    playSequentially(hideKocokDadu, p14t, p15, penawaran, klikOpen, mistery)
                    start()
                }
            } else if (randomNumber == 2) {
                AnimatorSet().apply {
                    playSequentially(
                        hideKocokDadu,
                        p14t,
                        p15,
                        p15t,
                        p0,
                        penawaran,
                        klikOpen,
                        mistery
                    )
                    start()
                }
            } else if (randomNumber == 3) {
                AnimatorSet().apply {
                    playSequentially(
                        hideKocokDadu,
                        p14t,
                        p15,
                        p15t,
                        p0,
                        p0t,
                        p1,
                        penawaran,
                        klikOpen,
                        mistery
                    )
                    start()
                }
            } else if (randomNumber == 4) {
                AnimatorSet().apply {
                    playSequentially(
                        hideKocokDadu,
                        p14t,
                        p15,
                        p15t,
                        p0,
                        p0t,
                        p1,
                        p1t,
                        p2,
                        penawaran, klikOpen, mistery
                    )
                    start()
                }
            } else if (randomNumber == 5) {
                AnimatorSet().apply {
                    playSequentially(
                        hideKocokDadu,
                        p14t,
                        p15,
                        p15t,
                        p0,
                        p0t,
                        p1,
                        p1t,
                        p2,
                        p2t,
                        p3,
                        penawaran, klikOpen, mistery
                    )
                    start()
                }
            } else if (randomNumber == 6) {
                AnimatorSet().apply {
                    playSequentially(
                        hideKocokDadu,
                        p14t,
                        p15,
                        p15t,
                        p0,
                        p0t,
                        p1,
                        p1t,
                        p2,
                        p2t,
                        p3,
                        p3t,
                        p4,
                        penawaran, klikOpen, mistery
                    )
                    start()
                }
            }
        } else if (posisi == 15) {
            if (randomNumber == 1) {
                AnimatorSet().apply {
                    playSequentially(hideKocokDadu, p15t, p0, penawaran, klikOpen, mistery)
                    start()
                }
            } else if (randomNumber == 2) {
                AnimatorSet().apply {
                    playSequentially(hideKocokDadu, p15t, p0, p0t, p1, penawaran, klikOpen, mistery)
                    start()
                }
            } else if (randomNumber == 3) {
                AnimatorSet().apply {
                    playSequentially(
                        hideKocokDadu,
                        p15t,
                        p0,
                        p0t,
                        p1,
                        p1t,
                        p2,
                        penawaran,
                        klikOpen,
                        mistery
                    )
                    start()
                }
            } else if (randomNumber == 4) {
                AnimatorSet().apply {
                    playSequentially(
                        hideKocokDadu,
                        p15t,
                        p0,
                        p0t,
                        p1,
                        p1t,
                        p2,
                        p2t,
                        p3,
                        penawaran, klikOpen, mistery
                    )
                    start()
                }
            } else if (randomNumber == 5) {
                AnimatorSet().apply {
                    playSequentially(
                        hideKocokDadu,
                        p15t,
                        p0,
                        p0t,
                        p1,
                        p1t,
                        p2,
                        p2t,
                        p3,
                        p3t,
                        p4,
                        penawaran, klikOpen, mistery
                    )
                    start()
                }
            } else if (randomNumber == 6) {
                AnimatorSet().apply {
                    playSequentially(
                        hideKocokDadu,
                        p15t,
                        p0,
                        p0t,
                        p1,
                        p1t,
                        p2,
                        p2t,
                        p3,
                        p3t,
                        p4,
                        p4t,
                        p5,
                        penawaran, klikOpen, mistery
                    )
                    start()
                }
            }
        }
        posisi = posisiSaatIni
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
}