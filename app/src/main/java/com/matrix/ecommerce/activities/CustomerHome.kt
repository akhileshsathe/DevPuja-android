package com.matrix.ecommerce.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.matrix.ecommerce.fragments.CustomerCartFragment
import com.matrix.ecommerce.fragments.CustomerFavFragment
import com.matrix.ecommerce.fragments.CustomerHomeFragment
import com.matrix.ecommerce.fragments.CustomerProfileFragment
import com.matrix.ecommerce.R
import com.matrix.ecommerce.databinding.ActivityCustomerHomeBinding

class CustomerHome : AppCompatActivity() {

    private lateinit var binding: ActivityCustomerHomeBinding
    private lateinit var bottomNavigation: BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= ActivityCustomerHomeBinding.inflate((layoutInflater))
        setContentView(binding.root)

        bottomNavigation =binding.bottomNav
        replaceFragment(CustomerHomeFragment())
        bottomNavigation.setOnItemSelectedListener {
            when(it.itemId){
                R.id.home -> replaceFragment(CustomerHomeFragment())
                R.id.account ->replaceFragment(CustomerProfileFragment())
                R.id.cart ->replaceFragment(CustomerCartFragment())
                R.id.fav ->replaceFragment(CustomerFavFragment())
                else->{}

            }
            true
        }



    }
    private fun replaceFragment(fragment: Fragment){

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.frameLayout,fragment)
            commit()
        }


    }



}