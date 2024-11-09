package org.example.project

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabDisposable
import cafe.adriel.voyager.navigator.tab.TabNavigator
import contactapp.composeapp.generated.resources.Res
import org.example.project.data.MongoDB
import org.example.project.presentation.screen.contact.ContactViewModel
import org.example.project.presentation.screen.home.HomeTab
import org.example.project.presentation.screen.home.HomeViewModel
import org.example.project.presentation.screen.keypad.KeyTab
import org.example.project.presentation.screen.keypad.KeypadViewModel
import org.example.project.presentation.screen.recents.RecentCallsViewModel
import org.example.project.presentation.screen.recents.RecentTab
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.koin.core.context.GlobalContext.startKoin
import org.koin.dsl.module


val lightRedColor = Color(color = 0xFFF57D88)
val darkRedColor = Color(color = 0xFF77000B)

val lightColors = lightColorScheme(
    primary = lightRedColor,
    onPrimary = darkRedColor,
    primaryContainer = lightRedColor,
    onPrimaryContainer = darkRedColor
)
val darkColors = darkColorScheme(
    primary = lightRedColor,
    onPrimary = darkRedColor,
    primaryContainer = lightRedColor,
    onPrimaryContainer = darkRedColor
)

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter",
        "UnusedMaterialScaffoldPaddingParameter", "UnrememberedMutableState"
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val colors by mutableStateOf(
                darkColors
            )

            MaterialTheme(colors) {


                initKoin()


                TabNavigator(
                    HomeTab,
                    tabDisposable = {
                        TabDisposable(
                            navigator = it,
                            tabs = listOf(HomeTab, KeyTab, RecentTab)
                        )
                    }
                ) { tabNavigator ->
                    Scaffold(
                        content = {
                            CurrentTab()
                        },
                        contentColor = MaterialTheme.colorScheme.primaryContainer,
                        bottomBar = {
                            NavigationBar(
                                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                                modifier = Modifier.height(64.dp)
                            ) {
                                TabNavigationItem(HomeTab)
                                TabNavigationItem(KeyTab)
                                TabNavigationItem(RecentTab)
                            }
                        },
                        modifier = Modifier.wrapContentHeight()
                    )
                }

            }
        }
    }
    val module = module {
        single { MongoDB() }
        factory { HomeViewModel(get()) }
        factory { ContactViewModel(get()) }
        factory { KeypadViewModel(get()) }
        factory { RecentCallsViewModel(get()) }
    }

    fun initKoin(){
        startKoin{
            modules(module)
        }
    }
}


@Composable
private fun RowScope.TabNavigationItem(tab: Tab) {
    val tabNavigator = LocalTabNavigator.current

    NavigationBarItem(
        selected = tabNavigator.current.key == tab.key,
        onClick = { tabNavigator.current = tab },
        icon = { Icon(painter = tab.options.icon!!, contentDescription = tab.options.title) }
    )
}

@Composable
fun getLexendFont() = FontFamily(Font(R.font.lexend_variable_font))