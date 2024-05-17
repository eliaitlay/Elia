package com.example.eliaitlay

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.eliaitlay.databinding.ActivityForgetpageBinding
import com.google.firebase.auth.FirebaseAuth

class Forgetpage : AppCompatActivity() {

    private lateinit var binding: ActivityForgetpageBinding

    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgetpageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnforget.setOnClickListener {
            val email: String = binding.emailforget.text.toString().trim()
            if (email.isEmpty()) {
                binding.emailforget.error = "Masukan email anda"
                binding.emailforget.requestFocus()
                return@setOnClickListener
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                binding.emailforget.error = "Masukan email yang benar"
                binding.emailforget.requestFocus()
                return@setOnClickListener
            }

            FirebaseAuth.getInstance().sendPasswordResetEmail(email).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Cek email anda unutk mengubah password", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, Loginpage::class.java).apply {
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    })
                } else {
                    Toast.makeText(this, task.exception?.message, Toast.LENGTH_SHORT).show()
                }
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(binding.btnforget) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
