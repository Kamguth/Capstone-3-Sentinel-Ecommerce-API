package org.yearup.models;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class ShoppingCart
{
    private Map<Integer, ShoppingCartItem> items = new HashMap<>();

    public Map<Integer, ShoppingCartItem> getItems()
    {
        return items;
    }

    public void setItems(Map<Integer, ShoppingCartItem> items)
    {
        this.items = items;
    }

    public boolean contains(int productId)
    {
        return items.containsKey(productId);
    }

    public void add(ShoppingCartItem item)
    {
        int productId = item.getProductId();

        if (items.containsKey(productId))
        {
            // Increase quantity if already exists
            ShoppingCartItem existing = items.get(productId);
            existing.setQuantity(existing.getQuantity() + item.getQuantity());
        }
        else
        {
            items.put(productId, item);
        }
    }

    public void updateQuantity(int productId, int quantity)
    {
        if (items.containsKey(productId))
        {
            items.get(productId).setQuantity(quantity);
        }
    }

    public ShoppingCartItem get(int productId)
    {
        return items.get(productId);
    }

    public void remove(int productId)
    {
        items.remove(productId);
    }

    public void clear()
    {
        items.clear();
    }

    public BigDecimal getTotal()
    {
        return items.values()
                .stream()
                .map(ShoppingCartItem::getLineTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
