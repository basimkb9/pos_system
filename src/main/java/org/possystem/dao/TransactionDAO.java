package org.possystem.dao;

import com.sun.corba.se.impl.orb.PrefixParserAction;
import org.possystem.domain.Transaction;
import org.possystem.mapper.TransactionMapper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.possystem.dao.SqlQueryConstants.*;

public class TransactionDAO extends BaseDAO implements ICrud<Transaction> {

    private final TransactionMapper transactionMapper = new TransactionMapper();
    @Override
    public void insert(Transaction obj) {
        try {
            PreparedStatement ps = conn.prepareStatement(INSERT_INTO_TRANSACTIONS);
            ps.setDate(1, Date.valueOf(obj.getTransactionDate()));
            ps.setDouble(2, obj.getAmountPaid());
            ps.setString(3,obj.getPaymentMethod());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Transaction> getAll() {
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(GET_ALL_TRANSACTIONS);
            return transactionMapper.resultSetToList(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    @Override
    public Transaction getById(Long id) {
        try {
            PreparedStatement stmt = conn.prepareStatement(GET_TRANSACTION_BY_ID);
            stmt.setInt(1,Math.toIntExact(id));
            ResultSet rs = stmt.executeQuery();
            return transactionMapper.resultSetToObject(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void update(Transaction obj, Long id) {
        try{
            PreparedStatement ps = conn.prepareStatement(UPDATE_TRANSACTION_BY_ID);
            ps.setString(1,obj.getPaymentMethod());
            ps.setInt(2,Math.toIntExact(obj.getId()));
            ps.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void deleteById(Long id) {
        try{
            PreparedStatement ps = conn.prepareStatement(DELETE_TRANSACTION_BY_ID);
            ps.setInt(1,Math.toIntExact(id));
            ps.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public List<String[]> getAllInStringList() {
        List<String[]> trList = new ArrayList<>();

        try{
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(GET_ALL_TRANSACTIONS);

            List<Transaction> trDataSet = transactionMapper.resultSetToList(rs);

            for (int i = 0; i < trDataSet.size(); i++) {
                String id = String.valueOf(trDataSet.get(i).getId());
                String trDate = String.valueOf(trDataSet.get(i).getTransactionDate());
                String amountPaid = String.valueOf(trDataSet.get(i).getAmountPaid());
                String paymentMethod = String.valueOf(trDataSet.get(i).getPaymentMethod());

                String[] row = {id,trDate,amountPaid,paymentMethod};
                trList.add(row);
            }

            return trList;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
