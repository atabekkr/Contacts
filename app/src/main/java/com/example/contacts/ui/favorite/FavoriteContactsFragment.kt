package com.example.contacts.ui.favorite

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.contacts.R
import com.example.contacts.databinding.FragmentContactsFavoriteBinding

class FavoriteContactsFragment: Fragment(R.layout.fragment_contacts_favorite) {
    private lateinit var binding: FragmentContactsFavoriteBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentContactsFavoriteBinding.bind(view)
    }
}