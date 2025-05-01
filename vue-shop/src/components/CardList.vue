<script setup>
import Card from './Card.vue'

const onClickAdd = () => {
  alert('Добавить!')
}
</script>

<template>
  <div className="grid grid-cols-4 gap-5">
    <Card
      title="Мужские кроссовки Nike Blazer Mid Suede"
      imageUrl="/sneakers/sneakers-1.jpg"
      :price="5000"
      :isAdded="true"
      :isFavorite="true"
      :onClickAdd="onClickAdd"
    />
    <Card />
    <Card />
    <Card />
    <Card />
    <Card />
  </div>
</template>
