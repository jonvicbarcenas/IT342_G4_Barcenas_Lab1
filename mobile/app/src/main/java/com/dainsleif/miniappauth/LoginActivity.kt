package com.dainsleif.miniappauth

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.dainsleif.miniappauth.model.LoginRequest
import com.dainsleif.miniappauth.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.login_root)) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val signupLink = findViewById<android.view.View>(R.id.login_to_signup)
        signupLink.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
        }

        val emailInput = findViewById<EditText>(R.id.login_email)
        val passwordInput = findViewById<EditText>(R.id.login_password)
        val loginButton = findViewById<android.view.View>(R.id.login_button)

        loginButton.setOnClickListener {
            val email = emailInput.text.toString().trim()
            val password = passwordInput.text.toString().trim()

            if (email.isBlank() || password.isBlank()) {
                Toast.makeText(this, getString(R.string.error_generic), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            ApiClient.getApi(this).login(LoginRequest(email, password)).enqueue(object : Callback<com.dainsleif.miniappauth.model.AuthResponse> {
                override fun onResponse(
                    call: Call<com.dainsleif.miniappauth.model.AuthResponse>,
                    response: Response<com.dainsleif.miniappauth.model.AuthResponse>
                ) {
                    val body = response.body()
                    if (response.isSuccessful && body != null) {
                        navigateToDashboard(body.userId, body.firstName, body.lastName, body.email)
                    } else {
                        Toast.makeText(this@LoginActivity, body?.message ?: getString(R.string.error_generic), Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<com.dainsleif.miniappauth.model.AuthResponse>, t: Throwable) {
                    Toast.makeText(this@LoginActivity, t.message ?: getString(R.string.error_generic), Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    private fun navigateToDashboard(userId: Int?, firstName: String?, lastName: String?, email: String?) {
        val intent = Intent(this, DashboardActivity::class.java).apply {
            putExtra(DashboardActivity.EXTRA_USER_ID, userId)
            putExtra(DashboardActivity.EXTRA_FIRST_NAME, firstName)
            putExtra(DashboardActivity.EXTRA_LAST_NAME, lastName)
            putExtra(DashboardActivity.EXTRA_EMAIL, email)
        }
        startActivity(intent)
    }
}
