package cz.simekdaniel.nakupniseznam.api;

import cz.simekdaniel.nakupniseznam.data.ShoppingItem;
import cz.simekdaniel.nakupniseznam.data.ShoppingItemStatus;
import cz.simekdaniel.nakupniseznam.repos.ShoppingItemRepo;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
public class NakupniSeznamAPI
{
    private final ShoppingItemService shoppingItemService;
    private final ShoppingItemRepo shoppingItemRepo;

    public NakupniSeznamAPI(ShoppingItemService shoppingItemService,
        ShoppingItemRepo shoppingItemRepo)
    {
        this.shoppingItemService = shoppingItemService;
        this.shoppingItemRepo = shoppingItemRepo;
    }

    // REV: Obecně mi tu chybělo logování, co se kdy děje s parametry (např. přes log4j, slf4j, ..., nebo i konzoli, která není ideální pro tyto účely)

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
        // REV: Z pohledu algoritmizace se ověření, zda existuje ukládaná položka do databáze dá udělat efektivněji, například jeden ze způsobů níže
        // 1) Najít v db pomocí content, zda existuje položka (nový dotaz do ShoppingItemRepo) - může vrátit například celou položku, nebo boolean informaci ano/ne
        // 2) Ověřit, zda je položka v db již přítomná a pokud ne, tak povolit uložení do db, náznak jak udělat je níže
        // Optional<ShoppingItem> foundShoppingItem = shoppingItemRepo.findByContent(shoppingItem.getContent());
        // foundShoppingItem.ifPresent(shoppingItemService::save);

        boolean itemAlreadyExists = false;
        if (!shoppingItem.getContent().isEmpty())
        {
            for (ShoppingItem shoppingItemInDatabase : shoppingItemService.allItems())
            {
                if (shoppingItemInDatabase.getContent().equals(shoppingItem.getContent()))
                {
                    itemAlreadyExists = true;
                    break;
                }
            }
            if (!itemAlreadyExists)
            {
                shoppingItemService.save(shoppingItem);
                return new ResponseEntity<>(shoppingItem, HttpStatus.CREATED);
            }
            return null;
        }
        return null;
    }

    @PutMapping(value = "/shoppingItem/{id}")
    public ResponseEntity<ShoppingItem> shoppingItemEdit(@RequestBody ShoppingItem shoppingItem, @PathVariable int id)
    {
        return new ResponseEntity<>(shoppingItemService.edit(id, shoppingItem), HttpStatus.CREATED);
    }

    @GetMapping(value = "/shoppingItem/{id}")
    public ResponseEntity<String> shoppingItemListAfterStatusChanged()
    {
        //To prevent warnings from GET calls on status changes
        return new ResponseEntity<>("{}", HttpStatus.OK);
    }

    @DeleteMapping(value = "/shoppingItem/{id}")
    public ResponseEntity<String> shoppingItemDelete(@PathVariable int id)
    {
        shoppingItemService.delete(id);
        return new ResponseEntity<>("{}", HttpStatus.OK);
    }
}