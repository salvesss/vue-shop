<script setup>
import Card from './Card.vue'

defineProps({
  items: Array,
})

const onClickAdd = () => {
  alert('Добавить!')
}
</script>

<template>
  <div className="grid grid-cols-4 gap-5">
    <Card
      v-for="item in items"
      :key="item.id"
      :title="item.title"
      :imageUrl="item.imageUrl"
      :price="Number(item.price)"
      :onClickAdd="onClickAdd"
    />
  </div>
</template>
