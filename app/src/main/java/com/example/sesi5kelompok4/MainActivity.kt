package com.example.sesi5kelompok4

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import androidx.compose.runtime.rememberCoroutineScope

// ---------------- Color palette ----------------
private val GreenCream = Color(0xFFF1F3E0)   // background
private val GreenSoft  = Color(0xFFD2DCB6)   // surface / card
private val GreenMed   = Color(0xFFA1BC98)   // primary
private val GreenDark  = Color(0xFF778873)   // accent / icons

// ---------------- UI dims ----------------
private val CardCorner = 14.dp
private val CardElevation = 6.dp
private val ScreenPadding = 16.dp
private val SectionTop = 20.dp

// ---------------- Data models ----------------
data class Member(val name: String, val nim: String, val bio: String)
data class Profile(val name: String, val nim: String, val program: String, val bio: String)
data class Skill(val name: String, val progress: Float, val icon: @Composable () -> Unit)

// ---------------- Sample data ----------------
private val initialMembers = listOf(
    Member("Said Muhammad Ma'ruf", "2023230044", "."),
    Member("M. Zacqy Algiffari T. Nassa", "2023230015", "."),
    Member("Ahmad Al Kautsar Rustam", "2023230012", "."),
    Member("Indah Hairunnisa Duha", "2023230022", ".")
)

private val sampleProfile = Profile(
    name = "Said Muhammad Ma'ruf",
    nim = "2023230044",
    program = "Sistem Teknologi Informasi",
    bio = ""
)

private val sampleSkills = listOf(
    Skill("Kotlin", 0.92f, { Icon(Icons.Default.Code, contentDescription = "Kotlin") }),
    Skill("Jetpack Compose", 0.88f, { Icon(Icons.Default.Brush, contentDescription = "Compose") }),
    Skill("Android", 0.85f, { Icon(Icons.Default.PhoneAndroid, contentDescription = "Android") }),
    Skill("APIs", 0.76f, { Icon(Icons.Default.Cloud, contentDescription = "APIs") }),
    Skill("UI/UX", 0.65f, { Icon(Icons.Default.Face, contentDescription = "UI/UX") })
)

// ---------------- Activity ----------------
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppRoot()
        }
    }
}

