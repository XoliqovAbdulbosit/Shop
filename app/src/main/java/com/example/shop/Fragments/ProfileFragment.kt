package com.example.shop.Fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.example.shop.API.APIClient
import com.example.shop.API.APIService
import com.example.shop.Adapters.CartAdapter
import com.example.shop.DataClasses.CartList
import com.example.shop.DataClasses.User
import com.example.shop.R
import com.example.shop.databinding.FragmentProfileBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val binding = FragmentProfileBinding.inflate(inflater, container, false)
        binding.carts.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        val api = APIClient.getInstance().create(APIService::class.java)
        val sharedPref = requireContext().getSharedPreferences("myPref", Context.MODE_PRIVATE)
        val savedValue = sharedPref.getInt("id", -1)
        if (savedValue == -1) {
            findNavController().navigate(R.id.action_profileFragment_to_loginFragment)
        } else {
            api.getUser(savedValue.toString()).enqueue(object : Callback<User> {
                override fun onResponse(call: Call<User>, response: Response<User>) {
                    binding.profileImg.load(response.body()!!.image)
                    binding.name.text =
                        "${response.body()!!.firstName} ${response.body()!!.lastName}"
                    binding.email.text = response.body()!!.email
                }

                override fun onFailure(call: Call<User>, t: Throwable) {
                    Log.d("TAG", "$t")
                }
            })

            api.getAllCarts(savedValue.toString()).enqueue(object : Callback<CartList> {
                override fun onResponse(call: Call<CartList>, response: Response<CartList>) {
                    val carts = response.body()!!.carts[0].products
                    binding.carts.adapter = CartAdapter(carts)
                }

                override fun onFailure(call: Call<CartList>, t: Throwable) {
                    Log.d("TAG", "$t")
                }
            })
        }

        binding.cartBack.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_homeFragment)
        }

        binding.logout.setOnClickListener {
            val editor = sharedPref.edit()
            editor.putInt("id", -1)
            editor.apply()
            findNavController().navigate(R.id.action_profileFragment_to_homeFragment)
        }
        return binding.root
    }
}