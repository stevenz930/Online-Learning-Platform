import { createRouter, createWebHistory } from 'vue-router';
import LoginView from '../views/Login.vue';
import MainView from '../views/Main.vue';
import UserView from '../views/User.vue';
import CourseView from '../views/Course.vue';
import ReviewView from '../views/Review.vue';
import AddUser from '../views/AddUser.vue';
import AddCourse from '../views/AddCourse.vue';
import AddLesson from '../views/AddLesson.vue';
import EditUser from '../views/EditUser.vue';
import EditReview from '../views/EditReview.vue';
import EditCourse from '../views/EditCourse.vue';

import { useAuthStore } from "../stores/useAuthStore";

const routes = [
  { 
    path: '/', 
    component: LoginView 
  },
  { 
    path: '/main', 
    component: MainView
  },
  {
    path: '/edit-user/:userId',
    component: EditUser
  },
  {
    path: '/add-user',
    component: AddUser
  },
  {
    path: '/edit-review/:reviewId',
    component: EditReview
  },
  {
    path: '/edit-course/:courseId',
    component: EditCourse
  },
  {
    path: '/add-course',
    component: AddCourse
  },
  {
    path: '/add-lesson/:courseId/:lessonOrder',
    component: AddLesson
  }
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

router.beforeEach((to, from, next) => {
  const authStore = useAuthStore();
  if (!authStore.isLoggedIn && to.path !== '/') {
    next('/');
  } else {
    next();
  }
});

export default router;
