package com.vertice.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.vertice.app.core.di.ServiceLocator
import com.vertice.app.ui.nav.VerticeApp
import com.vertice.app.ui.theme.VerticeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ServiceLocator.init(applicationContext)
        enableEdgeToEdge()
        setContent {
            VerticeTheme {
                VerticeApp()
            }
        }
    }
}
