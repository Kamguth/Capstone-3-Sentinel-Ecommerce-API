package org.yearup.data;

import org.yearup.models.ShoppingCart;
import org.yearup.models.ShoppingCartItem;

public interface ShoppingCartDao
{
    // Get the full shopping cart for a user
    ShoppingCart getCartByUserId(int userId);

    // Add a new item or increment quantity if it already exists
    void addOrUpdateItem(int userId, int productId, int quantity);

    // Update the quantity of an item in the cart
    void updateQuantity(int userId, int productId, int quantity);

    // Remove all items from a user's cart
    void clearCart(int userId);
}
