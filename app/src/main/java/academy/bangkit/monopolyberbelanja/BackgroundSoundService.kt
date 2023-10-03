package academy.bangkit.monopolyberbelanja

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import android.support.annotation.Nullable
import android.widget.Toast

class BackgroundSoundService : Service() {

    var mediaPlayer: MediaPlayer? = null

    @Nullable
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        mediaPlayer = MediaPlayer.create(this, R.raw.bgmusic)
        mediaPlayer!!.isLooping = true // Set looping
        mediaPlayer!!.setVolume(100f, 100f)
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        if (intent.action == "PLAY") {
            if (mediaPlayer!!.isPlaying == false) {
                mediaPlayer!!.start()
                Toast.makeText(applicationContext, "Music Is Playing", Toast.LENGTH_SHORT).show()
            }
        }
        if (intent.action == "STOP") {
            stopService(intent)
            Toast.makeText(applicationContext, "Music Stopped", Toast.LENGTH_SHORT).show()
        }
        return startId
    }

    override fun onDestroy() {
        mediaPlayer!!.stop()
        mediaPlayer!!.release()
    }

    override fun onLowMemory() {}
}