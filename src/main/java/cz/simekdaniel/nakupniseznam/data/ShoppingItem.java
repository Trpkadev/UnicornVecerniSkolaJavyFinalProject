package cz.simekdaniel.nakupniseznam.data;

import jakarta.persistence.Column;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

public class ShoppingItem
{
    @Id
    private int id;
    @Column(name = "content")
    private String content;
    @Column(name = "count")
    private int count;
    @Column(name = "state")
    private ShoopingItemStatus state;
    @Column(name = "createdAt")
    private LocalDateTime createdAt;

    public ShoppingItem(int id, String content, int count, ShoopingItemStatus state, LocalDateTime createdAt)
    {
        this.id = id;
        this.content = content;
        this.count = count;
        this.state = state;
        this.createdAt = createdAt;
    }
}
