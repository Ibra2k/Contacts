package org.example.project.presentation.screen.contact

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.Navigator
import org.example.project.domain.Contact

const val FNAME = ""
const val NNAME = ""
const val NUMBER = ""
const val PLACE = ""

class ContactScreen(val navigator: Navigator, val contact: Contact? = null) : Screen{
    @Composable
    override fun Content() {
        val viewModel = getScreenModel<ContactViewModel>()

        MainScreen(navigator, viewModel, contact)

    }


    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun MainScreen(navigator: Navigator, viewModel: ContactViewModel, contact: Contact?) {

        var fName by remember { mutableStateOf(contact?.fullName ?: FNAME) }
        var nName by remember { mutableStateOf(contact?.nickName ?: NNAME) }
        var number by remember { mutableStateOf(contact?.nickName ?: NUMBER) }
        var placeFrom by remember { mutableStateOf(contact?.nickName ?: PLACE) }



        Scaffold(
            topBar = { TopAppBar(title = { Text(
                "New Contact",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold
            )}
                , navigationIcon = { IconButton(onClick = {navigator.pop()})
                { Icon(Icons.AutoMirrored.Filled.ArrowBack, "Go Back")}
                })},
            floatingActionButton = { FloatingActionButton(onClick = {

                //Handles if Contact was click or Create Contact clicked

                if (fName.isNotEmpty()) {
                    if (contact != null) {
                        viewModel.updateContact(
                            Contact().apply {
                                _id = contact._id
                                fullName = fName
                                nickName = nName
                                phoneNumber = number
                                business = placeFrom
                            }
                        )
                    } else {
                        viewModel.addContact(
                            Contact().apply {
                                fullName = fName
                                nickName = nName
                                phoneNumber = number
                                business = placeFrom
                            }
                        )
                    }
                    navigator.pop()

                }
            }

            ){
                Icon(Icons.Default.Check, "Update/Create")
            } }
        ) { padding ->

            Column(modifier = Modifier.fillMaxSize().padding(8.dp).padding(
                top = padding.calculateTopPadding(),
                bottom = padding.calculateBottomPadding()
            )) {


                OutlinedTextField(
                    value = fName,
                    label = { Text("Name")},
                    onValueChange = { fName = it },
                    shape = RoundedCornerShape(16.dp),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
                )
                OutlinedTextField(
                    value = nName,
                    label = { Text("Nick Name")},
                    onValueChange = { nName = it },
                    shape = RoundedCornerShape(16.dp),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
                )
                OutlinedTextField(
                    value = number,
                    label = { Text("Number")},
                    onValueChange = { number = it },
                    shape = RoundedCornerShape(16.dp),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
                )
                OutlinedTextField(
                    value = placeFrom,
                    label = { Text("Business")},
                    onValueChange = { placeFrom = it },
                    shape = RoundedCornerShape(16.dp),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
                )
            }

        }
    }

}
