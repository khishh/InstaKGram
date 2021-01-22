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
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

private const val TAG = "TimelineFragment"

class TimelineFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by lazy {
        ViewModelProvider(requireActivity(), viewModelFactory).get(TimelineViewModel::class.java)
    }

    private lateinit var binding: FragmentTimelineBinding
    private val disposables = CompositeDisposable()

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
        val timelineAdapter = PostTimelineAdapter(viewModel)
        recyclerView.adapter = timelineAdapter
        recyclerView.layoutManager = LinearLayoutManager(context)

        viewModel.getAllFollowers()

        viewModel.behaviorSubject.subscribeBy(
            onNext = {posts->
                Log.d(TAG, "onChanged: followingUserPosts called")
                timelineAdapter.submitList(posts)},
            onError = {error->
                Log.d(TAG, "behaviorSubject Error: $error")
            }
        ).addTo(disposables)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        disposables.clear()
    }
}