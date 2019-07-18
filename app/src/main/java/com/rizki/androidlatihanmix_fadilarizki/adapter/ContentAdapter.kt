package com.rizki.androidlatihanmix_fadilarizki.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rizki.androidlatihanmix_fadilarizki.R
import com.rizki.androidlatihanmix_fadilarizki.model.ModelUpload

class ContentAdapter : RecyclerView.Adapter<ContentAdapter.ViewHolder> {

    var uploads: ArrayList<ModelUpload>
    var context: Context

    constructor(uploads: ArrayList<ModelUpload>, context: Context) {
        this.uploads = uploads
        this.context = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_content, parent, false)
        // set the view's size, margins, paddings and layout parameters
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return uploads.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val upload = uploads.get(position)
        holder.contentTV.text = upload.caption
        holder.contentSender.text = upload.senderName
        Glide.with(context)
            .load(upload.imageUrl)
            .centerCrop()
            .into(holder.contentIV)
        Glide.with(context)
            .load(upload.photoUrl)
            .centerCrop()
            .into(holder.photoIV)
    }

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        var photoIV: ImageView
        var contentSender: TextView
        var contentIV: ImageView
        var contentTV: TextView

        init {
            photoIV = v.findViewById(R.id.photoIV)
            contentSender = v.findViewById(R.id.contentSender)
            contentIV = v.findViewById(R.id.contentIV)
            contentTV = v.findViewById(R.id.contentTV)
        }
    }
}