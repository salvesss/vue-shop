<script setup>
import { onMounted, ref } from 'vue'
import axios from 'axios'

import Header from './components/Header.vue'
import CardList from './components/CardList.vue'
import Drawer from './components/Drawer.vue'

const items = ref([])

onMounted(async () => {
  try {
    const { data } = await axios.get('https://caa4ea22ee7fba88.mokky.dev/items')
    items.value = data
  } catch (err) {
    console.log(err)
  }
})
</script>
<template>
  <!--   <Drawer /> -->
  <div className="bg-white w-4/5 m-auto rounded-xl shadow-xl mt-14">
    <Header />

    <div className="p-10">
      <div className="flex justify-between items-center">
        <h2 className="text-3xl font-bold mb-8">Все велосипеды</h2>

        <div className="flex gap-4">
          <select className="py-2 px-3 border border-gray-200 outline-none rounded-md">
            <option>По названию</option>
            <option>Сначала дешевые</option>
            <option>Сначала дорогие</option>
          </select>
          <div className="relative">
            <img src="/search.svg" alt="Search" className="absolute left-3 top-2.5" />
            <input
              type="text"
              placeholder="Поиск..."
              className="border border-gray-200 rounded-md py-1.5 pl-11 pr-4 outline-none focus:border-gray-400"
            />
          </div>
        </div>
      </div>
      <div className="mt-10">
        <CardList :items="items" />
      </div>
    </div>
  </div>
</template>

<style scoped></style>
