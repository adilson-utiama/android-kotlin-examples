package com.asuprojects.kotlincustomcomponents.fragments.media

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.asuprojects.kotlincustomcomponents.R

class MediaViewAdapter(var medias: MutableList<String>)
    : RecyclerView.Adapter<MediaViewAdapter.MediaViewHolder>(){

    private var onMediaFileSelected: OnMediaFileSelected? = null

    fun setOnMediaFileSelected(listener: OnMediaFileSelected){
        this.onMediaFileSelected = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MediaViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.viewholder_media, parent, false)
        return MediaViewHolder(
            view
        )
    }

    override fun onBindViewHolder(holder: MediaViewHolder, position: Int) {
        val audioFile = medias[position]
        holder.mediaFileName.text = audioFile
        holder.container.setOnClickListener {
            onMediaFileSelected?.onClickMediaFile(it, holder.adapterPosition)
        }
        holder.mediaOptions.setOnClickListener {
            onMediaFileSelected?.onClickMediaOptionsMenu(it, holder.adapterPosition)
        }
    }

    override fun getItemCount(): Int {
        return medias.size
    }

    class MediaViewHolder(view: View): RecyclerView.ViewHolder(view){
        val container = view.findViewById<ConstraintLayout>(R.id.vh_container_media_file)
        val mediaFileName = view.findViewById<AppCompatTextView>(R.id.vh_media_file_name)
        val mediaOptions = view.findViewById<AppCompatImageView>(R.id.vh_media_file_options)
    }

    interface OnMediaFileSelected {
        fun onClickMediaFile(view: View, position: Int)
        fun onClickMediaOptionsMenu(view: View, position: Int)
    }
}