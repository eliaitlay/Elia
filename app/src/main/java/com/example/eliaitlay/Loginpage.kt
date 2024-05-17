package com.example.eliaitlay

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.eliaitlay.databinding.ActivityLoginpageBinding
import com.google.firebase.auth.FirebaseAuth

class Loginpage : AppCompatActivity() {

    private lateinit var binding: ActivityLoginpageBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginpageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.btnlogin.setOnClickListener {
            val email: String = binding.edituser.text.toString().trim()
            val password: String = binding.editpassword.text.toString().trim()

            if (email.isEmpty()) {
                binding.edituser.error = "Masukan Email"
                binding.edituser.requestFocus()
                return@setOnClickListener
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                binding.edituser.error = "Masukan email yang benar"
                binding.edituser.requestFocus()
                return@setOnClickListener
            }

            if (password.isEmpty() || password.length < 6) {
                binding.editpassword.error = "Masukan password lebih dari 6 karakter"
                binding.editpassword.requestFocus()
                return@setOnClickListener
            }

            loginUser(email, password)
        }

        binding.linkregister.setOnClickListener {
            startActivity(Intent(this, Registerpage::class.java))
        }

        binding.linkforget.setOnClickListener {
            startActivity(Intent(this, Forgetpage::class.java))
        }

        ViewCompat.setOnApplyWindowInsetsListener(binding.btnlogin) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun loginUser(email: String, password: String) {
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                Intent(this, MainActivity::class.java).also { intent ->
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                }
            } else {
                Toast.makeText(this, "${it.exception?.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        if (firebaseAuth.currentUser != null) {
            Intent(this, MainActivity::class.java).also { intent ->
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }
        }
    }
}
