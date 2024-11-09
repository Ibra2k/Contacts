package org.example.project.presentation.screen.recents

import android.annotation.SuppressLint
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import org.example.project.domain.RecentCall
import org.example.project.domain.RequestState
import org.example.project.getLexendFont
import org.example.project.presentation.component.ErrorScreen
import org.example.project.presentation.component.LoadingScreen
import org.example.project.presentation.screen.home.CallButton
import java.text.SimpleDateFormat
import java.util.Date

object RecentTab : Tab{
    override val options: TabOptions

        @Composable
        get() {
            val title = "Recent"
            val icon = rememberVectorPainter(Icons.Default.Notifications)

            return remember {
                TabOptions(
                    index = 2u,
                    title = title,
                    icon = icon
                )
            }

        }

    @Composable
    override fun Content() {
        val viewModel = getScreenModel<RecentCallsViewModel>()
        val recentCalls by viewModel.recentCalls

        Recent(viewModel, recentCalls)
    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Recent(viewModel: RecentCallsViewModel, recentCalls: RequestState<List<RecentCall>>) {


    Scaffold(
        topBar = { TopAppBar(title = { TopRecentBar(viewModel = viewModel, text = "Recents") }) },
    ) { padding ->

        Column(modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .padding(
                top = padding.calculateTopPadding(),
                bottom = padding.calculateBottomPadding()
            )) {

            Spacer(modifier = Modifier.height(20.dp))
            HorizontalDivider()

            recentCalls.DisplayResult(
                onLoading = { LoadingScreen() },
                onError = { ErrorScreen() },
                onSuccess = {
                    HorizontalDivider()
                    LazyColumn(modifier = Modifier.fillMaxWidth().padding(top = 16.dp)) {
                        items(
                            items = it,
                            key = { recentCall -> recentCall._id.toHexString() }
                        ){ eachRecent ->
                            EachRecentCall(
                                Modifier.fillMaxWidth()
                                    .clip(RoundedCornerShape(16.dp))
                                    .padding(vertical = 16.dp),
                                eachRecent,
                                viewModel
                            )
                            HorizontalDivider()
                        }

                    }
                }
            )

        }
    }
}

@Composable
fun EachRecentCall(
    modifier: Modifier = Modifier,
    eachRecentCall: RecentCall,
    viewModel: RecentCallsViewModel
) {

    var isChecked by remember { mutableStateOf(false) }

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Checkbox(
            checked = isChecked,
            onCheckedChange = {
                isChecked = it
                viewModel.updateContact(it, eachRecentCall)
            }
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            eachRecentCall.phoneNumber,
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
fun TopRecentBar(text: String, viewModel: RecentCallsViewModel) {
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
            viewModel.deleteRecent(viewModel.listOfCheckedContacts)
        }){
            Icon(
                Icons.Default.Delete,
                "Delete Selected Icons"
            )
        }
    }
}

@SuppressLint("SimpleDateFormat")
fun formatTime(date: Date): String{
    val formatter = SimpleDateFormat("HH:mm")
    return formatter.format(date)
}