package ca.khiraish.instagramclone.ui.account

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import ca.khiraish.instagramclone.databinding.FragmentSignInBinding
import dagger.android.support.DaggerFragment
import javax.inject.Inject

private const val TAG = "SignInFragment"
class SignInFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var binding: FragmentSignInBinding

    private val viewModel by lazy {
        ViewModelProvider(requireActivity(), viewModelFactory).get(AccountViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSignInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onCreate: " + viewModel.hashCode())
        binding.viewModel = viewModel
        binding.loginNewUser.setOnClickListener {
            val signUpDir = SignInFragmentDirections.actionSignInFragmentToSignUpFragment();
            Navigation.findNavController(requireView()).navigate(signUpDir)
        }
    }
}