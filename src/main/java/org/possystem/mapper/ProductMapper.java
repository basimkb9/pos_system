package org.possystem.mapper;

import org.possystem.domain.Cart;
import org.possystem.domain.Product;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductMapper implements IMapper<Product> {
    private final String ID = "id";
    private final String NAME = "p_name";
    private final String QUANTITY = "quantity_in_stock";
    private final String CATEGORY_ID = "category_id";
    private final String V_NAME = "v.v_name";
    private final String V_PRICE = "v.price";

    @Override
    public List<Product> resultSetToList(ResultSet rs) throws SQLException {
        List<Product> productList = new ArrayList<>();

        while(rs.next()){
            Product p = Product.builder()
                    .id((long) rs.getInt(ID))
                    .name(rs.getString(NAME))
                    .categoryId((long) rs.getInt(CATEGORY_ID))
                    .build();
            productList.add(p);
        }

        return productList;
    }

    @Override
    public Product resultSetToObject(ResultSet rs) throws SQLException {
        if(rs.next()){
            return Product.builder()
                    .id(rs.getLong(ID))
                    .name(rs.getString(NAME))
                    .categoryId(rs.getLong(CATEGORY_ID))
                    .build();
        }
        return null;
    }

    public List<String[]> resultSetToStringListWithVariant(ResultSet rs) throws SQLException {
        List<String[]> productList = new ArrayList<>();

        while(rs.next()){
            String productName = rs.getString(NAME);
            String variantName = rs.getString(V_NAME);
            String price = rs.getString(V_PRICE);

            String[] row = {productName, variantName, price};
            productList.add(row);
        }

        return productList;
    }

    public List<String[]> resultSetToStringList(ResultSet rs) throws SQLException {
        List<String[]> productString = new ArrayList<>();

        while(rs.next()){
            String id = rs.getString(ID);
            String productName = rs.getString(NAME);
            String categoryId = rs.getString(CATEGORY_ID);

            String[] row = {id, productName, categoryId};
            productString.add(row);
        }

        return productString;
    }

    public Cart resultSetToCartItem(ResultSet rs) throws SQLException{
        try {
            if (rs.next()) {
                String pName = rs.getString("p_name");
                String vName = rs.getString("v_name");
                Double price = rs.getDouble("price");

                Cart c = Cart.builder()
                        .productName(pName)
                        .variantName(vName)
                        .quantity(1L)
                        .unitPrice(price)
                        .build();

                Double subTotal = c.getQuantity() * c.getUnitPrice();
                c.setSubTotal(subTotal);

                return c;
            }

        }catch (RuntimeException e){
            e.printStackTrace();
        }

        return null;
    }
}
