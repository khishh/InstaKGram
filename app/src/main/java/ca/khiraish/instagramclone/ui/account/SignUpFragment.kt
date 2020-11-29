package ca.khiraish.instagramclone.ui.account

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import ca.khiraish.instagramclone.R
import ca.khiraish.instagramclone.databinding.FragmentSignUpBinding
import dagger.android.support.DaggerFragment
import javax.inject.Inject

private const val TAG = "SignUpFragment"
class SignUpFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var binding: FragmentSignUpBinding

    private val viewModel by lazy {
        ViewModelProvider(requireActivity(), viewModelFactory).get(AccountViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onCreate: " + viewModel.hashCode())
        binding.viewModel = viewModel
        binding.signUpAlreadyUser.setOnClickListener {
            val signInDir = SignUpFragmentDirections.actionSignUpFragmentToSignInFragment();
            Navigation.findNavController(requireView()).navigate(signInDir)
        }
    }
}