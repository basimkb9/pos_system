package org.possystem.mapper;

import org.possystem.domain.TransactionDetail;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TransactionDetailMapper implements IMapper<TransactionDetail> {
    private static final String ID = "id";
    private static final String TRANSACTION_ID = "t_id";
    private static final String VARIANT_ID = "v_id";
    private static final String UNIT_PRICE = "unitprice";
    private static final String QUANTITY_SOLD = "quantity";
    private static final String SUB_TOTAL = "subtotal";
    private static final String COST_PRICE = "cost_price";
    private static final String PROFIT = "profit";
    @Override
    public List<TransactionDetail> resultSetToList(ResultSet rs) throws SQLException {
        List<TransactionDetail> transactionDetailList = new ArrayList<>();

        while (rs.next()){
            TransactionDetail transactionDetail = TransactionDetail.builder()
                    .id((long) rs.getInt(ID))
                    .transactionId((long) rs.getInt(TRANSACTION_ID))
                    .variantId((long) rs.getInt(VARIANT_ID))
                    .unitPrice(rs.getDouble(UNIT_PRICE))
                    .quantitySold(rs.getLong(QUANTITY_SOLD))
                    .subTotal(rs.getDouble(SUB_TOTAL))
                    .costPrice(rs.getDouble(COST_PRICE))
                    .profit(rs.getDouble(PROFIT))
                    .build();

            transactionDetailList.add(transactionDetail);
        }

        return transactionDetailList;
    }

    @Override
    public TransactionDetail resultSetToObject(ResultSet rs) throws SQLException {
        if(rs.next()){
            return TransactionDetail.builder()
                    .id((long) rs.getInt(ID))
                    .transactionId((long) rs.getInt(TRANSACTION_ID))
                    .variantId((long) rs.getInt(VARIANT_ID))
                    .unitPrice(rs.getDouble(UNIT_PRICE))
                    .quantitySold(rs.getLong(QUANTITY_SOLD))
                    .subTotal(rs.getDouble(SUB_TOTAL))
                    .costPrice(rs.getDouble(COST_PRICE))
                    .profit(rs.getDouble(PROFIT))
                    .build();
        }
        return null;
    }
}
