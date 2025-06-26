package org.yearup.data.mysql;

import org.springframework.stereotype.Component;
import org.yearup.models.ShoppingCart;
import org.yearup.models.ShoppingCartItem;

import javax.sql.DataSource;
import java.sql.*;

@Component
public class MySqlOrderDao extends MySqlDaoBase implements OrderDao {

    public MySqlOrderDao(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public int createOrder(int userId, ShoppingCart cart) {
        String insertOrder = "INSERT INTO orders (user_id, order_date) VALUES (?, NOW())";
        String insertItem = "INSERT INTO order_line_items (order_id, product_id, quantity, price) VALUES (?, ?, ?, ?)";

        try (Connection conn = getConnection()) {
            conn.setAutoCommit(false);

            // Insert order
            PreparedStatement orderStmt = conn.prepareStatement(insertOrder, Statement.RETURN_GENERATED_KEYS);
            orderStmt.setInt(1, userId);
            orderStmt.executeUpdate();

            ResultSet rs = orderStmt.getGeneratedKeys();
            rs.next();
            int orderId = rs.getInt(1);

            // Insert order line items
            PreparedStatement itemStmt = conn.prepareStatement(insertItem);
            for (ShoppingCartItem item : cart.getItems().values()) {
                itemStmt.setInt(1, orderId);
                itemStmt.setInt(2, item.getProduct().getProductId());
                itemStmt.setInt(3, item.getQuantity());
                itemStmt.setBigDecimal(4, item.getProduct().getPrice());
                itemStmt.addBatch();
            }

            itemStmt.executeBatch();

            conn.commit();
            return orderId;

        } catch (SQLException e) {
            throw new RuntimeException("Failed to create order", e);
        }
    }
}
