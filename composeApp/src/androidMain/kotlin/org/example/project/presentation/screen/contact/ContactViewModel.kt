package org.example.project.presentation.screen.contact

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.example.project.data.MongoDB
import org.example.project.domain.Contact

class ContactViewModel(private val mongoDB: MongoDB) : ScreenModel{

    fun addContact(contact: Contact){
        screenModelScope.launch(Dispatchers.IO) {
            mongoDB.insertContact(contact)
        }
    }

    fun updateContact(contact: Contact){
        screenModelScope.launch(Dispatchers.IO) {
            mongoDB.updateContact(contact)
        }
    }
}