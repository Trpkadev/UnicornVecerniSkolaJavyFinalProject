package cz.simekdaniel.nakupniseznam.api;

import cz.simekdaniel.nakupniseznam.data.ShoppingItem;
import cz.simekdaniel.nakupniseznam.data.ShoppingItemStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping
public class NakupniSeznamAPI
{
    @Autowired
    private ShoppingItemService shoppingItemService;

    public NakupniSeznamAPI(ShoppingItemService shoppingItemService)
    {
        this.shoppingItemService = shoppingItemService;
    }

    @GetMapping(value = "/shoppingList", produces = APPLICATION_JSON_VALUE)
    public List<ShoppingItem> shoppingItemsList()
    {
        System.out.println(shoppingItemService.allItems());
        return shoppingItemService.allItems();
    }

    @GetMapping(value = "/shoppingList/{state}", produces = APPLICATION_JSON_VALUE)
    public List<ShoppingItem> shoppingItemsListWithFilter(@PathVariable ShoppingItemStatus state)
    {
        List<ShoppingItem> shoppingItemList = shoppingItemService.filterItems(state);
        System.out.println(shoppingItemList);
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