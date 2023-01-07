package com.example.contacts.ui.all

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.contacts.R
import com.example.contacts.data.Contact
import com.example.contacts.data.ContactDatabase
import com.example.contacts.databinding.DialogContactAddBinding

class AddContactDialog: DialogFragment(R.layout.dialog_contact_add) {
    private lateinit var binding: DialogContactAddBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = DialogContactAddBinding.bind(view)

        val dao = ContactDatabase.getInstance(requireContext()).getContactDao()

        binding.apply {
            btnAdd.setOnClickListener {
                val name = etName.text.toString()
                val phone = etPhone.text.toString()

                if (name.isNotEmpty() && phone.isNotEmpty()) {
                    val contact = Contact(
                        name = name,
                        phone = phone,
                        image = "",
                        isFav = 0
                    )
                    dao.addContact(contact)
                    onAddSuccess.invoke()
                    dismiss()
                }
                else {
                    Toast.makeText(requireContext(), "Toltir", Toast.LENGTH_SHORT).show()
                }
            }

        }
    }

    private var onAddSuccess: () -> Unit = {}
    fun setOnAddSuccessListener(onAddSuccess: () -> Unit) {
        this.onAddSuccess = onAddSuccess
    }
}