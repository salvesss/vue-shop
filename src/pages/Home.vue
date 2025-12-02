<script setup>
import CardList from '../components/CardList.vue'

const props = defineProps({
  items: {
    type: Array,
    required: true,
  },
  filters: {
    type: Object,
    required: true,
  },
  onChangeSelect: {
    type: Function,
    required: true,
  },
  onChangeSearchInput: {
    type: Function,
    required: true,
  },
  addToFavorite: {
    type: Function,
    required: true,
  },
  onClickAddPlus: {
    type: Function,
    required: true,
  },
})
</script>

<template>
  <div>
    <div class="flex justify-between items-center">
      <h2 class="text-3xl font-bold mb-8">Все велосипеды</h2>

      <div class="flex gap-4">
        <select
          :value="filters.sortBy"
          @change="props.onChangeSelect"
          class="py-2 px-3 border border-gray-200 outline-none rounded-md"
        >
          <option value="name">По названию</option>
          <option value="price">Сначала дешевые</option>
          <option value="-price">Сначала дорогие</option>
        </select>
        <div class="relative">
          <img src="/search.svg" alt="Search" class="absolute left-3 top-2.5" />
          <input
            :value="filters.searchQuery"
            @input="props.onChangeSearchInput"
            type="text"
            placeholder="Поиск..."
            class="border border-gray-200 rounded-md py-1.5 pl-11 pr-4 outline-none focus:border-gray-400"
          />
        </div>
      </div>
    </div>

    <div class="mt-10">
      <CardList :items="items" @add-to-favorite="addToFavorite" @add-to-cart="onClickAddPlus" />
    </div>
  </div>
</template>


