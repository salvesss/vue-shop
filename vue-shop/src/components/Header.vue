<template>
  <header className="flex justify-between border-b border-slate-200 px-8 py-8">
    <div className="flex items-center gap-4">
      <img src="/logo.png" alt="Logo" className="w-15 rounded-2xl" />
      <div>
        <h2 className="text-xl font-bold">bikeON</h2>
        <p className="text-slate-400">Магазин лучших велосипедов</p>
      </div>
    </div>

    <ul className="flex items-center gap-10">
      <li className="flex items-center gap-3 cursor-pointer text-gray-500 hover:text-black">
        <img src="/cart.svg" alt="Cart" />
        <b>1205 руб.</b>
      </li>
      <li className="flex items-center gap-3 cursor-pointer text-gray-500 hover:text-black">
        <img src="/heart.svg" alt="Heart" />
        <span>Закладки</span>
      </li>
      <li className="flex items-center gap-3 cursor-pointer text-gray-500 hover:text-black">
        <img src="/profile.svg" alt="Profile" />
        <span>Профиль</span>
      </li>
    </ul>
  </header>
</template>
