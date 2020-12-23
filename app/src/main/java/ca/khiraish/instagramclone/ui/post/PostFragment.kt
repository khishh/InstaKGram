package ca.khiraish.instagramclone.ui.post

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import ca.khiraish.instagramclone.R
import ca.khiraish.instagramclone.databinding.FragmentPostBinding
import ca.khiraish.instagramclone.ui.account.AccountViewModel
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_post.*
import javax.inject.Inject

private const val ARG_IMAGE_URI = "IMAGE_URI"
private const val TAG = "PostFragment"
class PostFragment : DaggerFragment() {
    private lateinit var imageUri: Uri

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var binding: FragmentPostBinding

    private val viewModel by lazy {
        ViewModelProvider(requireActivity(), viewModelFactory).get(PostViewModel::class.java)
    }

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
        binding = FragmentPostBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        viewModel.imageUri = imageUri
        binding.postImage.setImageURI(imageUri)

        viewModel.postSuccess.observe(this, observer = Observer {
            Log.d(TAG, "onChange: called")
            Navigation.findNavController(view).popBackStack()
        })
    }
}