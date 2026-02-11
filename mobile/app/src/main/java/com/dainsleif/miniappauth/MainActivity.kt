package com.dainsleif.miniappauth

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        if (savedInstanceState == null) {
            // Placeholder for backend setup; route to login once ready.
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}
