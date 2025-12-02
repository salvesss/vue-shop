<script setup>
import { inject, ref } from 'vue'
import { useRoute, useRouter } from 'vue-router'

const auth = inject('auth')
const router = useRouter()
const route = useRoute()

const email = ref('')
const password = ref('')
const error = ref('')

const onSubmit = () => {
  error.value = ''

  if (!email.value || !password.value) {
    error.value = 'Введите e-mail и пароль'
    return
  }

  // Здесь можно подключить реальный запрос на бэкенд.
  // Сейчас авторизация демо-режима: любой логин/пароль.
  auth.login(email.value)

  const redirect = route.query.redirect || '/'
  router.push(redirect)
}
</script>

<template>
  <div class="max-w-md mx-auto mt-10 bg-white rounded-3xl shadow-xl p-8">
    <h2 class="text-3xl font-bold mb-6 text-center">Вход в аккаунт</h2>

    <p class="text-gray-500 mb-6 text-center">
      Введите e-mail и пароль, чтобы войти и получить доступ к закладкам и профилю.
    </p>

    <form class="flex flex-col gap-4" @submit.prevent="onSubmit">
      <div class="flex flex-col gap-1">
        <label class="text-sm text-gray-600">E-mail</label>
        <input
          v-model="email"
          type="email"
          class="border border-gray-200 rounded-xl px-4 py-2 outline-none focus:border-lime-500 transition"
          placeholder="you@example.com"
        />
      </div>

      <div class="flex flex-col gap-1">
        <label class="text-sm text-gray-600">Пароль</label>
        <input
          v-model="password"
          type="password"
          class="border border-gray-200 rounded-xl px-4 py-2 outline-none focus:border-lime-500 transition"
          placeholder="••••••••"
        />
      </div>

      <p v-if="error" class="text-sm text-red-500">{{ error }}</p>

      <button
        type="submit"
        class="mt-2 bg-lime-500 hover:bg-lime-600 active:bg-lime-700 text-white rounded-xl py-2.5 font-semibold transition"
      >
        Войти
      </button>
    </form>
  </div>
</template>


