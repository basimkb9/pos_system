package org.possystem.dao;

import org.possystem.domain.Cart;
import org.possystem.domain.Product;
import org.possystem.domain.Variant;
import org.possystem.mapper.ProductMapper;
import org.possystem.service.ItemNotFoundException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.possystem.dao.SqlQueryConstants.*;

public class ProductDAO extends BaseDAO implements ICrud<Product> {
    private final ProductMapper productMapper = new ProductMapper();

    @Override
    public void insert(Product obj) {
        try {
            conn.setAutoCommit(false);
            PreparedStatement ps = conn.prepareStatement(INSERT_INTO_PRODUCT);
            ps.setString(1,obj.getName());
            ps.setInt(2, Math.toIntExact(obj.getCategoryId()));

            int id = ps.executeUpdate();

            conn.commit();
        } catch (SQLException e) {
//            throw new RuntimeException(e);
            try {
                conn.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }

    }

    @Override
    public List<Product> getAll() {
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(GET_ALL_PRODUCT);
            return productMapper.resultSetToList(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public List<String> getAllNames() {
        try {
            List<String> productNameList = new ArrayList<>();

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(GET_ALL_PRODUCT);
            List<Product> products = productMapper.resultSetToList(rs);

//            for (Product product : products) {
//                productNameList.add(product.getName());
//            }

            productNameList = products.stream()
                    .map(s -> s.getName())
                    .collect(Collectors.toList());
            return productNameList;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    @Override
    public Product getById(Long id) {
        try {
            PreparedStatement stmt = conn.prepareStatement(GET_PRODUCT_BY_ID);
            stmt.setInt(1,Math.toIntExact(id));
            ResultSet rs = stmt.executeQuery();
            return productMapper.resultSetToObject(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void update(Product obj, Long id) {
        try{
            PreparedStatement ps = conn.prepareStatement(UPDATE_PRODUCT_BY_ID);
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
            PreparedStatement ps = conn.prepareStatement(DELETE_PRODUCT_BY_ID);
            ps.setInt(1,Math.toIntExact(id));
            ps.executeUpdate();
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    public List<String[]> getAllProductsAndVariant(){
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(GET_ALL_PRODUCT_WITH_VARIANT);
            return productMapper.resultSetToStringListWithVariant(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public List<String[]> getProductWithVariantByName(String barcode){
        List<String[]> productData = new ArrayList<>();
        try {
            PreparedStatement ps = conn.prepareStatement("select p.p_name, v.v_name, v.price from product p " +
                    "inner join variant v on p.id = v.p_id " +
                    "where v.barcode_id like '%" + barcode +"%'");

            ResultSet rs = ps.executeQuery();
            productData = productMapper.resultSetToStringListWithVariant(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productData;
    }


    public List<String[]> getProductByName(String name){
        List<String[]> productData = new ArrayList<>();
        try {
            PreparedStatement ps = conn.prepareStatement("select * from product where p_name like '%"+name+"%'");
//            ps.setString(1,name);

            ResultSet rs = ps.executeQuery();
            productData = productMapper.resultSetToStringList(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productData;
    }

    public List<String[]> getAllInString(){
        List<String[]> productData = new ArrayList<>();
        try {
            Statement stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery(GET_ALL_PRODUCT);
            productData = productMapper.resultSetToStringList(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productData;
    }


    public void insertProdAndVariant(Product product, List<Variant> variantInfo) {
        try {
            conn.setAutoCommit(false);

            PreparedStatement ps = conn.prepareStatement(INSERT_INTO_PRODUCT, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, product.getName());
            ps.setInt(2, product.getCategoryId().intValue());

            int rowsAffected = ps.executeUpdate();
            System.out.println(rowsAffected);

            if (rowsAffected >= 0) {
                ResultSet generatedId = ps.getGeneratedKeys();
                if (generatedId.next()) {
                    int id = generatedId.getInt(1);
//                  System.out.println(id);

                    for (int i = 0; i < variantInfo.size(); i++) {
                        try {
                            PreparedStatement variantPs = conn.prepareStatement(INSERT_INTO_VARIANT);

                            variantPs.setString(1, variantInfo.get(i).getName());
                            variantPs.setDouble(2, variantInfo.get(i).getCostPrice());
                            variantPs.setDouble(3, variantInfo.get(i).getPrice());
                            variantPs.setString(4,variantInfo.get(i).getBarcode());
                            variantPs.setLong(5, variantInfo.get(i).getQuantityInStock());
                            variantPs.setInt(6, id);
                            variantPs.executeUpdate();

                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    conn.commit();
                }
            }
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    public Cart getProductWithVariantByBarcode(String barcode) throws ItemNotFoundException {
        try {
            PreparedStatement ps = conn.prepareStatement("select p.p_name, v.v_name, v.price" +
                    " from product p inner join variant v on p.id = v.p_id" +
                    " where v.barcode_id = "+barcode);
//            ps.setString(1,name);

            ResultSet rs = ps.executeQuery();
            return productMapper.resultSetToCartItem(rs);
        } catch (SQLException e) {
            throw new ItemNotFoundException("Item not found");
        }
    }

    public void updateProductsAndVariants(Integer prodId, String productName, List<Variant> variants) throws SQLException {
        try{
            conn.setAutoCommit(false);
            PreparedStatement ps = conn.prepareStatement("update product set p_name = " + productName + " where id = "+ prodId);
            int rowsAffected = ps.executeUpdate();

            if(rowsAffected >= 1){
                for (Variant variant : variants) {
                    ps = conn.prepareStatement("update variant set v_name = ?, cost_price = ?, " +
                            "price = ?, quantity = ?, barcode_id = ? where p_id = ?");

                    ps.setString(1,variant.getName());
                    ps.setDouble(2,variant.getCostPrice());
                    ps.setDouble(3,variant.getPrice());
                    ps.setLong(4, variant.getQuantityInStock());
                    ps.setString(5,variant.getBarcode());
                    ps.setInt(6,prodId);

                    ps.executeUpdate();
                }
            }
            conn.commit();
        }
        catch (SQLException e){
            conn.rollback();
            throw new RuntimeException(e);
        }
    }
}