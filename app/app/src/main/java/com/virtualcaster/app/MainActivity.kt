
package com.virtualcaster.app

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView

class MainActivity : AppCompatActivity() {

    private var player: ExoPlayer? = null
    private lateinit var playerView: PlayerView
    private lateinit var statusText: TextView
    private lateinit var loopButton: Button
    private var looping = true

    private val videoPicker = registerForActivityResult(
        ActivityResultContracts.OpenDocument()
    ) { uri: Uri? ->
        if (uri != null) {
            playVideo(uri)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(24, 24, 24, 24)
        }

        val title = TextView(this).apply {
            text = "VirtualCaster"
            textSize = 26f
        }
        root.addView(title)

        statusText = TextView(this).apply {
            text = "Pilih video untuk mulai"
            textSize = 14f
            setPadding(0, 12, 0, 12)
        }
        root.addView(statusText)

        playerView = PlayerView(this).apply {
            useController = true
            layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                0,
                1f
            )
        }
        root.addView(playerView)

        val pickButton = Button(this).apply {
            text = "Pilih Video"
            setOnClickListener {
                videoPicker.launch(arrayOf("video/*"))
            }
        }
        root.addView(pickButton)

        val playButton = Button(this).apply {
            text = "Play / Pause"
            setOnClickListener {
                val currentPlayer = player
                if (currentPlayer == null) {
                    Toast.makeText(
                        this@MainActivity,
                        "Pilih video terlebih dahulu",
                        Toast.LENGTH_SHORT
                    ).show()
                } else if (currentPlayer.isPlaying) {
                    currentPlayer.pause()
                } else {
                    currentPlayer.play()
                }
            }
        }
        root.addView(playButton)

        loopButton = Button(this).apply {
            text = "Loop: Aktif"
            setOnClickListener {
                looping = !looping
                player?.repeatMode = if (looping) {
                    Player.REPEAT_MODE_ONE
                } else {
                    Player.REPEAT_MODE_OFF
                }
                text = if (looping) "Loop: Aktif" else "Loop: Mati"
            }
        }
        root.addView(loopButton)

        setContentView(root)
        initializePlayer()
    }

    private fun initializePlayer() {
        player = ExoPlayer.Builder(this).build().also {
            it.repeatMode = Player.REPEAT_MODE_ONE
            playerView.player = it

            it.addListener(object : Player.Listener {
                override fun onPlaybackStateChanged(playbackState: Int) {
                    statusText.text = when (playbackState) {
                        Player.STATE_BUFFERING -> "Memuat video..."
                        Player.STATE_READY -> "Video siap"
                        Player.STATE_ENDED -> "Video selesai"
                        Player.STATE_IDLE -> "Pilih video untuk mulai"
                        else -> "VirtualCaster"
                    }
                }

                override fun onPlayerError(
                    error: androidx.media3.common.PlaybackException
                ) {
                    statusText.text = "Video gagal diputar"
                    Toast.makeText(
                        this@MainActivity,
                        error.message ?: "Format video tidak didukung",
                        Toast.LENGTH_LONG
                    ).show()
                }
            })
        }
    }

    private fun playVideo(uri: Uri) {
        val currentPlayer = player ?: return

        try {
            contentResolver.takePersistableUriPermission(
                uri,
                Intent.FLAG_GRANT_READ_URI_PERMISSION
            )
        } catch (_: SecurityException) {
            // Izin permanen tidak tersedia; gunakan izin sementara.
        }

        currentPlayer.setMediaItem(MediaItem.fromUri(uri))
        currentPlayer.prepare()
        currentPlayer.play()
        statusText.text = "Memutar video"
    }

    override fun onStop() {
        super.onStop()
        player?.pause()
    }

    override fun onDestroy() {
        playerView.player = null
        player?.release()
        player = null
        super.onDestroy()
    }
}
  
