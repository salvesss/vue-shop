<script setup>
import { onMounted, ref, reactive, provide, watch, computed } from 'vue'
import { RouterView } from 'vue-router'
import axios from 'axios'

import Header from './components/Header.vue'
import Drawer from './components/Drawer.vue'

const apiBaseUrl = import.meta.env.VITE_API_BASE_URL ?? 'http://localhost:8080'
const apiClient = axios.create({
  baseURL: apiBaseUrl,
})

const items = ref([])
const cart = ref([])

const drawerOpen = ref(false)

const totalPrice = computed(() => cart.value.reduce((acc, item) => acc + item.price, 0))

// --- Auth ---
const isAuthenticated = ref(false)
const userEmail = ref('')

const login = (email) => {
  isAuthenticated.value = true
  userEmail.value = email
  localStorage.setItem('auth', JSON.stringify({ email }))
}

const logout = () => {
  isAuthenticated.value = false
  userEmail.value = ''
  localStorage.removeItem('auth')
}

const closeDrawer = () => {
  drawerOpen.value = false
}
const openDrawer = () => {
  drawerOpen.value = true
}

const filters = reactive({
  sortBy: 'name',
  searchQuery: '',
})

const addToCart = (item) => {
  cart.value.push(item)
  item.isAdded = true
}

const removeFromCart = (item) => {
  cart.value.splice(cart.value.indexOf(item), 1)
  item.isAdded = false
}

const onClickAddPlus = (item) => {
  if (!item.isAdded) {
    addToCart(item)
  } else {
    removeFromCart(item)
  }
}

const onChangeSelect = (event) => {
  filters.sortBy = event.target.value
}

const onChangeSearchInput = (event) => {
  filters.searchQuery = event.target.value
}

const fetchFavorites = async () => {
  try {
    const { data: favorites } = await apiClient.get(`/favorites`)
    items.value = items.value.map((item) => {
      const favorite = favorites.find((favorite) => favorite.parentId === item.id)
      if (!favorite) {
        return item
      }
      return {
        ...item,
        isFavorite: true,
        favoriteId: favorite.id,
      }
    })
  } catch (err) {
    console.log(err)
  }
}

const addToFavorite = async (item) => {
  try {
    if (!item.isFavorite) {
      const obj = {
        parentId: item.id,
      }
      item.isFavorite = true
      const { data } = await apiClient.post(`/favorites`, obj)

      item.favoriteId = data.id
    } else {
      item.isFavorite = false
      await apiClient.delete(`/favorites/${item.favoriteId}`)
      item.favoriteId = null
    }
  } catch (err) {
    console.log(err)
  }
}

const fetchItems = async () => {
  try {
    const params = {
      sortBy: filters.sortBy,
    }

    if (filters.searchQuery) {
      params.title = `*${filters.searchQuery}*`
    }

    const { data } = await apiClient.get(`/items`, { params })
    items.value = data.map((obj) => ({
      ...obj,
      isFavorite: false,
      favoriteId: null,
      isAdded: false,
    }))
  } catch (err) {
    console.log(err)
  }
}

onMounted(async () => {
  const savedAuth = localStorage.getItem('auth')
  if (savedAuth) {
    try {
      const parsed = JSON.parse(savedAuth)
      if (parsed?.email) {
        isAuthenticated.value = true
        userEmail.value = parsed.email
      }
    } catch (e) {
      localStorage.removeItem('auth')
    }
  }

  await fetchItems()
  await fetchFavorites()
})
watch(filters, fetchItems)
provide('cart', {
  cart,
  closeDrawer,
  openDrawer,
  addToCart,
  removeFromCart,
})
provide('auth', {
  isAuthenticated,
  userEmail,
  login,
  logout,
})
</script>
<template>
  <Drawer v-if="drawerOpen" :total-price="totalPrice" />
  <div
    class="bg-white/90 backdrop-blur-sm w-11/12 max-w-5xl m-auto rounded-3xl shadow-2xl mt-10 border border-lime-100"
  >
    <Header :total-price="totalPrice" @open-drawer="openDrawer" />

    <div class="p-8 md:p-10">
      <RouterView v-slot="{ Component }">
        <component
          :is="Component"
          :items="items"
          :filters="filters"
          :on-change-select="onChangeSelect"
          :on-change-search-input="onChangeSearchInput"
          :add-to-favorite="addToFavorite"
          :on-click-add-plus="onClickAddPlus"
        />
      </RouterView>
    </div>
  </div>
</template>

<style scoped></style>
