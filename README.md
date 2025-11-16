# Tugas Sesi 5 — Aplikasi Profil Kelompok (Jetpack Compose)

---

## Ringkasan

Aplikasi Android sederhana menggunakan **Jetpack Compose (Material v2)** yang menampilkan biodata anggota kelompok, daftar skill, halaman kontak, dan halaman about. Fitur utama:

* Tampilan **Biodata** (daftar anggota) dengan card per anggota
* Halaman **Skills** menampilkan progress skill dengan `LinearProgressIndicator`
* Halaman **Contact** berisi email & nomor telepon dan tombol *Send Message* (menampilkan Snackbar)
* Halaman **About** menampilkan biodata singkat profil pengguna
* **Floating Action Button (FAB)**: kontekstual —

  * Di tab **Biodata**: membuka dialog untuk menambah anggota baru
  * Di tab lain: toggle tema Light / Dark + menampilkan Snackbar
* State member disimpan pada `mutableStateListOf` sehingga penambahan langsung muncul di UI

---

## Fitur (detail)

* **Biodata**

  * Setiap anggota ditampilkan pada `Card` dengan nama, NIM, dan latar singkat.
  * FAB (ikon `+`) membuka dialog `AlertDialog` berisi `OutlinedTextField` untuk nama, NIM, dan bio.
  * Validasi sederhana: Nama & NIM tidak boleh kosong.

* **Skills**

  * Daftar skill (Kotlin, Jetpack Compose, Android, APIs, UI/UX)
  * Setiap item berupa `Card` dengan icon, nama skill, persentase, dan progress bar.

* **Contact**

  * Menampilkan email dan nomor telepon.
  * Tombol `Send Message` memicu `Snackbar` konfirmasi.

* **About**

  * Menampilkan profil pengguna (nama, NIM, program studi, bio).

* **Theme**

  * Tema light berdasarkan palette hijau krem.
  * Tema dark versi sederhana (konfigurasi terbatas).

---

## Struktur Project (inti)

```
app/src/main/java/com/example/sesi5kelompok4/
  ├─ MainActivity.kt        # Semua composable + logika scaffold
  ├─ ui/                   # (jika ada) theme, color, typography
  └─ ...
app/build.gradle
settings.gradle
```

> Catatan: Pada pengerjaan ini kami menyimpan seluruh UI di `MainActivity.kt` untuk kemudahan tugas.

---

## Cara Build & Run (CLI)

1. Buka terminal di root project Android.
2. Sinkronisasi gradle (Android Studio otomatis) atau jalankan gradle wrapper:

```bash
./gradlew clean assembleDebug
```

3. Install ke emulator / device:

```bash
./gradlew installDebug
```

Atau gunakan Android Studio: **Run 'app'** setelah pilih device/emulator.

---

## File Penting / Kode Utama

* `MainActivity.kt` — berisi semua composable: `AppRoot`, `BiodataScreen`, `SkillsScreen`, `ContactScreen`, `AboutScreen`, `AddMemberDialog`.

Jika ingin menimpa MainActivity, ganti isi file tersebut dengan kode yang disediakan oleh tugas (pastikan package name cocok).

---

## Cara Menggunakan Aplikasi

1. Jalankan aplikasi pada emulator atau perangkat.
2. Default terbuka pada tab **Biodata**.
3. FAB di kanan bawah:

   * Jika berada di **Biodata**, tekan FAB (`+`) untuk membuka dialog tambah anggota. Isi Nama & NIM (wajib), lalu tekan *Tambah*.
   * Jika berada di tab **Skills**, **Contact**, atau **About**, tekan FAB (ikon matahari/bulan) untuk toggle Light/Dark.
4. Untuk menavigasi antar halaman gunakan bottom navigation (Biodata, Skills, Contact, About).

