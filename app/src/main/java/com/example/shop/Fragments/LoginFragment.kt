package com.example.shop.Fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.shop.API.APIClient
import com.example.shop.API.APIService
import com.example.shop.DataClasses.Login
import com.example.shop.DataClasses.User
import com.example.shop.R
import com.example.shop.databinding.FragmentLoginBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val binding = FragmentLoginBinding.inflate(inflater, container, false)

        binding.submit.setOnClickListener {
            val api = APIClient.getInstance().create(APIService::class.java)
            val l = Login(binding.username.text.toString(), binding.password.text.toString())
            api.login(l).enqueue(object : Callback<User> {
                override fun onResponse(call: Call<User>, response: Response<User>) {
                    if (response.isSuccessful && response.body() != null) {
                        val sharedPref =
                            context!!.getSharedPreferences("myPref", Context.MODE_PRIVATE)
                        val editor = sharedPref.edit()
                        editor.putInt("id", response.body()!!.id)
                        editor.apply()
                        findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                    }
                }

                override fun onFailure(call: Call<User>, t: Throwable) {
                    Log.d("TAG", "$t")
                }
            })
        }

        binding.home.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
        }

        return binding.root
    }
}