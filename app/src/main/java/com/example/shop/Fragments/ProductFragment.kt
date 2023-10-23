package com.example.shop.Fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.shop.Adapters.ImageAdapter
import com.example.shop.databinding.FragmentProductBinding
import com.example.shop.DataClasses.Product
import com.example.shop.R

private const val ARG_PARAM1 = "product"

class ProductFragment : Fragment() {
    private lateinit var product: Product
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { product = it.getSerializable(ARG_PARAM1) as Product }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val binding = FragmentProductBinding.inflate(inflater, container, false)
        binding.productBack.setOnClickListener {
            requireActivity().onBackPressed()
        }

        binding.productImage.adapter = ImageAdapter(product.images, binding.productImage)
        binding.productTitle.text = product.title
        binding.productBrand.text = product.brand
        binding.productPrice.text = product.price.toString() + " $"
        binding.productDescription.text = product.description
        binding.productRating.text = product.rating.toString()

        binding.login.setOnClickListener {
            val sharedPref = requireContext().getSharedPreferences("myPref", Context.MODE_PRIVATE)
            val savedValue = sharedPref.getInt("id", -1)
            if (savedValue == -1) {
                findNavController().navigate(R.id.action_productFragment_to_loginFragment)
            } else {
                findNavController().navigate(R.id.action_productFragment_to_profileFragment)
            }
        }

        return binding.root
    }
}