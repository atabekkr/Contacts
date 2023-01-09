package com.example.contacts.ui.all

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.contacts.R
import com.example.contacts.data.Contact
import com.example.contacts.data.ContactDao
import com.example.contacts.data.ContactDatabase
import com.example.contacts.databinding.FragmentContactEditBinding

class EditContactFragment: Fragment(R.layout.fragment_contact_edit) {
    private lateinit var binding: FragmentContactEditBinding
    private lateinit var db: ContactDatabase
    private lateinit var contactDao: ContactDao

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentContactEditBinding.bind(view)

        db = ContactDatabase.getInstance(requireContext())
        contactDao = db.getContactDao()

        val id = arguments?.getInt("id") ?: 0
        val oldName = arguments?.getString("name")
        val oldPhone = arguments?.getString("phone")
        val image = arguments?.getString("image")
        val isFavorite = arguments?.getInt("isFavorite")


        binding.apply {
            etName.setText(oldName)
            etPhone.setText(oldPhone)

            btnSave.setOnClickListener {
                val name = etName.text.toString()
                val phone = etPhone.text.toString()

                if (name.isNotEmpty() && phone.isNotEmpty()) {
                    val contact = Contact(
                        id = id,
                        name = name,
                        phone = phone,
                        image = image ?: "",
                        isFav = isFavorite ?: 0
                    )
                    contactDao.updateContact(contact)
                } else {
                    Toast.makeText(requireContext(), "Toltir", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}