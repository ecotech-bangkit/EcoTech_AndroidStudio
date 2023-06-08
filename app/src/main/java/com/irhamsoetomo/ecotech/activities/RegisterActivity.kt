package com.irhamsoetomo.ecotech.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.irhamsoetomo.ecotech.EcotechApp
import com.irhamsoetomo.ecotech.data.model.request.LoginRequestBody
import com.irhamsoetomo.ecotech.data.model.request.RegisterRequestBody
import com.irhamsoetomo.ecotech.databinding.ActivityRegisterBinding
import com.irhamsoetomo.ecotech.viewmodel.AuthViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private val authViewModel: AuthViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (checkSharedPrefForAutoLogin()) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        observeRegister()

        binding.btnRegister.setOnClickListener {
            val name = binding.userNama.text.toString()
            val email = binding.edtEmailRegister.text.toString()
            val password = binding.edtPasswordRegister.text.toString()
            val confirmPassword = binding.confirmPassword.text.toString()

            if (password == confirmPassword) {
                val registerRequestBody = RegisterRequestBody(name, email, password, confirmPassword)
                authViewModel.registerUser(registerRequestBody)
            }
        }

        binding.textLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    private fun observeRegister() {
        authViewModel.isLoading.observe(this) { isLoading ->
            binding.btnRegister.isEnabled = !isLoading
        }

        authViewModel.registerUser.observe(this) { registerResponse ->
            if (registerResponse != null) {
                Toast.makeText(this, "Register success", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, LoginActivity::class.java))
            }
        }

        authViewModel.errorMessage.observe(this) { errorMessage ->
            if (!errorMessage.isNullOrEmpty()) {
                Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun checkSharedPrefForAutoLogin(): Boolean {
        val sharedPref = EcotechApp.context.getSharedPreferences("ecoTechPref", Context.MODE_PRIVATE)
        val email = sharedPref.getString("email", null)
        val password = sharedPref.getString("password", null)

        return if (!email.isNullOrEmpty() && !password.isNullOrEmpty()) {
            authViewModel.loginUser(LoginRequestBody(email, password)) //Assuming you have this method in your ViewModel
            true
        } else false
    }
}