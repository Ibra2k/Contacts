package org.example.project.presentation.screen.keypad

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.example.project.data.MongoDB
import org.example.project.domain.RecentCall

class KeypadViewModel(private val mongoDB: MongoDB) : ScreenModel {

    private var _number by mutableStateOf("")
    val number get() = _number

    fun addNumber(newNumber: Int){
        _number += newNumber
    }

    fun addSymbol(newSymbol: String) {
        _number += newSymbol
    }

    fun addRecentCall(recentCall: RecentCall){
        screenModelScope.launch(Dispatchers.IO) {
            mongoDB.insertRecent(recentCall)
        }
    }

    fun clearText(): () -> Unit = { _number = "" }


}