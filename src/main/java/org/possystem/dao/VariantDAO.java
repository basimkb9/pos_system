package org.possystem.dao;

import org.possystem.domain.Cart;
import org.possystem.domain.Variant;
import org.possystem.mapper.VariantMapper;
import org.possystem.service.ItemNotFoundException;
import org.possystem.service.QuantityOverflowException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.possystem.dao.SqlQueryConstants.*;

public class VariantDAO extends BaseDAO implements ICrud<Variant>{

    private final VariantMapper variantMapper = new VariantMapper();

    @Override
    public void insert(Variant obj) {
        try {
            PreparedStatement ps = conn.prepareStatement(INSERT_INTO_VARIANT);
            ps.setString(1, obj.getName());
            ps.setDouble(2, obj.getPrice());
            ps.setLong(2, obj.getQuantityInStock());
            ps.setInt(3, Math.toIntExact(obj.getProductId()));

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Variant> getAll() {
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(GET_ALL_VARIANT);
            return variantMapper.resultSetToList(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    @Override
    public Variant getById(Long id) {
        try {
            PreparedStatement stmt = conn.prepareStatement(GET_VARIANT_BY_ID);
            stmt.setInt(1,Math.toIntExact(id));
            ResultSet rs = stmt.executeQuery();
            return variantMapper.resultSetToObject(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void update(Variant obj, Long id) {
        try{
            PreparedStatement ps = conn.prepareStatement(UPDATE_VARIANT_BY_ID);
            ps.setString(1,obj.getName());
            ps.setInt(2,Math.toIntExact(id));
            ps.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void deleteById(Long id) {
        try{
            PreparedStatement ps = conn.prepareStatement(DELETE_VARIANT_BY_ID);
            ps.setInt(1,Math.toIntExact(id));
            ps.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public ResultSet getByProdNameAndVarName(String tableProdName, String tableVariantName) {
        try{
            PreparedStatement ps = conn.prepareStatement("select price from variant v " +
                    "inner join product p on v.p_id = p.id" +
                    "where p_name = '" +tableProdName+ "' and v_name = '" + tableVariantName + "'");
            ResultSet rs = ps.executeQuery();
            return rs;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String getVariantIdByVariantNameAndProductName(String pName, String vName) throws SQLException, ItemNotFoundException{
        try {
            PreparedStatement ps = conn.prepareStatement("select v_id from variant inner join product on product.id = variant.p_id " +
                    "where variant.v_name = ? and product.p_name = ?");
            ps.setString(1, vName);
            ps.setString(2, pName);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int variantId = rs.getInt("v_id");
                return String.valueOf(variantId);
            } else {
                throw new ItemNotFoundException("Variant ID not found");
            }

        } catch (SQLException e) {
            System.err.println("SQL Exception: " + e.getMessage()); // Print detailed SQL exception
            throw new RuntimeException(e);
        } catch (ItemNotFoundException e) {
            System.err.println("ItemNotFoundException: " + e.getMessage()); // Print detailed exception
            throw new RuntimeException(e);
        }
    }

    public Long getQuantityInStock(String pName, String vName) throws SQLException, ItemNotFoundException {
        try {
            PreparedStatement ps = conn.prepareStatement("select quantity from variant inner join product on product.id = variant.p_id " +
                    "where variant.v_name = ? and product.p_name = ?");
            ps.setString(1, vName);
            ps.setString(2, pName);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Long quantityInStock = rs.getLong("quantity");
                return quantityInStock;
            } else {
                throw new ItemNotFoundException("Item not found");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Double getCostPrice(String pName, String vName) {
        try {
            PreparedStatement ps = conn.prepareStatement("select cost_price from variant inner join product on product.id = variant.p_id\n" +
                    "where variant.v_name = ? and product.p_name = ?");
            ps.setString(1, vName);
            ps.setString(2, pName);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Double costPrice = rs.getDouble("cost_price");
                return costPrice;
            } else {
                throw new ItemNotFoundException("Item not found");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ItemNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateQuantityById(Long quantityInStock, String variantId) {
        try{
            Integer id = Integer.valueOf(variantId);

            PreparedStatement ps = conn.prepareStatement("update variant set quantity = ? where v_id = ?");
            ps.setLong(1,quantityInStock);
            ps.setInt(2,Math.toIntExact(id));

            ps.executeUpdate();
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    public Boolean updateQuantities(List<Cart> cartList) {
        boolean flag = true;
        for(Cart cart : cartList){
            try{
                String pName = cart.getProductName();
                String vName = cart.getVariantName();
                Integer id = Integer.valueOf(getVariantIdByVariantNameAndProductName(pName,vName));
                Long updatedQuantity = getQuantityInStock(pName,vName) - cart.getQuantity();

                PreparedStatement ps = conn.prepareStatement("update variant set quantity = ? where v_id = ?");
                ps.setLong(1,updatedQuantity);
                ps.setInt(2,id);

                ps.executeUpdate();
                if(ps.executeUpdate() == 0){
                    flag = false;
                    break;
                }
            }
            catch(SQLException e){
                throw new RuntimeException(e);
            }
            catch (ItemNotFoundException e){
                throw new RuntimeException(e);
            }
        }
        return flag;
    }

    public List<Variant> getAllByProductId(Long prodId){
        try{
            PreparedStatement ps = conn.prepareStatement("select * from variant where p_id = ?");
            ps.setInt(1, Math.toIntExact(prodId));

            ResultSet rs = ps.executeQuery();

            return variantMapper.resultSetToList(rs);

        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }
}
