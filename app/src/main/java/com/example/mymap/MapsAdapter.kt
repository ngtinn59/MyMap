package com.example.mymap

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mymap.models.UserMap

class MapsAdapter(val context: Context, val userMaps: List<UserMap>, val onClickListener: OnClickListener) : RecyclerView.Adapter<MapsAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {}


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.row_place, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return userMaps.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val userMaps = userMaps[position]
        val tvTitle = holder.itemView.findViewById<TextView>(R.id.tvPlace)
        tvTitle.text = userMaps.title

        holder.itemView.setOnClickListener {

            onClickListener.onItemClick(position)
        }
    }

    interface OnClickListener {
        fun onItemClick(position: Int)
    }
}
