package com.example.sesi5kelompok4

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProfileApp()
        }
    }
}

@Composable
fun ProfileApp() {
    var selectedScreen by remember { mutableStateOf(0) } // 0 = About, 1 = Skills, 2 = Contact
    var isDark by rememberSaveable { mutableStateOf(false) }

    val colors = if (isDark) darkColors() else lightColors()

    MaterialTheme(colors = colors) {
        val scaffoldState = rememberScaffoldState()

        Scaffold(
            scaffoldState = scaffoldState,
            topBar = {
                TopAppBar(title = { Text("My Profile") })
            },
            bottomBar = {
                BottomNavigation {
                    BottomNavigationItem(
                        icon = { Icon(Icons.Default.Person, contentDescription = "About") },
                        label = { Text("About") },
                        selected = selectedScreen == 0,
                        onClick = { selectedScreen = 0 }
                    )
                    BottomNavigationItem(
                        icon = { Icon(Icons.Default.List, contentDescription = "Skills") },
                        label = { Text("Skills") },
                        selected = selectedScreen == 1,
                        onClick = { selectedScreen = 1 }
                    )
                    BottomNavigationItem(
                        icon = { Icon(Icons.Default.Call, contentDescription = "Contact") },
                        label = { Text("Contact") },
                        selected = selectedScreen == 2,
                        onClick = { selectedScreen = 2 }
                    )
                }
            },
            floatingActionButton = {
                if (selectedScreen == 0) {
                    FloatingActionButton(onClick = { isDark = !isDark }) {
                        Icon(Icons.Default.Brightness4, contentDescription = "Toggle theme")
                    }
                }
            },
            content = { padding ->
                Box(modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp)
                ) {
                    when (selectedScreen) {
                        0 -> AboutScreen()
                        1 -> SkillsScreen()
                        2 -> ContactScreen(scaffoldState)
                    }
                }
            }
        )
    }
}

@Composable
fun AboutScreen() {
    val defaultName = "Mr Rijal"
    val defaultNim = "123456789"
    val defaultProgram = "Sistem Tekno"
    var name by rememberSaveable { mutableStateOf(defaultName) }
    var nim by rememberSaveable { mutableStateOf(defaultNim) }
    var program by rememberSaveable { mutableStateOf(defaultProgram) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(24.dp))
        Icon(Icons.Default.Person, contentDescription = "Profile", modifier = Modifier.size(120.dp))
        Spacer(Modifier.height(12.dp))
        Text(text = name, style = MaterialTheme.typography.h6)
        Text("NIM: $nim")
        Text("Program: $program")
        Spacer(Modifier.height(20.dp))

        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(12.dp)) {
                Text("Biodata Singkat", style = MaterialTheme.typography.subtitle1)
                Spacer(Modifier.height(8.dp))
                Text("Mahasiswa aktif yang sedang belajar Jetpack Compose.")
            }
        }

        Spacer(Modifier.height(20.dp))

        // Simple editable fields inline (very basic)
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Nama") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(
            value = nim,
            onValueChange = { nim = it },
            label = { Text("NIM") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(
            value = program,
            onValueChange = { program = it },
            label = { Text("Program Studi") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(24.dp))
    }
}

@Composable
fun SkillsScreen() {
    val skills = listOf(
        "Kotlin" to 0.9f,
        "Jetpack Compose" to 0.8f,
        "Android" to 0.85f,
        "Java" to 0.7f,
        "XML" to 0.6f
    )

    LazyColumn {
        items(skills) { (skill, progress) ->
            Card(modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 6.dp)) {
                Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.CheckCircle, contentDescription = null)
                    Spacer(Modifier.width(12.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(skill, style = MaterialTheme.typography.subtitle1)
                        Spacer(Modifier.height(6.dp))
                        LinearProgressIndicator(progress = progress, modifier = Modifier.fillMaxWidth().height(6.dp))
                    }
                    Spacer(Modifier.width(12.dp))
                    Text("${(progress * 100).toInt()}%")
                }
            }
        }
    }
}

@Composable
fun ContactScreen(scaffoldState: ScaffoldState) {
    val scope = rememberCoroutineScope()
    Column(modifier = Modifier.fillMaxSize().padding(12.dp)) {
        Text("Email: rijal@gmail.com")
        Text("Phone: +62 812-4819-6647")
        Spacer(Modifier.height(16.dp))
        Row {
            Button(onClick = {
                scope.launch { scaffoldState.snackbarHostState.showSnackbar("Message sent!") }
            }) {
                Text("Send Message")
            }
            Spacer(Modifier.width(12.dp))
            OutlinedButton(onClick = { scope.launch { scaffoldState.snackbarHostState.showSnackbar("Opening social...") }}) {
                Text("Social")
            }
        }
    }
}