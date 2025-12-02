<script setup>
import { inject } from 'vue'
import { useRouter } from 'vue-router'

const auth = inject('auth')
const router = useRouter()

const onLogout = () => {
  auth.logout()
  router.push('/login')
}
</script>

<template>
  <div class="space-y-6">
    <h2 class="text-3xl font-bold">Профиль</h2>

    <div class="flex items-center gap-6 bg-gray-50 rounded-2xl p-6 border border-gray-100">
      <img src="/profile.svg" alt="Profile" class="w-16 h-16" />

      <div>
        <p class="text-lg font-semibold">{{ auth.userEmail || 'Пользователь' }}</p>
        <p class="text-gray-500">Вы авторизованы и можете сохранять закладки и заказы.</p>
      </div>
    </div>

    <div class="flex items-center justify-between bg-white rounded-2xl border border-gray-100 p-4">
      <div>
        <p class="font-semibold">Управление аккаунтом</p>
        <p class="text-sm text-gray-500">В следующих версиях здесь появятся настройки профиля.</p>
      </div>

      <button
        type="button"
        class="px-4 py-2 rounded-xl border border-red-400 text-red-500 text-sm font-semibold hover:bg-red-50 transition"
        @click="onLogout"
      >
        Выйти
      </button>
    </div>
  </div>
</template>
