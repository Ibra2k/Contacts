package org.example.project.presentation.screen.keypad

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import org.example.project.domain.RecentCall
import org.example.project.getLexendFont
import org.example.project.presentation.screen.recents.RecentTab
import java.time.LocalDate
import java.time.ZoneId
import java.util.Date

var keypadSize: Dp = 120.dp

const val numberCallName = "UNKNOWN"
const val numberCallNickName = "UNKNOWN"
const val numberCallBusiness = "UNKNOWN"

object KeyTab : Tab{
    override val options: TabOptions

        @Composable
        get() {
            val title = "Phone"
            val icon = rememberVectorPainter(Icons.Default.Phone)

            return remember{
                TabOptions(
                    index = 1u,
                    title = title,
                    icon = icon
                )
            }
        }

    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    override fun Content() {
        val viewModel = getScreenModel<KeypadViewModel>()
        val navigator = LocalNavigator.currentOrThrow

        Column(
            modifier = Modifier.fillMaxSize().padding(bottom = 70.dp),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            ClearButton { viewModel.clearText() }
            Spacer(modifier = Modifier.height(80.dp))
            NumberDisplay(viewModel)
            Spacer(modifier = Modifier.height(80.dp))
            KeypadDisplay(viewModel, navigator)
        }
    }

}

@Composable
fun NumberDisplay(viewModel: KeypadViewModel) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ){
        Text(
            viewModel.number,
            fontSize = 36.sp,
            fontWeight = FontWeight.Medium
        )
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun KeypadDisplay(viewModel: KeypadViewModel, navigator: Navigator) {


    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Card(
            modifier = Modifier
                .size(keypadSize),
            onClick = { viewModel.addNumber(1) }
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    "1",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = getLexendFont()
                )

            }
        }

        Card(
            modifier = Modifier
                .size(keypadSize),
            onClick = { viewModel.addNumber(2) }
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "2",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = getLexendFont()
                )
            }
        }


        Card(
            modifier = Modifier
                .size(keypadSize),
            onClick = { viewModel.addNumber(3) }
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "3",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = getLexendFont()
                )
            }
        }

    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Card(
            modifier = Modifier
                .size(keypadSize),
            onClick = { viewModel.addNumber(4) }
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "4",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = getLexendFont()
                )

            }
        }

        Card(
            modifier = Modifier
                .size(keypadSize),
            onClick = { viewModel.addNumber(5) }
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "5",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = getLexendFont()
                )
            }
        }


        Card(
            modifier = Modifier
                .size(keypadSize),
            onClick = { viewModel.addNumber(6) }
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "6",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = getLexendFont()
                )
            }
        }

    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Card(
            modifier = Modifier
                .size(keypadSize),
            onClick = { viewModel.addNumber(7) }
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "7",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = getLexendFont()
                )

            }
        }

        Card(
            modifier = Modifier
                .size(keypadSize),
            onClick = { viewModel.addNumber(8) }
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "8",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = getLexendFont()
                )
            }
        }


        Card(
            modifier = Modifier
                .size(keypadSize),
            onClick = { viewModel.addNumber(9) }
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "9",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = getLexendFont()
                )
            }
        }
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Card(
            modifier = Modifier
                .size(keypadSize),
            onClick = { viewModel.addSymbol("*") }
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "*",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = getLexendFont()
                )

            }
        }

        Card(
            modifier = Modifier
                .size(keypadSize),
            onClick = { viewModel.addNumber(0) }
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "0",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = getLexendFont()
                )
            }
        }


        Card(
            modifier = Modifier
                .size(keypadSize),
            onClick = { viewModel.addSymbol("#") }
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "#",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = getLexendFont()
                )
            }
        }

    }


    //Call Button
    Card(
        modifier = Modifier
            .height(80.dp)
            .padding(horizontal = 8.dp),
        shape = RoundedCornerShape(32.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            IconButton(
                onClick = {
                    viewModel.addRecentCall(
                        RecentCall().apply {
                            phoneNumber = viewModel.number
                            callTime = getCurrentDate()
                        }
                    )

                    navigator.push(RecentTab)

                },
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.primary)
            ) {
                Icon(Icons.Default.Call, "Make Call", modifier = Modifier.size(40.dp))
            }

        }
    }


}

@RequiresApi(Build.VERSION_CODES.O)
fun getCurrentDate(): Date {
    val localDate = LocalDate.now()
    return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant())
}

@Composable
fun ClearButton(clearText: () -> Unit) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
        Card(
            onClick = {clearText()},
            modifier = Modifier
                .width(80.dp)
                .height(40.dp)
                .padding(end = 8.dp, top = 8.dp),
            shape = RoundedCornerShape(8.dp),
        ){
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.primary)
            ) {
                Text(
                    "Clear",
                    fontWeight = FontWeight.Medium,
                    fontFamily = getLexendFont()
                )
            }
        }
    }
}