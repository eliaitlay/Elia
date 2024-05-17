package com.example.eliaitlay

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ImageAdapter(private val context: Context, private val dataList: List<Image>) : RecyclerView.Adapter<ImageAdapter.ListViewHolder>() {

    inner class ListViewHolder(var v: View) : RecyclerView.ViewHolder(v) {
        val imgT: ImageView = v.findViewById(R.id.Foto)
        val nameT: TextView = v.findViewById(R.id.txtjudul)
        val ketT: TextView = v.findViewById(R.id.txtket)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val v = inflater.inflate(R.layout.item_image, parent, false)
        return ListViewHolder(v)
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val newList = dataList[position]
        holder.nameT.text = newList.nama
        holder.ketT.text = newList.ket
        holder.imgT.loadImage(newList.imageUrl)

        holder.v.setOnClickListener {
            val intent = Intent(context, DetailActivity::class.java).apply {
                putExtra("NAMET", newList.nama)
                putExtra("KETS", newList.ket)
                putExtra("DESCRIT", newList.desc)
                putExtra("IMGURI", newList.imageUrl)
            }
            context.startActivity(intent)
        }
    }
}
