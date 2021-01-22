package ca.khiraish.instagramclone.util

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.ObservableField
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ca.khiraish.instagramclone.R
import ca.khiraish.instagramclone.data.model.Post
import ca.khiraish.instagramclone.databinding.ItemPostTimelineBinding
import ca.khiraish.instagramclone.ui.timeline.TimelineViewModel
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import javax.inject.Inject

class PostTimelineAdapter(
    private val viewModel: TimelineViewModel
) : ListAdapter<Post, PostTimelineAdapter.PostViewHolder>(PostTimelineDiffCallback) {

    class PostViewHolder(
        private val binding: ItemPostTimelineBinding,
        private val viewModel: TimelineViewModel
    ) : RecyclerView.ViewHolder(binding.root){
        val numLikesMsg = ObservableField<String>()

        init {

        }

        fun bind(post: Post){
            binding.post = post
            binding.viewmodel = viewModel
            binding.viewholder = this
            composeFavLikesText(post.favUsers.size)
            val picasso = Picasso.get()
            if(!post.userImage.isNullOrEmpty()){
                picasso.load(post.userImage)
                    .error(R.mipmap.ic_launcher_round)
                    .into(binding.itemPostTimelineUserImage)
            }else{
                binding.itemPostTimelineUserImage.setImageResource(R.mipmap.ic_launcher_round)
            }

            picasso // TODO di to Picasso
                .load(Uri.parse(post.imageUri))
                .error(R.mipmap.ic_launcher)
                .into(binding.itemPostTimelinePostImage)

            binding.itemPostTimelineFav.setOnClickListener {
                post.isFav = binding.itemPostTimelineFav.isChecked
                post.postId?.let { postId -> viewModel.updateIsFav(postId){ numLikes-> composeFavLikesText(numLikes)} }
            }
        }

        fun composeFavLikesText(numLikes: Int){
            when(numLikes){
                0 -> numLikesMsg.set("")
                1 -> numLikesMsg.set("$numLikes Like")
                else -> numLikesMsg.set("$numLikes Like")
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = ItemPostTimelineBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding, viewModel)
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