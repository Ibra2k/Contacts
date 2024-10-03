package org.example.project

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import org.example.project.data.MongoDB
import org.example.project.presentation.screen.contact.ContactViewModel
import org.example.project.presentation.screen.home.HomeScreen
import org.example.project.presentation.screen.home.HomeViewModel
import org.koin.core.context.GlobalContext.startKoin
import org.koin.dsl.module

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            initKoin()

            Navigator(
                screen = HomeScreen(),
            ){
                SlideTransition(it)
            }


        }
    }

    val module = module {
        single { MongoDB() }
        factory { HomeViewModel(get()) }
        factory { ContactViewModel(get()) }
    }

    fun initKoin(){
        startKoin{
            modules(module)
        }
    }


}


