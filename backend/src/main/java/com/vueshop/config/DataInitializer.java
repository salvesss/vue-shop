package com.vueshop.config;

import com.vueshop.model.Item;
import com.vueshop.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private ItemRepository itemRepository;

    @Override
    public void run(String... args) {
        if (itemRepository.count() == 0) {
            // Initialize with sample bicycle data
            itemRepository.save(new Item("Велосипед AIST Rocky 2.0 Disc 27.5 красный (2023)", "/velo/velo1.jpg", 1051));
            itemRepository.save(new Item("Велосипед AIST Slide 2.0 Disc 29 (2025)", "/velo/velo2.jpg", 1377));
            itemRepository.save(new Item("Велосипед AIST Rosy 1.0 Disc 27.5 (2025)", "/velo/velo3.jpg", 888));
            itemRepository.save(new Item("Велосипед Totem W860 MD 27.5 (2025) белый", "/velo/velo4.jpg", 890));
            itemRepository.save(new Item("Велосипед Marin DSX 2 (2023)", "/velo/velo5.jpg", 3915));
            itemRepository.save(new Item("Велосипед ATOM Tundra PRO MatteConcrete", "/velo/velo6.jpg", 4250));
            itemRepository.save(new Item("Велосипед Titan Racing Valerian Sport Midnight Shine(2024)", "/velo/velo7.jpg", 3200));
            itemRepository.save(new Item("Велосипед Merida eSpeeder 200 SteelBlue/Silver/Black", "/velo/velo8.jpg", 9480));
            itemRepository.save(new Item("Велосипед Atom Nitro (2022)", "/velo/velo9.jpg", 1265));
            itemRepository.save(new Item("Велосипед Forward Iris 26 1.0 (2021)", "/velo/velo10.jpg", 690));
        }
    }
}




