package com.api.apirectrofitmvvmroomdb.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.api.apirectrofitmvvmroomdb.R
import com.api.apirectrofitmvvmroomdb.activities.ActivityFullScreen
import com.api.apirectrofitmvvmroomdb.interfaces.AdapterClick
import com.api.apirectrofitmvvmroomdb.interfaces.ItemClick
import com.api.apirectrofitmvvmroomdb.models.Hit
import com.api.apirectrofitmvvmroomdb.viewmodels.MainViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class ImageAdapter(
    private val context: Context,
    var hits: List<Hit> = emptyList(),
    private val adapterClick: AdapterClick,
    private val itemClick: ItemClick?,
) : RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {

    fun submitList(newHits: List<Hit>) {
        hits = newHits
        notifyDataSetChanged()
    }

    class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
        val likesTextView: TextView = itemView.findViewById(R.id.likesTextView)
        val viewsTextView: TextView = itemView.findViewById(R.id.viewsTextView)
        val commentsTextView: TextView = itemView.findViewById(R.id.commentsTextView)
        val pbImg:ProgressBar=itemView.findViewById(R.id.pbImg)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.image_row_layout, parent, false)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val hit = hits[position]

        holder.pbImg.visibility = View.VISIBLE

        Glide.with(context)
            .load(hit.largeImageURL)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    holder.pbImg.visibility = View.GONE
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    holder.pbImg.visibility = View.GONE
                    return false
                }
            })
            .into(holder.imageView)

        // Set likes, views, and comments
        holder.likesTextView.text = "${hit.likes}"
        holder.viewsTextView.text = "${hit.views}"
        holder.commentsTextView.text = "${hit.comments}"


        holder.itemView.setOnClickListener {
                val intent = Intent(context, ActivityFullScreen::class.java)
                intent.putExtra("Hit", hit)
                context.startActivity(intent)
            }
        }

    override fun getItemCount(): Int {
        return hits.size
    }
}
