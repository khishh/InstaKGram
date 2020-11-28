package ca.khiraish.instagramclone.ui.account

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import ca.khiraish.instagramclone.R
import ca.khiraish.instagramclone.databinding.FragmentSignInBinding
import dagger.android.support.DaggerFragment
import javax.inject.Inject


class SignInFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var binding: FragmentSignInBinding

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(AccountViewModel::class.java)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSignInBinding.inflate(inflater, container, false)
        return binding.root
    }
//
//    override fun on(savedInstanceState: Bundle?) {
//        super.onActivityCreated(savedInstanceState)
//        binding.viewModel = viewModel
//        binding.loginLogInBtn.setOnClickListener {
//            val action = SignInFragmentDirections.actionSignInFragmentToSignUpFragment();
//            Navigation.findNavController(requireView()).navigate(action)
//        }
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel = viewModel
        binding.loginLogInBtn.setOnClickListener {
            val action = SignInFragmentDirections.actionSignInFragmentToSignUpFragment();
            Navigation.findNavController(requireView()).navigate(action)
        }
    }
}