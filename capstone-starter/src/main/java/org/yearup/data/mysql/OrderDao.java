package org.yearup.data.mysql;

import org.yearup.models.ShoppingCart;

public interface OrderDao {
    int createOrder(int userId, ShoppingCart cart);
}
