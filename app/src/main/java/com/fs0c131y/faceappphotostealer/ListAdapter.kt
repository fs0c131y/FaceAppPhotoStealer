package com.fs0c131y.faceappphotostealer

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class ListAdapter(private val list: List<FaceAppPhoto>, private val listener: (String) -> Unit)
    : RecyclerView.Adapter<PhotosViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotosViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return PhotosViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: PhotosViewHolder, position: Int) {
        val photo: FaceAppPhoto = list[position]
        holder.bind(photo, listener)
    }

    override fun getItemCount(): Int = list.size

}