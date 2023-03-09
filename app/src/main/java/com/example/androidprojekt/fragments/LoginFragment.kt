package com.example.androidprojekt.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.androidprojekt.R
import com.example.androidprojekt.databinding.FragmentLoginBinding
import com.example.androidprojekt.services.LogInService

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)

        binding.buttonLogin.setOnClickListener {
            attemptLogin(binding.inputUsername.text.toString(), binding.inputPassword.text.toString())
        }
        return binding.root
    }

    private fun attemptLogin(username: String, password: String){
        binding.labelLoginError.visibility = View.INVISIBLE
        if(LogInService.logIn(username, password)){
            findNavController().navigate(R.id.action_LoginFragment_to_MapFragment)
        }
        else{
            Toast.makeText(this.requireContext(), "Unsuccessful login",Toast.LENGTH_SHORT).show()
            binding.labelLoginError.visibility = View.VISIBLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}