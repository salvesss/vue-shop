<script setup>
import DrawerHead from './DrawerHead.vue'
import CartItemList from './CartItemList.vue'

defineProps({
  totalPrice: Number,
  vatPrice: Number,
})
</script>

<template>
  <div class="fixed top-0 left-0 h-full w-full bg-black z-10 opacity-70"></div>
  <div class="bg-white w-96 h-full fixed right-0 top-0 z-20 p-8">
    <DrawerHead />
    <CartItemList />

    <div class="flex flex-col gap-4 mt-7">
      <div class="flex gap-2">
        <span>Итого:</span>
        <div class="flex-1 border-b border-dashed border-slate-500"></div>
        <b>{{ totalPrice }} р.</b>
      </div>

      <button
        disabled=""
        class="bg-lime-500 mt-4 rounded-xl w-full py-3 text-white transition cursor-pointer hover:bg-lime-600 active:bg-lime-700 disabled:bg-slate-400"
      >
        Оформить заказ
      </button>
    </div>
  </div>
</template>
