package org.example.project.presentation.screen.home

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.example.project.data.MongoDB
import org.example.project.domain.Contact
import org.example.project.domain.RequestState


typealias MutableContacts = MutableState<RequestState<List<Contact>>>
typealias Contacts = MutableState<RequestState<List<Contact>>>

class HomeViewModel(private val mongoDB: MongoDB) : ScreenModel{

    private var _contacts: MutableContacts = mutableStateOf(RequestState.Idle)
    val contacts: Contacts = _contacts

    val listOfCheckedContacts = mutableStateListOf<Contact>()


    init{
        _contacts.value = RequestState.Loading
        screenModelScope.launch(Dispatchers.IO) {
            delay(500)

            mongoDB.readContacts().collectLatest { contacts ->
                _contacts.value = contacts
            }
        }
    }

    fun deleteContact(checkedContacts: List<Contact>){
        screenModelScope.launch(Dispatchers.IO) {
            mongoDB.deleteContact(checkedContacts)
        }
    }

    fun updateContact(newValue: Boolean, contact: Contact){
        if(newValue){ listOfCheckedContacts.add(contact) }
        else {listOfCheckedContacts.remove(contact)}
    }

}