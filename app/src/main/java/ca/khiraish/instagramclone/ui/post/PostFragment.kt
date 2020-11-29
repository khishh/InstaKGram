package ca.khiraish.instagramclone.ui.post

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ca.khiraish.instagramclone.R
import dagger.android.support.DaggerFragment

private const val ARG_IMAGE_URI = "IMAGE_URI"

class PostFragment : DaggerFragment() {
    private lateinit var imageUri: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            imageUri = Uri.parse(requireArguments().getString("imageUri"))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_post, container, false)
    }
}