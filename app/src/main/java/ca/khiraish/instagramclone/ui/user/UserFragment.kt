package ca.khiraish.instagramclone.ui.user

import android.net.Uri
import android.opengl.Visibility
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.GridLayoutManager
import ca.khiraish.instagramclone.R
import ca.khiraish.instagramclone.databinding.FragmentUserBinding
import ca.khiraish.instagramclone.util.ItemOffsetDecoration
import ca.khiraish.instagramclone.util.PostAdapter
import dagger.android.support.DaggerFragment
import javax.inject.Inject

private const val TAG = "UserFragment"
class UserFragment : DaggerFragment() {

    private lateinit var userId: String

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(UserViewModel::class.java)
    }

    private lateinit var binding: FragmentUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(arguments != null) {
            userId = requireArguments().getString("userId").toString()
            Log.d(TAG, "onCreate: userId == $userId")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentUserBinding.inflate(inflater, container, false);
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = binding.userRecyclerView
        binding.userFollowBtn.setOnClickListener {
            viewModel.updateFollowingStatus(userId)
            viewModel.updateFollowerStatus(userId)
        }
        if(viewModel.getOwnerUserId().equals(userId)){ //TODO check if the owner is looking at his own profile
            binding.userFollowBtn.visibility = View.GONE
            binding.userMessageBtn.visibility = View.GONE
        }
        val postAdapter = PostAdapter() // TODO add listener postCLick to its argument
        recyclerView.adapter = postAdapter
        recyclerView.layoutManager = GridLayoutManager(context, 3)
        recyclerView.addItemDecoration(ItemOffsetDecoration(1))
//        viewModel.userId = userId
        binding.viewmodel = viewModel
        viewModel.userPosts.observe(viewLifecycleOwner){
            Log.d(TAG, "===== onChanged userPosts $it")
            postAdapter.submitList(it)
        }
        viewModel.userPhotoUri.observe(viewLifecycleOwner){
            Log.d(TAG, "===== onChanged userPhotoUri $it ")
            if(!it.isNullOrEmpty()){
                binding.userUserImage.setImageURI(Uri.parse(it))
            }else{
                binding.userUserImage.setImageResource(R.mipmap.ic_launcher_round)
            }
        }
//        viewModel.user.observe(viewLifecycleOwner){
//            Log.d(TAG, "===== onChanged user $it ")
//            viewModel.getAllFollowings(userId)
//            viewModel.getAllFollowers(userId)
//        }
        viewModel.fetchUserInfo(userId)
        viewModel.getFollowingStatus(userId)
        viewModel.fetchUserPosts(userId)
        viewModel.getAllFollowings(userId) //Todo
        viewModel.getAllFollowers(userId)
    }
}