package com.dainsleif.miniappauth

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.dainsleif.miniappauth.model.RegisterRequest
import com.dainsleif.miniappauth.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignupActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_signup)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.signup_root)) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val loginLink = findViewById<android.view.View>(R.id.signup_to_login)
        loginLink.setOnClickListener {
            finish()
        }

        val firstNameInput = findViewById<EditText>(R.id.signup_first_name)
        val lastNameInput = findViewById<EditText>(R.id.signup_last_name)
        val emailInput = findViewById<EditText>(R.id.signup_email)
        val passwordInput = findViewById<EditText>(R.id.signup_password)
        val signupButton = findViewById<android.view.View>(R.id.signup_button)

        signupButton.setOnClickListener {
            val firstName = firstNameInput.text.toString().trim()
            val lastName = lastNameInput.text.toString().trim()
            val email = emailInput.text.toString().trim()
            val password = passwordInput.text.toString().trim()

            if (firstName.isBlank() || lastName.isBlank() || email.isBlank() || password.isBlank()) {
                Toast.makeText(this, getString(R.string.error_generic), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val request = RegisterRequest(firstName, lastName, email, password)
            ApiClient.getApi(this).register(request).enqueue(object : Callback<com.dainsleif.miniappauth.model.AuthResponse> {
                override fun onResponse(
                    call: Call<com.dainsleif.miniappauth.model.AuthResponse>,
                    response: Response<com.dainsleif.miniappauth.model.AuthResponse>
                ) {
                    val body = response.body()
                    if (response.isSuccessful && body != null) {
                        navigateToDashboard(body.userId, body.firstName, body.lastName, body.email)
                    } else {
                        Toast.makeText(this@SignupActivity, body?.message ?: getString(R.string.error_generic), Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<com.dainsleif.miniappauth.model.AuthResponse>, t: Throwable) {
                    Toast.makeText(this@SignupActivity, t.message ?: getString(R.string.error_generic), Toast.LENGTH_SHORT).show()
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
