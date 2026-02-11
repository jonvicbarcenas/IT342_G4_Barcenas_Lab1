package com.dainsleif.miniappauth

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class DashboardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_dashboard)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.dashboard_root)) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val userId = intent.getIntExtra(EXTRA_USER_ID, 0)
        val firstName = intent.getStringExtra(EXTRA_FIRST_NAME)
        val lastName = intent.getStringExtra(EXTRA_LAST_NAME)
        val email = intent.getStringExtra(EXTRA_EMAIL)

        val dashboardMessage = findViewById<TextView>(R.id.dashboard_message)
        val displayName = listOfNotNull(firstName, lastName).joinToString(" ").ifBlank { "User" }
        dashboardMessage.text = getString(R.string.dashboard_message, displayName)

        val profileButton = findViewById<android.view.View>(R.id.dashboard_profile)
        profileButton.setOnClickListener {
            val profileIntent = Intent(this, ProfileActivity::class.java).apply {
                putExtra(EXTRA_USER_ID, userId)
                putExtra(EXTRA_FIRST_NAME, firstName)
                putExtra(EXTRA_LAST_NAME, lastName)
                putExtra(EXTRA_EMAIL, email)
            }
            startActivity(profileIntent)
        }

        val logoutButton = findViewById<android.view.View>(R.id.dashboard_logout)
        logoutButton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
        }
    }

    companion object {
        const val EXTRA_USER_ID = "extra_user_id"
        const val EXTRA_FIRST_NAME = "extra_first_name"
        const val EXTRA_LAST_NAME = "extra_last_name"
        const val EXTRA_EMAIL = "extra_email"
    }
}