// ---------------- AppRoot ----------------
@Composable
fun AppRoot() {
    var darkMode by rememberSaveable { mutableStateOf(false) }

    // stateful list so UI updates when adding
    val members = remember { mutableStateListOf<Member>().apply { addAll(initialMembers) } }

    // material color selection (Material2)
    val colors = if (!darkMode) {
        lightColors(
            primary = GreenMed,
            primaryVariant = GreenDark,
            secondary = GreenDark,
            background = GreenCream,
            surface = GreenSoft,
            onPrimary = Color.White,
            onBackground = Color(0xFF222222),
            onSurface = Color(0xFF222222)
        )
    } else {
        darkColors(
            primary = Color(0xFF708090),
            primaryVariant = Color(0xFF708090),
            secondary = Color(0xFF708090),
            background = Color(0xFFA9A9A9),
            surface = Color(0xFFA9A9A9),
            onPrimary = Color.White,
            onBackground = Color.White,
            onSurface = Color.White
        )
    }

    MaterialTheme(colors = colors) {
        // NEW ORDER: 0 = Biodata, 1 = Skills, 2 = Contact, 3 = About
        var currentTab by rememberSaveable { mutableStateOf(0) } // default to Biodata
        val scaffoldState = rememberScaffoldState()
        val scope = rememberCoroutineScope()

        // dialog for adding member (optional; not shown by default)
        var showAddDialog by remember { mutableStateOf(false) }

        Scaffold(
            scaffoldState = scaffoldState,
            topBar = {
                TopAppBar(
                    title = {
                        val title = when (currentTab) {
                            1 -> "Skills"
                            2 -> "Contact"
                            3 -> "About"
                            else -> "Biodata Kelompok"
                        }
                        Text(title, fontSize = 20.sp)
                    },
                    backgroundColor = MaterialTheme.colors.primary,
                    contentColor = MaterialTheme.colors.onPrimary,
                    elevation = 0.dp
                )
            },
            bottomBar = {
                BottomNavigation(backgroundColor = MaterialTheme.colors.primary) {
                    // Biodata first
                    BottomNavigationItem(
                        icon = { Icon(Icons.Default.Group, contentDescription = "Biodata") },
                        label = { Text("Biodata") },
                        selected = currentTab == 0,
                        onClick = { currentTab = 0 },
                        selectedContentColor = MaterialTheme.colors.onPrimary,
                        unselectedContentColor = MaterialTheme.colors.onSurface.copy(alpha = 0.6f)
                    )

                    // Skills second
                    BottomNavigationItem(
                        icon = { Icon(Icons.Default.List, contentDescription = "Skills") },
                        label = { Text("Skills") },
                        selected = currentTab == 1,
                        onClick = { currentTab = 1 },
                        selectedContentColor = MaterialTheme.colors.onPrimary,
                        unselectedContentColor = MaterialTheme.colors.onSurface.copy(alpha = 0.6f)
                    )

                    // Contact third
                    BottomNavigationItem(
                        icon = { Icon(Icons.Default.Call, contentDescription = "Contact") },
                        label = { Text("Contact") },
                        selected = currentTab == 2,
                        onClick = { currentTab = 2 },
                        selectedContentColor = MaterialTheme.colors.onPrimary,
                        unselectedContentColor = MaterialTheme.colors.onSurface.copy(alpha = 0.6f)
                    )

                    // About last (after Contact)
                    BottomNavigationItem(
                        icon = { Icon(Icons.Default.Person, contentDescription = "About") },
                        label = { Text("About") },
                        selected = currentTab == 3,
                        onClick = { currentTab = 3 },
                        selectedContentColor = MaterialTheme.colors.onPrimary,
                        unselectedContentColor = MaterialTheme.colors.onSurface.copy(alpha = 0.6f)
                    )
                }
            },
            // FAB: now visible on ALL tabs
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                        if (currentTab == 0) {
                            // Biodata tab: open dialog to add new member
                            showAddDialog = true
                        } else {
                            // Other tabs: toggle dark mode + snackbar
                            darkMode = !darkMode
                            val msg = if (darkMode) "Dark mode on" else "Light mode on"
                            scope.launch { scaffoldState.snackbarHostState.showSnackbar(msg) }
                        }
                    },
                    backgroundColor = MaterialTheme.colors.surface,
                    contentColor = MaterialTheme.colors.primary
                ) {
                    // Icon changes by tab
                    if (currentTab == 0) Icon(Icons.Default.Add, contentDescription = "Tambah member")
                    else Icon(Icons.Default.Brightness4, contentDescription = "Toggle theme")
                }
            },
            backgroundColor = MaterialTheme.colors.background
        ) { padding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .background(MaterialTheme.colors.background)
            ) {
                when (currentTab) {
                    0 -> BiodataScreen(list = members)     // Biodata first
                    1 -> SkillsScreen(skills = sampleSkills)
                    2 -> ContactScreen(scaffoldState = scaffoldState)
                    3 -> AboutScreen(profile = sampleProfile)
                }

                // Optional: AddMemberDialog (triggered by FAB in Biodata)
                if (showAddDialog) {
                    AddMemberDialog(
                        onAdd = { name, nim, bio ->
                            members.add(0, Member(name.trim(), nim.trim(), bio.trim()))
                            showAddDialog = false
                            scope.launch { scaffoldState.snackbarHostState.showSnackbar("Member ditambahkan") }
                        },
                        onDismiss = { showAddDialog = false }
                    )
                }
            }
        }
    }
}

