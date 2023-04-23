package cz.simekdaniel.nakupniseznam.data;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table
public class ShoppingItem
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "content")
    private String content;
    @Column(name = "count")
    private int count;
    @Enumerated(EnumType.STRING)
    @Column(name = "state")
    private ShoppingItemStatus state;
    @Column(name = "createdAt")
    private LocalDateTime createdAt;

    public ShoppingItem()
    {
    }

    @Override
    public String toString()
    {
        return "{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", count=" + count +
                ", state=" + state +
                ", createdAt=" + createdAt +
                '}';
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public int getCount()
    {
        return count;
    }

    public void setCount(int count)
    {
        this.count = count;
    }

    public ShoppingItemStatus getState()
    {
        return state;
    }

    public void setState(ShoppingItemStatus state)
    {
        this.state = state;
    }

    public void setState(String state)
    {
        this.state = state.equals("active") ? ShoppingItemStatus.active : ShoppingItemStatus.completed;
    }

    public void setCreatedAt(LocalDateTime createdAt)
    {
        this.createdAt = createdAt;
    }
}