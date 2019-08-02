package com.fs0c131y.faceappphotostealer

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list_item.view.*

class PhotosViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.list_item, parent, false)) {
    private var mNameView: TextView? = null
    private var mTimestampView: TextView? = null


    init {
        mNameView = itemView.findViewById(R.id.photo_name)
        mTimestampView = itemView.findViewById(R.id.timestamp)
    }

    fun bind(photo: FaceAppPhoto, listener: (String) -> Unit) = with(itemView) {
        mNameView?.text = photo.name
        mTimestampView?.text = photo.timestamp
        itemView.setOnClickListener {
            listener(photo.name)
        }
    }

}