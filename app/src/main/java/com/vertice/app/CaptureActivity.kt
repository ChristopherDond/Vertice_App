package com.vertice.app

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import com.vertice.app.ui.gallery.ScreenGallery
import com.vertice.app.ui.theme.VerticeTheme

class CaptureActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        val id = intent.getStringExtra("screen") ?: ""
        setContent {
            VerticeTheme {
                Box(Modifier.fillMaxSize()) { ScreenGallery(id) }
            }
        }
    }

    companion object {
        fun intent(ctx: android.content.Context, screen: String): Intent =
            Intent(ctx, CaptureActivity::class.java).apply {
                putExtra("screen", screen)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            }
    }
}
