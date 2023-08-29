package org.possystem.mapper;

import org.possystem.domain.Variant;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
public class VariantMapper implements IMapper<Variant>{
    private final static String ID = "v_id";
    private final static String NAME = "v_name";
    private final static String PRICE = "price";
    private final static String QUANTITY = "quantity";
    private final static String PRODUCTID = "p_id";
    private final static String BARCODE = "barcode_id";
    private final static String COST_PRICE = "cost_price";



    @Override
    public List<Variant> resultSetToList(ResultSet rs) throws SQLException {
        List<Variant> variantList = new ArrayList<>();

        while(rs.next()){
            Variant variant = Variant.builder()
                    .id((long) rs.getInt(ID))
                    .name(rs.getString(NAME))
                    .price(rs.getDouble(PRICE))
                    .quantityInStock(rs.getLong(QUANTITY))
                    .productId((long) rs.getInt(PRODUCTID))
                    .barcode(rs.getString(BARCODE))
                    .costPrice(rs.getDouble(COST_PRICE))
                    .build();

            variantList.add(variant);
        }

        return variantList;

    }

    @Override
    public Variant resultSetToObject(ResultSet rs) throws SQLException {
        if(rs.next()){
            return Variant.builder()
                    .id((long) rs.getInt(ID))
                    .name(rs.getString(NAME))
                    .price(rs.getDouble(PRICE))
                    .quantityInStock(rs.getLong(QUANTITY))
                    .productId((long) rs.getInt(PRODUCTID))
                    .build();
        }
        return null;
    }

    public String resultSetToString(ResultSet rs) {
        try {
            if(rs.next()){
                String v_id = String.valueOf(rs.getInt(ID));
                return v_id;
            }
            else{
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
