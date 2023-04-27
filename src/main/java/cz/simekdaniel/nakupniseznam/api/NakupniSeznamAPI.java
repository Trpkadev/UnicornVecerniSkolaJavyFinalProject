package cz.simekdaniel.nakupniseznam.api;

import cz.simekdaniel.nakupniseznam.data.ShoppingItem;
import cz.simekdaniel.nakupniseznam.data.ShoppingItemStatus;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<List<ShoppingItem>> shoppingItemsListWithFilter(@RequestParam(name = "state", defaultValue = "") ShoppingItemStatus state)
    {
        List<ShoppingItem> shoppingItemList;
        if (state != null)
        {
            shoppingItemList = shoppingItemService.filterItems(state);
        } else
        {
            shoppingItemList = shoppingItemService.allItems();
        }
        return new ResponseEntity<>(shoppingItemList, HttpStatus.CREATED);
    }

    @PostMapping(value = "/shoppingItem")
    public ResponseEntity<ShoppingItem> shoppingItemCreate(@RequestBody ShoppingItem shoppingItem)
    {
        if (!shoppingItem.getContent().isEmpty())
        {
            shoppingItemService.save(shoppingItem);
            return new ResponseEntity<>(shoppingItem, HttpStatus.CREATED);
        }
        return null;
    }

    @PutMapping(value = "/shoppingItem/{id}")
    public ResponseEntity<ShoppingItem> shoppingItemEdit(@RequestBody ShoppingItem shoppingItem, @PathVariable int id)
    {
        return new ResponseEntity<>(shoppingItemService.edit(id, shoppingItem), HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/shoppingItem/{id}")
    public ResponseEntity<String> shoppingItemDelete(@PathVariable int id)
    {
        shoppingItemService.delete(id);
        return new ResponseEntity<>("{}", HttpStatus.OK);
    }
}