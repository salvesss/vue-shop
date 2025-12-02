<script setup>
const emit = defineEmits('onClickRemove')

defineProps({
  id: Number,
  title: String,
  imageUrl: String,
  price: Number,
})
</script>

<template>
  <div className="flex items-center border border-slate-200 p-4 rounded-xl gap-4">
    <img className="w-16 h-16" :src="imageUrl" alt="Velo" />
    <div className="flex flex-col">
      <p>{{ title }}</p>
      <div className="flex justify-between mt-2">
        <b>{{ price }} р.</b>
        <img
          @click="emit('onClickRemove')"
          className="opacity-40 hover:opacity-100 cursor-pointer transition"
          src="/close.svg"
          alt=""
        />
      </div>
    </div>
  </div>
</template>
