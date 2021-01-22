package ca.khiraish.instagramclone.util

import android.widget.ImageButton
import android.widget.TextView
import androidx.databinding.BindingAdapter
import ca.khiraish.instagramclone.R

object BindingAdapter {

    @JvmStatic
    @BindingAdapter("favImage")
    fun setFavImage(imageButton: ImageButton, isFav: Boolean){
        if(!isFav) imageButton.setImageResource(R.drawable.ic_fav)
        else imageButton.setImageResource(R.drawable.ic_fav_empty)
    }

    @JvmStatic
    @BindingAdapter("favNums")
    fun setFavNumText(textView: TextView, numLikes: Int){
        when(numLikes){
            0 -> textView.text = ""
            1 -> {
                val likeNum = "$numLikes Like"
                textView.text = likeNum
            }
            else -> {
                val likeNums = "$numLikes Like"
                textView.text = likeNums
            }
        }
    }

}