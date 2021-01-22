package ca.khiraish.instagramclone.util

import android.widget.ImageButton
import androidx.databinding.BindingAdapter
import ca.khiraish.instagramclone.R

object BindingAdapter {

    @JvmStatic
    @BindingAdapter("favImage")
    fun setFavImage(imageButton: ImageButton, isFav: Boolean){
        if(!isFav) imageButton.setImageResource(R.drawable.ic_fav)
        else imageButton.setImageResource(R.drawable.ic_fav_empty)
    }

}