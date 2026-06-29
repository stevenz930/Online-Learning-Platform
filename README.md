# Online Learning Platform

An Android application built with Java that serves as an online learning platform with local data persistence.

## Features

- **Home** — View and manage users with add/delete operations
- **Courses** — Browse and manage courses with add/delete operations
- **Profile** — Placeholder profile fragment
- **Local Database** — Uses Room (SQLite) for offline data storage
- **MVVM Architecture** — ViewModel + LiveData pattern for reactive UI updates

## Tech Stack

| Component       | Technology                |
|-----------------|---------------------------|
| Language        | Java                      |
| UI              | XML Layouts, RecyclerView |
| Architecture    | Fragment + ViewModel      |
| Database        | Room (SQLite)             |
| Build System    | Gradle (Kotlin DSL)       |
| Min SDK         | 35 (Android 15)           |
| Target SDK      | 35                        |

## Project Structure

```
app/
├── src/
│   ├── main/
│   │   ├── java/com/example/olp/
│   │   │   ├── MainActivity.java          # Main entry with bottom navigation
│   │   │   ├── CourseAdapter.java         # RecyclerView adapter for courses
│   │   │   ├── UserAdapter.java           # RecyclerView adapter for users
│   │   │   ├── fragment/
│   │   │   │   ├── HomeFragment.java      # User list with add/delete
│   │   │   │   ├── CourseListFragment.java # Course list with add/delete
│   │   │   │   └── ProfileFragment.java    # Profile placeholder
│   │   │   └── room/
│   │   │       ├── model/
│   │   │       │   ├── User.java           # User entity
│   │   │       │   └── Course.java         # Course entity
│   │   │       ├── dao/
│   │   │       │   ├── UserDao.java        # User data access
│   │   │       │   └── CourseDao.java      # Course data access
│   │   │       ├── database/
│   │   │       │   └── AppDatabase.java    # Room database singleton
│   │   │       └── viewmodel/
│   │   │           ├── UserViewModel.java  # ViewModel for users
│   │   │           └── CourseViewModel.java # ViewModel for courses
│   │   ├── res/
│   │   │   ├── layout/                    # XML layouts
│   │   │   ├── values/                    # Themes, strings, etc.
│   │   │   └── ...
│   │   └── AndroidManifest.xml
│   ├── test/                              # Unit tests
│   └── androidTest/                       # Instrumented tests
```

## How to Build & Run

1. Open the project in **Android Studio**.
2. Sync Gradle and let dependencies resolve.
3. Select a device/emulator running **Android 15 (API 35)**.
4. Click **Run** (`^R`).

## Database

Room database (`app_database`) contains two tables:

- **`user_table`** — Stores user profiles (username, password, name, email, social links, avatar, etc.)
- **`course_table`** — Stores course info (title, description, level, price, timestamps)
