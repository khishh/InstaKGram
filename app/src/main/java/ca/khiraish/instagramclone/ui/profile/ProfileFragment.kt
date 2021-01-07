package ca.khiraish.instagramclone.ui.profile

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ca.khiraish.instagramclone.R
import ca.khiraish.instagramclone.util.ItemOffsetDecoration
import dagger.android.support.DaggerFragment
import javax.inject.Inject

private const val TAG = "ProfileFragment"
class ProfileFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(ProfileViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val postAdapter = PostAdapter()
        val recyclerView = view.findViewById<RecyclerView>(R.id.profile_recyclerView)
        recyclerView.adapter = postAdapter
        recyclerView.addItemDecoration(ItemOffsetDecoration(1))
        recyclerView.layoutManager = GridLayoutManager(context, 3)
        viewModel.myPosts.observe(viewLifecycleOwner){
            Log.d(TAG, "===== onChanged myPosts: $it")
            postAdapter.submitList(it)
            //postAdapter.notifyDataSetChanged()
        }
        viewModel.fetchMyPost()
    }


}