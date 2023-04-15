package cz.simekdaniel.nakupniseznam.api;

import com.google.gson.Gson;
import cz.simekdaniel.nakupniseznam.data.ShoppingItem;
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

    private final Gson gson = new Gson();

    public NakupniSeznamAPI(ShoppingItemService shoppingItemService)
    {
        this.shoppingItemService = shoppingItemService;
    }

    @GetMapping(value = "/shoppingList", produces = APPLICATION_JSON_VALUE)
    public List<ShoppingItem> shoppingListList()
    {
        System.out.println(shoppingItemService.allItems());
        return shoppingItemService.allItems();
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