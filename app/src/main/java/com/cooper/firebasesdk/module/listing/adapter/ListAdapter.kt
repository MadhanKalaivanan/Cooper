package com.cooper.firebasesdk.module.listing.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cooper.firebasesdk.R
import com.cooper.firebasesdk.module.addMeeting.dto.ResultData
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions

class ListAdapter(private val context: Context, data: FirebaseRecyclerOptions<ResultData>) :
    FirebaseRecyclerAdapter<ResultData, ListAdapter.ViewHolder>(data) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.list_row, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, model: ResultData) {
        holder.itemView.findViewById<TextView>(R.id.title).text = model.title
        holder.itemView.findViewById<TextView>(R.id.date).text = model.date
        holder.itemView.findViewById<TextView>(R.id.note).text = model.description

        holder.itemView.findViewById<ImageView>(R.id.edit).setOnClickListener {
            (context as ClickListener).rowClickListener(model, getRef(position).key)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItem(data: ResultData) {

        }
    }

}