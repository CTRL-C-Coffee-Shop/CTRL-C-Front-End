package com.example.ctrl_c.ui.profile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.ctrl_c.data.local.UserPreference
import com.example.ctrl_c.databinding.FragmentProfileBinding
import com.example.ctrl_c.ui.authentication.login.LoginActivity

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentProfileBinding.inflate(LayoutInflater.from(requireActivity()))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAction()
    }

    private fun setupAction() {
        binding.editProfile.setOnClickListener {
            navigateToEditProfileActivity()
        }

        binding.logoutButton.setOnClickListener {
            val pref = UserPreference(requireContext())
            pref.clearPreferences()
            navigateToLoginActivity()
        }
    }

    private fun navigateToEditProfileActivity() {
        val intent = Intent(activity, EditProfileActivity::class.java)
        startActivity(intent)
    }

    private fun navigateToLoginActivity(){
        val intent = Intent(activity,LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

}