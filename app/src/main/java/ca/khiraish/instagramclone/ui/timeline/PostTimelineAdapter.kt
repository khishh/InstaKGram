package ca.khiraish.instagramclone.ui.timeline

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ObservableField
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ca.khiraish.instagramclone.R
import ca.khiraish.instagramclone.data.model.Post
import ca.khiraish.instagramclone.databinding.ItemPostTimelineBinding
import com.squareup.picasso.Picasso

class PostTimelineAdapter(
    private val viewModel: TimelineViewModel,
    private val moveToCommentFragment: (String, String) -> Unit
) : ListAdapter<Post, PostTimelineAdapter.PostViewHolder>(PostTimelineDiffCallback) {

    class PostViewHolder(
        private val binding: ItemPostTimelineBinding,
        private val viewModel: TimelineViewModel,
        private val moveToCommentFragment: (String, String) -> Unit
    ) : RecyclerView.ViewHolder(binding.root){
        val numLikesMsg = ObservableField<String>()
        val numCommentsMsg = ObservableField<String>()

        init {

        }

        fun bind(post: Post){
            binding.post = post
            binding.viewmodel = viewModel
            binding.viewholder = this
            composeFavLikesText(post.favUsers.size)
            composeCommentsText(post.comments.size)
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

            binding.itemPostTimelineComment.setOnClickListener { moveToCommentFragment(post.userId!!, post.postId!!) }
        }

        private fun composeFavLikesText(numLikes: Int){
            when(numLikes){
                0 -> numLikesMsg.set("")
                1 -> numLikesMsg.set("$numLikes Like")
                else -> numLikesMsg.set("$numLikes Likes")
            }
        }

        private fun composeCommentsText(numComments: Int){
            when(numComments){
                0 -> numCommentsMsg.set("")
                1 -> numCommentsMsg.set("$numComments Comment")
                else -> numCommentsMsg.set("$numComments Comments")
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PostViewHolder {
        val binding = ItemPostTimelineBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding, viewModel, moveToCommentFragment)
    }

    override fun onBindViewHolder(
        holder: PostViewHolder,
        position: Int
    ) {
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