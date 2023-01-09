package com.example.contacts.ui.all

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.contacts.R
import com.example.contacts.data.Contact
import com.example.contacts.data.ContactDao
import com.example.contacts.data.ContactDatabase
import com.example.contacts.databinding.FragmentContactsAllBinding
import com.example.contacts.ui.ContactAdapter
import com.google.android.material.snackbar.Snackbar

class AllContactsFragment: Fragment(R.layout.fragment_contacts_all) {
    private lateinit var binding: FragmentContactsAllBinding
    private val adapter = ContactAdapter()
    private lateinit var db: ContactDatabase
    private lateinit var contactDao: ContactDao

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentContactsAllBinding.bind(view)

        db = ContactDatabase.getInstance(requireContext())
        contactDao = db.getContactDao()

        binding.apply {
            recyclerView.adapter = adapter

            adapter.setOnDeleteClickListener {  contact ->  
                contactDao.deleteContact(contact)
                Snackbar.make(fabAddContact, "Contact deleted successfully!", Snackbar.LENGTH_SHORT)
                    .show()
            }

            adapter.setOnCallClickListener {
                val intent = Intent(Intent.ACTION_DIAL)
                intent.data = Uri.parse("tel:${it.phone}")
                startActivity(intent)
            }

            adapter.contacts = contactDao.getAllContacts().toMutableList()

            fabAddContact.setOnClickListener {
                val dialog = AddContactDialog()
                dialog.show(requireActivity().supportFragmentManager, dialog.tag)

                dialog.setOnAddSuccessListener {
                    adapter.contacts = contactDao.getAllContacts().toMutableList()

                    Snackbar.make(fabAddContact, "Contact added successfully!", Snackbar.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }
}