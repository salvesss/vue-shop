<script setup>
import { computed } from 'vue'

import CardList from '../components/CardList.vue'

const props = defineProps({
  items: {
    type: Array,
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

const favoriteItems = computed(() => props.items.filter((item) => item.isFavorite))
</script>

<template>
  <div>
    <div class="flex justify-between items-center">
      <h2 class="text-3xl font-bold mb-8">Мои закладки</h2>
    </div>

    <div v-if="favoriteItems.length" class="mt-10">
      <CardList
        :items="favoriteItems"
        @add-to-favorite="addToFavorite"
        @add-to-cart="onClickAddPlus"
      />
    </div>

    <div v-else class="mt-10 text-center text-gray-500">
      У вас ещё нет закладок. Отметьте понравившийся велосипед сердечком.
    </div>
  </div>
</template>


