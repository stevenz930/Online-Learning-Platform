# Online Learning Platform

A full-stack online learning platform consisting of an **Android mobile app**, a **PHP REST API backend**, and a **Vue 3 web frontend**.

## Project Structure

```
Online-Learning-Platform/
├── android-app/          # Android mobile app (Java)
├── php-api/              # PHP REST API backend
├── vue-web/              # Vue 3 web frontend
└── README.md
```

---

## android-app — Android Mobile App

**Java · Retrofit/OkHttp · Glide**

A feature-rich Android client for browsing courses, watching lessons, taking tests, managing enrollment, and user authentication.

| Category | Key Files |
|----------|-----------|
| **Activities** | `MainActivity.java`, `LoginRegisterActivity.java`, `CourseDetailActivity.java`, `EnrollActivity.java`, `LessonDetailActivity.java` |
| **Fragments** | `HomeFragment`, `CoursesFragment`, `SearchFragment`, `AccountFragment`, `LoginFragment`, `RegisterFragment`, `CourseDetailLessonFragment`, `CourseDetailReviewFragment`, `LessonDetailContentFragment`, `LessonDetailTestFragment` |
| **Adapters** | 8 RecyclerView adapters for home, courses, cart, search, enrollment, reviews, lessons |
| **Networking** | Retrofit `ApiService` with models: `Course`, `User`, `Subject`, `Review`, `Lesson`, `Cart`, `Enrollment` |

**Build & Run:**
```
Open android-app/ in Android Studio → Sync Gradle → Run (API 35)
```

---

## php-api — PHP Backend

**Native PHP · MySQL (mysqli) · Apache · XAMPP**

Runs on **XAMPP** — place `php-api/htdocs/` as the XAMPP web root (or symlink to `htdocs/`).  
Database: `project` (MySQL), config in `api/config.php`.

31+ REST API endpoints:

| Category | Endpoints |
|----------|-----------|
| **Auth** | `login`, `register`, `logout`, `login-user` |
| **Courses** | `get-all-course`, `get-course`, `get-some-course`, `add-course`, `get-course-for-edit-page`, `search` |
| **Users** | `get-some-user`, `get-all-instructor`, `post-profile`, `post-be-instructor` |
| **Reviews** | `get-all-review-by-course`, `post-review`, `delete-review`, `update-review-data` |
| **Subjects** | `get-all-subject`, `get-category-subject` |
| **Rankings** | `get-top10-popular-course`, `get-top10-rating-course` (by subject) |
| **Lessons** | `get-all-lesson-by-course` |
| **Tests** | `get-test`, `post-test` |
| **Cart** | `delete-cart-item` |

---

## vue-web — Vue 3 Web Frontend

**Vue 3 · Vite · Pinia · Vue Router · Bootstrap · Axios · Sass**

Admin management interface with CRUD operations.

```
src/
├── views/        — 11 pages: Login, Main, Course, User, Review,
│                   AddCourse, AddLesson, AddUser, EditCourse,
│                   EditReview, EditUser
├── components/   — Nav.vue, Modal.vue
├── stores/       — useAuthStore.js, currentPageInMain.js
├── router/       — index.js
└── assets/
```

**Run:**
```
cd vue-web/vite-project
npm install
npm run dev
```
