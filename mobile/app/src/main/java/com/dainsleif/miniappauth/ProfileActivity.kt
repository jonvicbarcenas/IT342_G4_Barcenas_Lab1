package com.dainsleif.miniappauth

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_profile)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.profile_root)) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val userId = intent.getIntExtra(DashboardActivity.EXTRA_USER_ID, 0)
        val firstName = intent.getStringExtra(DashboardActivity.EXTRA_FIRST_NAME)
        val lastName = intent.getStringExtra(DashboardActivity.EXTRA_LAST_NAME)
        val email = intent.getStringExtra(DashboardActivity.EXTRA_EMAIL)

        findViewById<TextView>(R.id.profile_user_id_value).text = userId.takeIf { it != 0 }?.toString() ?: "-"
        findViewById<TextView>(R.id.profile_name_value).text = listOfNotNull(firstName, lastName).joinToString(" ").ifBlank { "-" }
        findViewById<TextView>(R.id.profile_email_value).text = email ?: "-"

        val logoutButton = findViewById<android.view.View>(R.id.profile_logout)
        logoutButton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
        }
    }
}
