package com.example.shop.Fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.example.shop.R
import com.example.shop.Adapters.CategoryAdapter
import com.example.shop.Adapters.ProductAdapter
import com.example.shop.databinding.FragmentHomeBinding
import com.example.shop.DataClasses.Product
import com.example.shop.DataClasses.ProductData
import com.example.shop.API.APIClient
import com.example.shop.API.APIService

class HomeFragment : Fragment() {
    lateinit var binding: FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.products.layoutManager =
            GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
        binding.categories.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        val api = APIClient.getInstance().create(APIService::class.java)

        api.getAll().enqueue(object : Callback<ProductData> {
            override fun onResponse(call: Call<ProductData>, response: Response<ProductData>) {
                val products = response.body()?.products!!
                changeProductsAdapter(products)
            }

            override fun onFailure(call: Call<ProductData>, t: Throwable) {
                Log.d("TAG", "$t")
            }
        })

        api.getCategories().enqueue(object : Callback<List<String>> {
            override fun onResponse(call: Call<List<String>>, response: Response<List<String>>) {
                val categories = response.body()!!
                binding.categories.adapter = CategoryAdapter(categories,
                    requireContext(),
                    object : CategoryAdapter.CategoryPressed {
                        override fun onPressed(category: String) {
                            if (category == "") {
                                api.getAll().enqueue(object : Callback<ProductData> {
                                    override fun onResponse(
                                        call: Call<ProductData>, response: Response<ProductData>
                                    ) {
                                        val products = response.body()!!.products
                                        changeProductsAdapter(products)
                                    }

                                    override fun onFailure(call: Call<ProductData>, t: Throwable) {
                                        Log.d("TAG", "$t")
                                    }
                                })
                            } else {
                                api.getByCategory(category).enqueue(object : Callback<ProductData> {
                                    override fun onResponse(
                                        call: Call<ProductData>, response: Response<ProductData>
                                    ) {
                                        val products = response.body()?.products!!
                                        changeProductsAdapter(products)
                                    }

                                    override fun onFailure(call: Call<ProductData>, t: Throwable) {
                                        Log.d("TAG", "$t")
                                    }
                                })
                            }
                        }
                    })
            }

            override fun onFailure(call: Call<List<String>>, t: Throwable) {
                Log.d("TAG", "$t")
            }
        })

        binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                api.search(query!!).enqueue(object : Callback<ProductData> {
                    override fun onResponse(
                        call: Call<ProductData>, response: Response<ProductData>
                    ) {
                        val products = response.body()!!.products
                        changeProductsAdapter(products)
                    }

                    override fun onFailure(call: Call<ProductData>, t: Throwable) {
                        Log.d("TAG", "$t")
                    }
                })
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })

        binding.profile.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_profileFragment)
        }
        return binding.root
    }

    fun changeProductsAdapter(products: List<Product>) {
        binding.products.adapter = ProductAdapter(products, object : ProductAdapter.ProductPressed {
            override fun onPressed(product: Product) {
                val bundle = Bundle()
                bundle.putSerializable("product", product)
                findNavController().navigate(R.id.action_homeFragment_to_productFragment, bundle)
            }
        })
    }
}