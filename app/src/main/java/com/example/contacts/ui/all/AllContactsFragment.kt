package com.example.contacts.ui.all

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.contacts.R
import com.example.contacts.data.Contact
import com.example.contacts.databinding.FragmentContactsAllBinding
import com.example.contacts.ui.ContactAdapter

class AllContactsFragment: Fragment(R.layout.fragment_contacts_all) {
    private lateinit var binding: FragmentContactsAllBinding
    private val adapter = ContactAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentContactsAllBinding.bind(view)

        binding.apply {
            recyclerView.adapter = adapter

            fabAddContact.setOnClickListener {

            }
        }
    }
}