package org.example.project.presentation.screen.recents

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.example.project.data.MongoDB
import org.example.project.domain.Contact
import org.example.project.domain.RecentCall
import org.example.project.domain.RequestState


typealias MutableRecentCalls = MutableState<RequestState<List<RecentCall>>>
typealias RecentCalls = MutableState<RequestState<List<RecentCall>>>


class RecentCallsViewModel(private val mongoDB: MongoDB): ScreenModel{

    private var _recentCalls: MutableRecentCalls = mutableStateOf(RequestState.Idle)
    val recentCalls: RecentCalls = _recentCalls

    val listOfCheckedContacts = mutableStateListOf<RecentCall>()



    init{
        _recentCalls.value = RequestState.Loading
        screenModelScope.launch(Dispatchers.IO) {
            delay(500)

            mongoDB.readRecentCalls().collectLatest { recentCalls ->
                _recentCalls.value = recentCalls
            }
        }
    }


    fun deleteRecent(checkedContacts: List<RecentCall>){
        screenModelScope.launch(Dispatchers.IO) {
            mongoDB.deleteRecent(checkedContacts)
        }
    }

    fun updateContact(newValue: Boolean, recentCall: RecentCall){
        if(newValue){ listOfCheckedContacts.add(recentCall) }
        else {listOfCheckedContacts.remove(recentCall)}
    }

}