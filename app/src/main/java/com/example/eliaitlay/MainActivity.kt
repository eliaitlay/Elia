package com.example.eliaitlay

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.eliaitlay.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {
    private lateinit var datalist: MutableList<Image>
    private lateinit var dataAdapter: ImageAdapter
    private lateinit var binding: ActivityMainBinding
    private var mDatabaseRef: DatabaseReference? = null
    private var mDBListener: ValueEventListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.papuans.setHasFixedSize(true)
        binding.papuans.layoutManager = LinearLayoutManager(this)

        binding.myDataLoaderProgressBar.visibility = View.VISIBLE
        datalist = ArrayList()
        dataAdapter = ImageAdapter(this, datalist)
        binding.papuans.adapter = dataAdapter

        mDatabaseRef = FirebaseDatabase.getInstance().getReference("data")
        mDBListener = mDatabaseRef!!.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                datalist.clear()
                for (teacherSnapshot in snapshot.children) {
                    val upload = teacherSnapshot.getValue(Image::class.java)
                    if (upload != null) {
                        upload.key = teacherSnapshot.key
                        datalist.add(upload)
                    }
                }
                dataAdapter.notifyDataSetChanged()
                binding.myDataLoaderProgressBar.visibility = View.GONE
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@MainActivity, error.message, Toast.LENGTH_SHORT).show()
                binding.myDataLoaderProgressBar.visibility = View.INVISIBLE
            }
        })
        binding.keluar.setOnClickListener{
            FirebaseAuth.getInstance().signOut()
            Intent(this, Loginpage::class.java).also {
                intent -> intent.flags=Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }
        }
    }
}
