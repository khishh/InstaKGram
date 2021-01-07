package ca.khiraish.instagramclone.ui.search

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
import ca.khiraish.instagramclone.data.model.User


class UserAdapter(
    private val onItemClick: (User) -> Unit
) : ListAdapter<User, UserAdapter.UserViewHolder>(UserDiffCallback){

    class UserViewHolder(itemView : View, private val onItemClick: (User) -> Unit)
        : RecyclerView.ViewHolder(itemView){
        private val userImage = itemView.findViewById<ImageView>(R.id.item_user_search_image)
        private val userId = itemView.findViewById<TextView>(R.id.item_user_search_userId)
        private val userName = itemView.findViewById<TextView>(R.id.item_user_search_name)
        private var currentUser : User? = null

        init {
            // TODO bind a listener
            itemView.setOnClickListener {
                currentUser?.let {
                    onItemClick(it)
                }
            }
        }

        fun bind(user: User){
            currentUser = user
            userId.text = currentUser!!.userId
            userName.text = currentUser!!.userName
            if(!user.userImage.isNullOrEmpty()){
                // TODO put image on it
            }else{
                userImage.setImageResource(R.mipmap.ic_launcher_round)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_user_search, parent, false)
        return UserViewHolder(view, onItemClick)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user)
    }


}

object UserDiffCallback : DiffUtil.ItemCallback<User>() {
    override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem.userId == newItem.userId
    }
}