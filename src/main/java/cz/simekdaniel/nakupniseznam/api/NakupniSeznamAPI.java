package cz.simekdaniel.nakupniseznam.api;

import cz.simekdaniel.nakupniseznam.repos.ShoppingItemRepo;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api")
public class NakupniSeznamAPI
{
    private final ShoppingItemRepo shoppingItemRepo;

    public NakupniSeznamAPI(ShoppingItemRepo shoppingItemRepo)
    {

        this.shoppingItemRepo = shoppingItemRepo;
    }

    @GetMapping(value = "/shoppingList", produces = APPLICATION_JSON_VALUE)
    public void shoppingListList()
    {
    }

    @PostMapping(value = "/shoppingItem", produces = APPLICATION_JSON_VALUE)
    public void shoppingItemCreate()
    {
    }

    @PutMapping(value = "/shoppingItem", produces = APPLICATION_JSON_VALUE)
    public void shoppingItemEdit()
    {
    }

    @DeleteMapping(value = "/shoppingItem", produces = APPLICATION_JSON_VALUE)
    public void shoppingItemDelete()
    {
    }
}