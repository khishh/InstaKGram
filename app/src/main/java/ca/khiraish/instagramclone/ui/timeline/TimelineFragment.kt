package ca.khiraish.instagramclone.ui.timeline

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ca.khiraish.instagramclone.R
import ca.khiraish.instagramclone.databinding.FragmentTimelineBinding
import ca.khiraish.instagramclone.util.PostAdapter
import ca.khiraish.instagramclone.util.PostTimelineAdapter
import dagger.android.support.DaggerFragment
import javax.inject.Inject

private const val TAG = "TimelineFragment"

class TimelineFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by lazy {
        ViewModelProvider(requireActivity(), viewModelFactory).get(TimelineViewModel::class.java)
    }

    private lateinit var binding: FragmentTimelineBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTimelineBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = view.findViewById<RecyclerView>(R.id.timeline_recyclerview)
        val timelineAdapter = PostTimelineAdapter()
        recyclerView.adapter = timelineAdapter
        recyclerView.layoutManager = LinearLayoutManager(context)
        viewModel.getAllFollowers()
        viewModel.followingUsers.observe(viewLifecycleOwner){
            Log.d(TAG, "onChange: followingUsers called")
            println(it.toString())
            viewModel.getAllFollowingsPost()
        }
        viewModel.followingUserPosts.observe(viewLifecycleOwner){
            Log.d(TAG, "onChanged: followingUserPosts called")
            println(it.toString())
            timelineAdapter.submitList(it)
        }
    }
}