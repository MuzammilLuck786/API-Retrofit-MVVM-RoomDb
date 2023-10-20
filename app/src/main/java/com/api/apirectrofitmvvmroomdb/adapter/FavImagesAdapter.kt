package com.api.apirectrofitmvvmroomdb.adapter
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.api.apirectrofitmvvmroomdb.R
import com.api.apirectrofitmvvmroomdb.models.FavouriteEntity
import com.bumptech.glide.Glide

class FavImagesAdapter(
    private val context: Context,
    private var fav: List<FavouriteEntity>,
) : RecyclerView.Adapter<FavImagesAdapter.FavImgViewHolder>() {

    private var favBtnListner: FavImagesAdapter.FavBtnClickListner ?= null

    fun setOnFavouriteClickListner(listner: FavBtnClickListner){
        favBtnListner = listner
    }

    fun submitList(newFavList: List<FavouriteEntity>) {
        fav = newFavList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavImgViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.fav_layout, parent, false)
        return FavImgViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: FavImgViewHolder, position: Int) {
        val currentHit = fav[position]

        Glide.with(context)
            .load(currentHit.largeImageURL)
            .into(holder.imageView)

//        holder.likesTextView.text = "${currentHit.likes}"
//        holder.viewsTextView.text = "${currentHit.views}"
//        holder.commentsTextView.text = "${currentHit.comments}"

        holder.heartIcon.setOnClickListener {
            favBtnListner?.onFavBtnClick(currentHit)
        }

    }

    override fun getItemCount(): Int {
        return fav.size
    }

    inner class FavImgViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
//        val likesTextView: TextView = itemView.findViewById(R.id.likesTv)
//        val viewsTextView: TextView = itemView.findViewById(R.id.viewsTv)
//        val commentsTextView: TextView = itemView.findViewById(R.id.commentsTv)
        val heartIcon:ImageView=itemView.findViewById(R.id.heartIcon)

    }

    interface FavBtnClickListner{
        fun onFavBtnClick(item:FavouriteEntity)
    }

}
