package org.example.project.presentation.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow

import org.example.project.domain.Contact
import org.example.project.domain.RequestState
import org.example.project.getLexendFont
import org.example.project.presentation.component.ErrorScreen
import org.example.project.presentation.component.LoadingScreen
import org.example.project.presentation.screen.contact.ContactScreen

object HomeScreen: Screen {
    @Composable
    override fun Content() {

        val navigator = LocalNavigator.currentOrThrow
        val viewModel = getScreenModel<HomeViewModel>()
        val contacts by viewModel.contacts

        MainScreen(navigator, viewModel, contacts)

    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navigator: Navigator, viewModel: HomeViewModel, contacts: RequestState<List<Contact>>) {

    Scaffold(
        topBar = { TopAppBar(title = { TopBar(viewModel, "Contacts") } ) },

        floatingActionButton = {
            FloatingActionButton(
                onClick = { navigator.push(ContactScreen(navigator)) },
                modifier = Modifier.padding(bottom = 68.dp)
            ){ Icon(
                Icons.Default.Person,
                "Add Contact"
            )
            } }
    ) { padding ->
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .padding(
                top = padding.calculateTopPadding(),
                bottom = padding.calculateBottomPadding()
            )) {

            SearchBar()

            Spacer(modifier = Modifier.height(16.dp))
            HorizontalDivider()

            contacts.DisplayResult(

                onError = { ErrorScreen() },
                onLoading = { LoadingScreen() },
                onSuccess = {

                    HorizontalDivider()
                    LazyColumn(modifier = Modifier.fillMaxWidth().padding(top = 16.dp)) {
                        items(
                            items = it,
                            key = { contact -> contact._id.toHexString() }
                        ){ contact ->
                            EachContact(
                                contact,
                                viewModel,
                                navigator
                            ) { navigator.push(ContactScreen(navigator, contact)) }
                            HorizontalDivider()
                        }

                    }
                }

            )
        }

    }

}

@Composable
fun SearchBar() {
    var search by remember { mutableStateOf("") }

    OutlinedTextField(
        value = search,
        onValueChange = { search = it },
        trailingIcon = { Icon(Icons.Default.Search, "Search") },
        label = { Text("Search") },
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.fillMaxWidth()
    )
}


@Composable
fun EachContact(
    contact: Contact,
    viewModel: HomeViewModel,
    navigator: Navigator,
    contactClicked: ()->Unit) {

    var isChecked by remember { mutableStateOf(false) }



    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .padding(vertical = 8.dp)
            .clickable { contactClicked() },
        verticalAlignment = Alignment.CenterVertically
    ){
        Checkbox(
            checked = isChecked,
            onCheckedChange = {
                isChecked = it
                viewModel.updateContact(it, contact)
            }
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            contact.fullName,
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium,
            fontFamily = getLexendFont()
        )

        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterEnd){
            CallButton(modifier = Modifier.size(30.dp)) {
                //IMPLEMENT CALLING FUNCTION
            }
        }

    }

}


@Composable
fun TopBar(viewModel: HomeViewModel? = null, text: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text,
            fontWeight = FontWeight.Bold,
            fontSize = 32.sp,
            fontFamily = getLexendFont()
        )

        IconButton(onClick = {
            viewModel?.deleteContact(viewModel.listOfCheckedContacts)
        }){
            Icon(
                Icons.Default.Delete,
                "Delete Selected Icons"
            )
        }
    }
}

@Composable
fun CallButton(modifier: Modifier = Modifier, callNumber: () -> Unit) {
    Card(
        modifier = modifier,
        shape = CircleShape
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.primary),
            contentAlignment = Alignment.Center
        ) {
            IconButton(onClick = {callNumber}){
                Icon(Icons.Default.Call, "Call")
            }
        }
    }
}
