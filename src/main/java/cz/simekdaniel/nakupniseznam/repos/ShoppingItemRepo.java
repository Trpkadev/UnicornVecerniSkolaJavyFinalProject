package cz.simekdaniel.nakupniseznam.repos;

import cz.simekdaniel.nakupniseznam.data.ShoppingItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingItemRepo extends JpaRepository<ShoppingItem, Integer>
{
}