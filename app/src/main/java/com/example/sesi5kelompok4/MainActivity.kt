package com.example.sesi5kelompok4

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

// ----------------- Model -----------------
data class Person(
    val id: Int,
    val name: String,
    val nim: String,
    val program: String,
    val email: String,
    val phone: String,
    val bio: String
)

// ----------------- Sample Data (5 person) -----------------
val samplePeople = listOf(
    Person(
        id = 0,
        name = "Rijal Saputra",
        nim = "12014001",
        program = "Teknik Informatika",
        email = "rijal@example.com",
        phone = "+62 812-0000-0001",
        bio = "Mahasiswa aktif yang tertarik di mobile development dan game."
    ),
    Person(
        id = 1,
        name = "Siti Aisyah",
        nim = "12014002",
        program = "Sistem Informasi",
        email = "siti@example.com",
        phone = "+62 812-0000-0002",
        bio = "Gemar UI/UX dan prototyping aplikasi."
    ),
    Person(
        id = 2,
        name = "Andi Pratama",
        nim = "12014003",
        program = "Teknik Komputer",
        email = "andi@example.com",
        phone = "+62 812-0000-0003",
        bio = "Spesialis embedded & IoT, suka utak-atik hardware."
    ),
    Person(
        id = 3,
        name = "Dewi Lestari",
        nim = "12014004",
        program = "Teknik Informatika",
        email = "dewi@example.com",
        phone = "+62 812-0000-0004",
        bio = "Tertarik pada machine learning dan data analysis."
    ),
    Person(
        id = 4,
        name = "Budi Santoso",
        nim = "12014005",
        program = "Sistem Informasi",
        email = "budi@example.com",
        phone = "+62 812-0000-0005",
        bio = "Backend developer, menyukai optimasi performa."
    )
)

// ----------------- Activity -----------------
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Jika proyekmu pakai Theme custom, bungkus dengan Theme tersebut:
            // Sesi5Kelompok4Theme { PersonsScreen(samplePeople) }
            MaterialTheme {
                PersonsScreen(people = samplePeople)
            }
        }
    }
}

// ----------------- UI -----------------
@Composable
fun PersonsScreen(people: List<Person>) {
    var selectedIndex by rememberSaveable { mutableStateOf(0) }
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Daftar Person (5)") })
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Tab row (quick switch)
            ScrollableTabRow(
                selectedTabIndex = selectedIndex,
                edgePadding = 8.dp,
                backgroundColor = MaterialTheme.colors.surface
            ) {
                people.forEachIndexed { idx, person ->
                    Tab(
                        selected = selectedIndex == idx,
                        onClick = {
                            selectedIndex = idx
                            scope.launch { listState.animateScrollToItem(idx) }
                        },
                        text = { Text(person.name, maxLines = 1) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Selected person brief card
            SelectedPersonBrief(person = people[selectedIndex])

            Spacer(modifier = Modifier.height(8.dp))

            // List of cards
            LazyColumn(
                state = listState,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 12.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                contentPadding = PaddingValues(bottom = 16.dp)
            ) {
                itemsIndexed(items = people) { index, person ->
                    PersonCard(
                        person = person,
                        isSelected = index == selectedIndex,
                        onClick = {
                            selectedIndex = index
                            scope.launch { listState.animateScrollToItem(index) }
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun SelectedPersonBrief(person: Person) {
    Surface(elevation = 4.dp, shape = MaterialTheme.shapes.medium) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Avatar placeholder
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colors.primary.copy(alpha = 0.12f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Avatar",
                    tint = MaterialTheme.colors.primary,
                    modifier = Modifier.size(36.dp)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(person.name, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(4.dp))
                Text("NIM: ${person.nim}  •  ${person.program}", style = MaterialTheme.typography.body2)
            }
        }
    }
}

@Composable
fun PersonCard(person: Person, isSelected: Boolean, onClick: () -> Unit) {
    val borderColor = if (isSelected) MaterialTheme.colors.primary else Color.Transparent
    Card(
        elevation = if (isSelected) 8.dp else 3.dp,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .then(
                if (isSelected) Modifier.border(2.dp, borderColor, MaterialTheme.shapes.medium)
                else Modifier
            )
    ) {
        Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            // Avatar
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colors.primary.copy(alpha = 0.12f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "avatar",
                    tint = MaterialTheme.colors.primary,
                    modifier = Modifier.size(36.dp)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(person.name, fontWeight = FontWeight.SemiBold)
                    Spacer(modifier = Modifier.width(8.dp))
                    if (isSelected) {
                        Surface(
                            color = MaterialTheme.colors.primary.copy(alpha = 0.12f),
                            shape = MaterialTheme.shapes.small,
                            elevation = 0.dp
                        ) {
                            Text(
                                "Selected",
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                style = MaterialTheme.typography.caption,
                                color = MaterialTheme.colors.primary
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(6.dp))

                Text("NIM: ${person.nim}", style = MaterialTheme.typography.body2)
                Text("Program: ${person.program}", style = MaterialTheme.typography.body2)

                Spacer(modifier = Modifier.height(8.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Email, contentDescription = "email", modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(person.email, style = MaterialTheme.typography.caption)
                }

                Spacer(modifier = Modifier.height(4.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Phone, contentDescription = "phone", modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(person.phone, style = MaterialTheme.typography.caption)
                }
            }
        }
    }
}
