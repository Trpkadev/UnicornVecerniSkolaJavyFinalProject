package cz.simekdaniel.nakupniseznam.api;

import cz.simekdaniel.nakupniseznam.data.ShoppingItem;
import cz.simekdaniel.nakupniseznam.data.ShoppingItemStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
public class NakupniSeznamAPI
{
    private final ShoppingItemService shoppingItemService;

    public NakupniSeznamAPI(ShoppingItemService shoppingItemService)
    {
        this.shoppingItemService = shoppingItemService;
    }

    @GetMapping(value = "/shoppingList", produces = APPLICATION_JSON_VALUE)
    public List<ShoppingItem> shoppingItemsListWithFilter(@RequestParam(name = "state", defaultValue = "") ShoppingItemStatus state)
    {
        List<ShoppingItem> shoppingItemList;
        if (state != null)
        {
            shoppingItemList = shoppingItemService.filterItems(state);
        } else
        {
            shoppingItemList = shoppingItemService.allItems();
        }
        return shoppingItemList;
    }

    @PostMapping(value = "/shoppingItem")
    public ResponseEntity<ShoppingItem> shoppingItemCreate(@RequestBody ShoppingItem shoppingItem)
    {
        shoppingItemService.save(shoppingItem);
        return ResponseEntity.ok(shoppingItem);
    }

    @PutMapping(value = "/shoppingItem/{id}")
    public ShoppingItem shoppingItemEdit(@RequestBody ShoppingItem shoppingItem, @PathVariable int id)
    {
        return shoppingItemService.edit(id, shoppingItem);
    }

    @DeleteMapping(value = "/shoppingItem/{id}")
    public String shoppingItemDelete(@PathVariable int id)
    {
        shoppingItemService.delete(id);
        return "{}";
    }
}