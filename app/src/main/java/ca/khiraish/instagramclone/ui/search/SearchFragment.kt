package ca.khiraish.instagramclone.ui.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ca.khiraish.instagramclone.R
import ca.khiraish.instagramclone.data.model.User
import dagger.android.support.DaggerFragment
import javax.inject.Inject

private const val TAG = "SearchFragment"
class SearchFragment : DaggerFragment() {


//    TODO private val adapter : SearchAdapter()

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(SearchViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val userAdapter = UserAdapter()
        val concatAdapter = ConcatAdapter(userAdapter)
        val recyclerView = view.findViewById<RecyclerView>(R.id.search_recyclerview)
        recyclerView.adapter = concatAdapter
        recyclerView.layoutManager = LinearLayoutManager(view.context)
        viewModel.getAllUsers()
        viewModel.users.observe(viewLifecycleOwner){
            it?.let {
                Log.d(TAG, "onChange: called")
                println(it.toString())
                userAdapter.submitList(it as MutableList<User>)
            }
        }
    }

}