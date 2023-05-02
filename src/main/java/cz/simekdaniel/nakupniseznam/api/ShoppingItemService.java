package cz.simekdaniel.nakupniseznam.api;

import cz.simekdaniel.nakupniseznam.data.ShoppingItem;
import cz.simekdaniel.nakupniseznam.data.ShoppingItemStatus;
import cz.simekdaniel.nakupniseznam.repos.ShoppingItemRepo;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ShoppingItemService
{
    private final ShoppingItemRepo shoppingItemRepo;

    public ShoppingItemService(ShoppingItemRepo shoppingItemRepo)
    {
        this.shoppingItemRepo = shoppingItemRepo;
    }

    public List<ShoppingItem> allItems()
    {
        return shoppingItemRepo.findAll();
    }

    public void save(ShoppingItem shoppingItem)
    {
        shoppingItem.setCreatedAt(LocalDateTime.now());
        shoppingItemRepo.save(shoppingItem);
    }

    public ShoppingItem edit(int id, ShoppingItem shoppingItemNew)
    {
        Optional<ShoppingItem> shoppingItemToUpdate = shoppingItemRepo.findById(id);
        if (shoppingItemNew.getCount() != 0)
        {
            shoppingItemToUpdate.get().setCount(shoppingItemNew.getCount()); // REV: Pozor na práci s Optional, tímto způsobem se s ním nepracuje a takto to ani nemusí fungovat, pokud bude Optional prázdné (výjimka)
            // pokud se použije metoda get(), která se obecně nedoporučuje používat ve většině případů, tak by to mělo předcháze kontrola, zda je Optional neprázdné přes metodu isPresent()
            // obecně, ale chceme spíše používat metody, jako ifPresent, orElse, orElseThrow, ... které nám zvyšují přehlednost, snižují if konstrukty a zvyšují bezpečnost kódu
            // mohlo by to vypadat např takto: shoppingItemToUpdate.ifPresent(shoppingItem -> shoppingItem.setCount(shoppingItemNew.getCount()));
        }
        if (shoppingItemNew.getState() != null)
        {
            shoppingItemToUpdate.get().setState(shoppingItemNew.getState()); // REV: Stejné, jako výše
        }
        save(shoppingItemToUpdate.get()); // REV: Stejné, jako výše
        return shoppingItemToUpdate.get(); // REV: Stejné, jako výše
    }

    public void delete(int id)
    {
        shoppingItemRepo.deleteById(id);
    }

    public List<ShoppingItem> filterItems(ShoppingItemStatus state)
    {
        // REV: Mnohem efektivnější je nechat vrátit databázi položky daného stavu, než načítat všechny do paměti a pak na aplikační vrstvě je filtrovat (CPU a paměť / rychlost a pamětové nároky)
        List<ShoppingItem> shoppingItemList = shoppingItemRepo.findAll();
        shoppingItemList.removeIf(shoppingItem -> shoppingItem.getState() != state);
        return shoppingItemList;
    }
}
