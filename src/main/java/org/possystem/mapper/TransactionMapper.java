package org.possystem.mapper;

import org.possystem.domain.Transaction;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TransactionMapper implements IMapper<Transaction> {
    private final String ID = "id";
    private final String DATE = "tr_date";
    private final String AMOUNT_PAID = "total_amount";
    private final String PAYMENT_METHOD = "payment_method";
    @Override
    public List<Transaction> resultSetToList(ResultSet rs) throws SQLException {
        List<Transaction> transactionList = new ArrayList<>();

        while(rs.next()){
            Transaction transaction = Transaction.builder()
                    .id((long) rs.getInt(ID))
                    .transactionDate(rs.getDate(DATE).toLocalDate())
                    .amountPaid(rs.getDouble(AMOUNT_PAID))
                    .paymentMethod(rs.getString(PAYMENT_METHOD))
                    .build();

            transactionList.add(transaction);
        }

        return transactionList;
    }

    @Override
    public Transaction resultSetToObject(ResultSet rs) throws SQLException {
        if(rs.next()){
            return Transaction.builder()
                    .id((long) rs.getInt(ID))
                    .transactionDate(rs.getDate(DATE).toLocalDate())
                    .amountPaid(rs.getDouble(AMOUNT_PAID))
                    .paymentMethod(rs.getString(PAYMENT_METHOD))
                    .build();
        }
        return null;
    }
}
