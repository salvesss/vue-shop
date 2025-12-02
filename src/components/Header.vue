<script setup>
import { inject } from 'vue'
import { RouterLink, useRouter } from 'vue-router'

defineProps({
  totalPrice: Number,
})

const emit = defineEmits(['openDrawer'])

const auth = inject('auth')
const router = useRouter()

const onLogout = () => {
  auth.logout()
  router.push('/login')
}
</script>

<template>
  <header class="flex justify-between items-center border-b border-slate-100 px-8 py-6">
    <RouterLink to="/" class="flex items-center gap-4">
      <img src="/logo.png" alt="Logo" class="w-14 h-14 rounded-2xl shadow-md" />
      <div>
        <h2 class="text-2xl font-bold tracking-tight">bikeON</h2>
        <p class="text-slate-400 text-sm">Магазин лучших велосипедов</p>
      </div>
    </RouterLink>

    <div class="flex items-center gap-8">
      <button
        type="button"
        class="flex items-center gap-2 cursor-pointer text-gray-500 hover:text-black transition"
        @click="() => emit('openDrawer')"
      >
        <img src="/cart.svg" alt="Cart" />
        <b>{{ totalPrice }} р.</b>
      </button>

      <RouterLink
        to="/favorites"
        class="flex items-center gap-2 cursor-pointer text-gray-500 hover:text-black transition"
      >
        <img src="/heart.svg" alt="Heart" />
        <span>Закладки</span>
      </RouterLink>

      <RouterLink
        v-if="auth.isAuthenticated"
        to="/profile"
        class="flex items-center gap-2 cursor-pointer text-gray-500 hover:text-black transition"
      >
        <img src="/profile.svg" alt="Profile" />
        <span>{{ auth.userEmail || 'Профиль' }}</span>
      </RouterLink>

      <RouterLink
        v-else
        to="/login"
        class="px-4 py-2 rounded-xl border border-lime-400 text-lime-600 text-sm font-semibold hover:bg-lime-50 transition"
      >
        Войти
      </RouterLink>

      <button
        v-if="auth.isAuthenticated"
        type="button"
        class="text-xs text-slate-400 hover:text-red-500 transition"
        @click="onLogout"
      >
        Выйти
      </button>
    </div>
  </header>
</template>
