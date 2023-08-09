package com.thirdweek.mymusicplayer

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.io.File

class CustomAdapter(private val context : Context,
                    private val items : ArrayList<String>,
                    private val mySongs: ArrayList<File>
                    )
    : RecyclerView.Adapter<CustomAdapter.SongViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        val itemView : View = LayoutInflater.from(context)
            .inflate(R.layout.list_item, parent, false)

        return SongViewHolder(itemView)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        holder.songTitle.isSelected = true
        holder.songTitle.text = items[position]

        holder.itemView.setOnClickListener {
            val theName : String = items[position]
            val intent = Intent(context, PlayerActivity::class.java)
            context.startActivity(intent)
            intent.putExtra("SongName", theName)
            intent.putExtra("SongDir", mySongs)
            intent.putExtra("position", position)
        }

    }


    class SongViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val songTitle : TextView = itemView.findViewById(R.id.songName)
        val songImage : ImageView = itemView.findViewById(R.id.songImg)

    }

}

