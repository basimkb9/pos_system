package org.possystem.dao;

import org.possystem.domain.Cart;
import org.possystem.domain.TransactionDetail;
import org.possystem.mapper.TransactionDetailMapper;
import org.possystem.service.ItemNotFoundException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.possystem.dao.SqlQueryConstants.*;

public class TransactionDetailDAO extends BaseDAO implements ICrud<TransactionDetail> {
    TransactionDetailMapper saleDetailMapper = new TransactionDetailMapper();
    VariantDAO variantDAO = new VariantDAO();
    @Override
    public void insert(TransactionDetail obj) {
//        try {
//            PreparedStatement ps = conn.prepareStatement(INSERT_INTO_TRANSACTION_DETAILS);
//            ps.setInt(1, Math.toIntExact(obj.getTransactionId()));
//            ps.setInt(2, Math.toIntExact(obj.getProductId()));
//            ps.setDouble(3, obj.getUnitPrice());
//            ps.setLong(4, obj.getQuantitySold());
//            ps.setDouble(5, obj.getSubTotal());
//
//            ps.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public List<TransactionDetail> getAll() {
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(GET_ALL_TRANSACTION_ITEMS);
            return saleDetailMapper.resultSetToList(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    @Override
    public TransactionDetail getById(Long id) {
        try {
            PreparedStatement stmt = conn.prepareStatement(GET_TRANSACTION_ITEM_BY_ID);
            stmt.setInt(1,Math.toIntExact(id));
            ResultSet rs = stmt.executeQuery();
            return saleDetailMapper.resultSetToObject(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void update(TransactionDetail obj, Long id) {
        try{
            PreparedStatement ps = conn.prepareStatement(UPDATE_TRANSACTION_ITEM_BY_ID);
            ps.setLong(1,obj.getQuantitySold());
            ps.setInt(2, Math.toIntExact(id));
            ps.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void deleteById(Long id) {
        try{
            PreparedStatement ps = conn.prepareStatement(DELETE_TRANSACTION_ITEM_BY_ID);
            ps.setInt(1,Math.toIntExact(id));
            ps.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public List<String[]> getAllStringList() {
        List<String[]> saleDetailStringList = new ArrayList<>();

        try{
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(GET_ALL_TRANSACTION_ITEMS);

            List<TransactionDetail> transactionDetailList = saleDetailMapper.resultSetToList(rs);

            for (int i = 0; i < transactionDetailList.size(); i++) {
                String id = String.valueOf(transactionDetailList.get(i).getId());
                String transactionId = String.valueOf(transactionDetailList.get(i).getTransactionId());
                String productId = String.valueOf(transactionDetailList.get(i).getVariantId());
                String unitPrice = String.valueOf(transactionDetailList.get(i).getUnitPrice());
                String quantitySold = String.valueOf(transactionDetailList.get(i).getQuantitySold());
                String subTotal = String.valueOf(transactionDetailList.get(i).getSubTotal());
                String costPrice = String.valueOf(transactionDetailList.get(i).getCostPrice());
                String profit = String.valueOf(transactionDetailList.get(i).getProfit());

                String[] row = {id,transactionId,productId,unitPrice,quantitySold,subTotal,costPrice,profit};
                saleDetailStringList.add(row);
            }

            return saleDetailStringList;

        }catch (SQLException e){
            throw new RuntimeException(e);
        }

    }

    public boolean saveTransactionAndDetails(List<Cart> cartList, Double totalAmount) throws SQLException{
        try{
            conn.setAutoCommit(false);

            PreparedStatement ps = conn.prepareStatement("insert into transactions(tr_date,total_amount,payment_method) " +
                    "values (curdate(),"+totalAmount+",'cash')", Statement.RETURN_GENERATED_KEYS);

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected >= 0) {
                ResultSet generatedId = ps.getGeneratedKeys();
                if (generatedId.next()) {
                    int t_id = generatedId.getInt(1);
//                  System.out.println(id);

                    for (int i = 0; i < cartList.size(); i++) {
                        try {
                            String v_id = variantDAO.getVariantIdByVariantNameAndProductName(
                                    cartList.get(i).getProductName(),cartList.get(i).getVariantName());

                            PreparedStatement saleDetailPs = conn.prepareStatement(INSERT_INTO_TRANSACTION_DETAILS);

                            saleDetailPs.setInt(1,t_id);
                            saleDetailPs.setInt(2, Integer.parseInt(v_id));
                            saleDetailPs.setDouble(3,cartList.get(i).getUnitPrice());
                            saleDetailPs.setLong(4,cartList.get(i).getQuantity());
                            saleDetailPs.setDouble(5,cartList.get(i).getSubTotal());
                            saleDetailPs.setDouble(6,cartList.get(i).getCostPrice());

                            Double costPriceTotal = cartList.get(i).getCostPrice() * cartList.get(i).getQuantity();
                            Double profit = cartList.get(i).getSubTotal() - costPriceTotal;

                            saleDetailPs.setDouble(7,profit);

                            saleDetailPs.executeUpdate();

                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                        catch(ItemNotFoundException e){
                            throw new RuntimeException(e);
                        }
                    }
                    conn.commit();

                    return true;
                }
            }
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
        return false;
    }
}
