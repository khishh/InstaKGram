package ca.khiraish.instagramclone.util

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ca.khiraish.instagramclone.R
import ca.khiraish.instagramclone.data.model.Post
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import javax.inject.Inject

class PostTimelineAdapter : ListAdapter<Post, PostTimelineAdapter.PostViewHolder>(PostTimelineDiffCallback) {


    class PostViewHolder(itemView: View)
        : RecyclerView.ViewHolder(itemView){
        private val userImage = itemView.findViewById<CircleImageView>(R.id.item_post_timeline_userImage)
        private val postImage = itemView.findViewById<ImageView>(R.id.item_post_timeline_postImage)
        private val userName = itemView.findViewById<TextView>(R.id.item_post_timeline_userName)

        private var currentPost: Post? = null

        init {

        }

        fun bind(post: Post){
            currentPost = post
            val picasso = Picasso.get()

            if(!post.userImage.isNullOrEmpty()){
                picasso.load(post.userImage)
                    .error(R.mipmap.ic_launcher_round)
                    .into(userImage)
            }else{
                userImage.setImageResource(R.mipmap.ic_launcher_round)
            }


            picasso // TODO di to Picasso
                .load(Uri.parse(post.imageUri))
//                .placeholder(R.mipmap.ic_launcher_round)
                .error(R.mipmap.ic_launcher)
                .into(postImage)

            userName.text = post.userName
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_post_timeline, parent, false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = getItem(position)
        holder.bind(post)
    }
}

object PostTimelineDiffCallback : DiffUtil.ItemCallback<Post>() {
    override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem.postId == newItem.postId
    }
}