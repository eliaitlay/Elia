package com.example.eliaitlay

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.eliaitlay.databinding.ActivityRegisterpageBinding
import com.google.firebase.auth.FirebaseAuth

class Registerpage : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterpageBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterpageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.btnregister.setOnClickListener {
            val nama: String = binding.userregister.text.toString().trim()
            val email: String = binding.emailregister.text.toString().trim()
            val password: String = binding.passwordregister.text.toString().trim()
            val confirmPassword: String = binding.passwordconfir.text.toString().trim()

            if (nama.isEmpty()) {
                binding.userregister.error = "Masukan username anda"
                binding.userregister.requestFocus()
                return@setOnClickListener
            }

            if (email.isEmpty()) {
                binding.emailregister.error = "Masukan email anda"
                binding.emailregister.requestFocus()
                return@setOnClickListener
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                binding.emailregister.error = "Masukan email yang benar"
                binding.emailregister.requestFocus()
                return@setOnClickListener
            }

            if (password.isEmpty() || password.length < 6) {
                binding.passwordregister.error = "Password harus lebih dari 6 karakter"
                binding.passwordregister.requestFocus()
                return@setOnClickListener
            }

            if (password != confirmPassword) {
                binding.passwordconfir.error = "Password tidak sama"
                binding.passwordconfir.requestFocus()
                return@setOnClickListener
            }
            registerUser(email, password)
        }
    }

    private fun registerUser(email: String, password: String) {
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(this, "Registrasi berhasil", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {
                Toast.makeText(this, task.exception?.message, Toast.LENGTH_SHORT).show()
            }
        }
    }
}