// ---------------- Add Member Dialog (optional) ----------------
@Composable
fun AddMemberDialog(onAdd: (name: String, nim: String, bio: String) -> Unit, onDismiss: () -> Unit) {
    var name by rememberSaveable { mutableStateOf("") }
    var nim by rememberSaveable { mutableStateOf("") }
    var bio by rememberSaveable { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Tambah Member") },
        text = {
            Column {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Nama") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = nim,
                    onValueChange = { nim = it },
                    label = { Text("NIM") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = bio,
                    onValueChange = { bio = it },
                    label = { Text("Latar singkat") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    if (name.isBlank() || nim.isBlank()) return@TextButton
                    onAdd(name, nim, bio)
                }
            ) { Text("Tambah") }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Batal") }
        }
    )
}

// ---------------- About Screen ----------------
@Composable
fun AboutScreen(profile: Profile) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(ScreenPadding),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(6.dp))
        // avatar placeholder
        Box(
            modifier = Modifier
                .size(110.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colors.surface),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = rememberVectorPainter(Icons.Default.Person),
                contentDescription = "Avatar",
                modifier = Modifier.size(56.dp)
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(profile.name, fontWeight = FontWeight.Bold, fontSize = 20.sp)
        Spacer(modifier = Modifier.height(6.dp))
        Text("NIM: ${profile.nim}", style = MaterialTheme.typography.body2)
        Text("Program: ${profile.program}", style = MaterialTheme.typography.body2)

        Spacer(modifier = Modifier.height(16.dp))

        Card(
            backgroundColor = MaterialTheme.colors.surface,
            elevation = CardElevation,
            shape = RoundedCornerShape(CardCorner),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                Text("Biodata Singkat", fontWeight = FontWeight.SemiBold)
                Spacer(modifier = Modifier.height(8.dp))
                Text(profile.bio)
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Column(modifier = Modifier.fillMaxWidth()) {
            Text("Info tambahan", fontWeight = FontWeight.SemiBold)
            Spacer(modifier = Modifier.height(6.dp))
            Text("- Hobi: Coding, membaca")
            Spacer(modifier = Modifier.height(6.dp))
            Text("- Lokasi: Indonesia")
        }
    }
}

// ---------------- Skills Screen ----------------
@Composable
fun SkillsScreen(skills: List<Skill>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(ScreenPadding),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(skills) { s ->
            Card(
                backgroundColor = MaterialTheme.colors.surface,
                elevation = CardElevation,
                shape = RoundedCornerShape(CardCorner),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colors.primary.copy(alpha = 0.12f)),
                        contentAlignment = Alignment.Center
                    ) {
                        s.icon()
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(s.name, fontWeight = FontWeight.SemiBold)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("${(s.progress * 100).toInt()}%", style = MaterialTheme.typography.caption)
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        LinearProgressIndicator(progress = s.progress, modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp))
                    }
                }
            }
        }
    }
}

// ---------------- Contact Screen ----------------
@Composable
fun ContactScreen(scaffoldState: ScaffoldState) {
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(ScreenPadding)
    ) {
        Text("Contact", fontWeight = FontWeight.SemiBold, fontSize = 18.sp)
        Spacer(modifier = Modifier.height(12.dp))

        Card(
            backgroundColor = MaterialTheme.colors.surface,
            elevation = CardElevation,
            shape = RoundedCornerShape(CardCorner),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                Text("Email", fontWeight = FontWeight.SemiBold)
                Spacer(modifier = Modifier.height(6.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Email, contentDescription = "email")
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("rijal@example.com")
                }

                Spacer(modifier = Modifier.height(10.dp))

                Text("Phone", fontWeight = FontWeight.SemiBold)
                Spacer(modifier = Modifier.height(6.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Call, contentDescription = "phone")
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("+62 812-0000-0001")
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(onClick = {
            scope.launch { scaffoldState.snackbarHostState.showSnackbar("Message sent") }
        }) {
            Text("Send Message")
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            IconButton(onClick = { /* open */ }) { Icon(Icons.Default.Public, contentDescription = "web") }
            IconButton(onClick = { /* open */ }) { Icon(Icons.Default.Link, contentDescription = "link") }
            IconButton(onClick = { /* open */ }) { Icon(Icons.Default.Share, contentDescription = "share") }
        }
    }
}

// ---------------- Biodata Screen ----------------
@Composable
fun BiodataScreen(list: List<Member>) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = ScreenPadding)
    ) {
        Spacer(modifier = Modifier.height(SectionTop))
        Text("Biodata Kelompok", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(12.dp))

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(14.dp),
            contentPadding = PaddingValues(bottom = 24.dp)
        ) {
            items(list) { m ->
                MemberCard(m)
            }
        }
    }
}

@Composable
fun MemberCard(m: Member) {
    Card(
        backgroundColor = MaterialTheme.colors.surface,
        elevation = CardElevation,
        shape = RoundedCornerShape(CardCorner),
        modifier = Modifier
            .fillMaxWidth()
            .shadow(CardElevation, RoundedCornerShape(CardCorner))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(m.name, fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text("NIM: ${m.nim}", style = MaterialTheme.typography.body2, color = MaterialTheme.colors.onSurface.copy(alpha = 0.9f))
            Spacer(modifier = Modifier.height(8.dp))
            Text("Latar singkat: ${m.bio}", style = MaterialTheme.typography.body2, color = MaterialTheme.colors.onSurface.copy(alpha = 0.9f))
        }
    }
}
