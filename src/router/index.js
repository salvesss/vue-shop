import { createRouter, createWebHistory } from 'vue-router'

import Home from '../pages/Home.vue'
import Favorites from '../pages/Favorites.vue'
import Profile from '../pages/Profile.vue'
import Login from '../pages/Login.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: Home,
    },
    {
      path: '/favorites',
      name: 'favorites',
      component: Favorites,
      meta: { requiresAuth: true },
    },
    {
      path: '/profile',
      name: 'profile',
      component: Profile,
      meta: { requiresAuth: true },
    },
    {
      path: '/login',
      name: 'login',
      component: Login,
    },
  ],
})

router.beforeEach((to, from, next) => {
  const isAuthenticated = !!localStorage.getItem('auth')

  if (to.meta.requiresAuth && !isAuthenticated) {
    next({ name: 'login', query: { redirect: to.fullPath } })
    return
  }

  if (to.name === 'login' && isAuthenticated) {
    next({ name: 'home' })
    return
  }

  next()
})

export default router