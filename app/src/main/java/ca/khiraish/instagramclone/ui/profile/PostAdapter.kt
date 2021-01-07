package ca.khiraish.instagramclone.ui.profile

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ca.khiraish.instagramclone.R
import ca.khiraish.instagramclone.data.model.Post
import ca.khiraish.instagramclone.data.model.User

class PostAdapter(): ListAdapter<Post, PostAdapter.PostViewHolder>(PostDiffCallback) {


    class PostViewHolder(itemView: View)
        : RecyclerView.ViewHolder(itemView){
        private val image = itemView.findViewById<ImageView>(R.id.item_post_photo)
        private var currentPost: Post? = null

        init {

        }

        fun bind(post: Post){
            currentPost = post
            if(post.imageUri.isNullOrEmpty()){
                image.setImageResource(R.mipmap.ic_launcher)
            }else{
                image.setImageURI(Uri.parse(post.imageUri))
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_post, parent, false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = getItem(position)
        holder.bind(post)
    }
}

object PostDiffCallback : DiffUtil.ItemCallback<Post>() {
    override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem.postId == newItem.postId
    }
}