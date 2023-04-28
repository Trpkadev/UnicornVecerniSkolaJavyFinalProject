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
        if(shoppingItemNew.getCount() != 0)
        {
            shoppingItemToUpdate.get().setCount(shoppingItemNew.getCount());
        }
        if(shoppingItemNew.getState() != null)
        {
            shoppingItemToUpdate.get().setState(shoppingItemNew.getState());
        }
        save(shoppingItemToUpdate.get());
        return shoppingItemToUpdate.get();
    }

    public void delete(int id)
    {
        shoppingItemRepo.deleteById(id);
    }

    public List<ShoppingItem> filterItems(ShoppingItemStatus state)
    {
        List<ShoppingItem> shoppingItemList = shoppingItemRepo.findAll();
        shoppingItemList.removeIf(shoppingItem -> shoppingItem.getState() != state);
        return shoppingItemList;
    }
}
