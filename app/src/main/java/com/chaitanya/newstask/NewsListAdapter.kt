package com.chaitanya.newstask

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target


class NewsListAdapter(private val listener: MainActivity):RecyclerView.Adapter<NewsViewHolder>() {
    private val items: ArrayList<Article> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_news,parent,false)
        val viewHolder = NewsViewHolder(view)
        view.setOnClickListener{
        listener.onItemClicked(items[viewHolder.adapterPosition])
    }
    return viewHolder
    }


    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val currentItem = items[position]
        holder.title.text = currentItem.title
        holder.author.text = currentItem.source.name
        if (currentItem.urlToImage != null) {
            Glide.with(holder.itemView.context).load(currentItem.urlToImage).listener(object :
                RequestListener<Drawable> {
                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    holder.imageProgress.visibility = View.GONE
                    return false
                }

                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    holder.imageProgress.visibility = View.GONE
                    return false
                }
            }).into(holder.image)
        }else{
            holder.imageProgress.visibility = View.GONE
            holder.image.setImageDrawable(ContextCompat.getDrawable(holder.itemView.context,R.drawable.image_placeholder))
        }




    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun updateNews(updatedNews: ArrayList<Article>){
        items.clear()
        items.addAll(updatedNews)
        notifyDataSetChanged()
    }
}
class NewsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
 val title: TextView = itemView.findViewById(R.id.tv_title)
    val image: ImageView = itemView.findViewById(R.id.iv_thumbnail)
    val author: TextView = itemView.findViewById(R.id.tv_author)
    val imageProgress :ProgressBar = itemView.findViewById(R.id.imageProgress)
}
interface NewsItemClicked {
    fun onItemClicked(item: Article)
}