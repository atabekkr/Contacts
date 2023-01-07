package com.example.contacts.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.contacts.R
import com.example.contacts.databinding.ActivityMainBinding
import com.example.contacts.ui.all.AllContactsFragment
import com.example.contacts.ui.favorite.FavoriteContactsFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction().add(
            R.id.fragment_container, AllContactsFragment())
            .commit()

        binding.apply {
            bnvMain.setOnItemSelectedListener {
                when (it.itemId) {
                    R.id.item_all -> {
                        supportFragmentManager.beginTransaction().add(
                            R.id.fragment_container, AllContactsFragment())
                            .commit()
                    }
                    R.id.item_favorite -> {
                        supportFragmentManager.beginTransaction().add(
                            R.id.fragment_container, FavoriteContactsFragment()
                        ).commit()
                    }
                }
                true
            }
        }
    }
}