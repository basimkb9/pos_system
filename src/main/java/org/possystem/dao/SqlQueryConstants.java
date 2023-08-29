package org.possystem.dao;

public class SqlQueryConstants {
    public static final String GET_ALL_PRODUCT = "select * from product";
    public static final String GET_ALL_PRODUCT_BY_NAME = "select * from product where p_name like ?";
    public static final String GET_ALL_TRANSACTIONS = "select * from transactions";
    public static final String GET_TRANSACTION_BY_ID = "select * from transactions where id = ?";
    public static final String UPDATE_TRANSACTION_BY_ID = "update transactions set payment_method = ? where id = ?";
    public static final String DELETE_TRANSACTION_BY_ID = "delete from transactions where id = ?";
    public static final String INSERT_INTO_TRANSACTIONS = "insert into transactions(tr_date,total_amount,payment_method) values (curdate(),?,'cash')";

    public static final String GET_USER_BY_USERNAME = "select * from user where username = ?";
    public static final String GET_ALL_CATEGORY = "select * from category";
    public static final String GET_ALL_TRANSACTION_ITEMS = "select * from transaction_details";
    public static final String INSERT_INTO_TRANSACTION_DETAILS = "insert into transaction_details(t_id,v_id,unitprice,quantity,subtotal,cost_price,profit) values (?,?,?,?,?,?,?)";
    public static final String GET_TRANSACTION_ITEM_BY_ID = "select * from transaction_items where id = ?";
    public static final String UPDATE_TRANSACTION_ITEM_BY_ID = "update transaction_items set quantity_sold = ? where id = ?";
    public static final String DELETE_TRANSACTION_ITEM_BY_ID = "delete from transaction_details where id = ?";

    public static final String GET_USERNAME_AND_PASS = "select * from user where username = ? and pass = ?";
    public static final String GET_ALL_PRODUCT_NAME = "select p_name from product";

    public static final String INSERT_INTO_PRODUCT = "insert into product(p_name,category_id) " +
            "values(?,?)";

    public static final String GET_PRODUCT_BY_ID = "select * from product where id = ?";
    public static final String UPDATE_PRODUCT_BY_ID = "update product set p_name = ? where id = ?";
    public static final String DELETE_PRODUCT_BY_ID = "delete from product where id = ?";
    public static final String INSERT_INTO_CATEGORY = "insert into category(c_name) values (?)";
    public static final String UPDATE_CATEGORY_BY_ID = "update category set name = ? where id = ?";
    public static final String DELETE_CATEGORY_BY_ID = "delete from category where id = ?";
    public static final String GET_CATEGORY_BY_ID = "select * from category where id = ?";

    public static final String GET_ALL_VARIANT = "select * from variant";
    public static final String GET_VARIANT_BY_ID = "select * from variant where id = ?";
    public static final String GET_VARIANT_BY_PROD_ID = "select * from variant where p_id = ?";
    public static final String INSERT_INTO_VARIANT = "insert into variant(v_name,cost_price,price,barcode_id,quantity,p_id) values (?,?,?,?,?,?)";
    public static final String UPDATE_VARIANT_BY_ID = "update variant set v_name = ? where id = ?";
    public static final String DELETE_VARIANT_BY_ID = "delete from variant where id = ?";


    public static final String GET_ALL_PRODUCT_WITH_VARIANT = "select p.p_name, v.v_name, v.price from product p " +
            "inner join variant v on p.id = v.p_id " +
            "order by p.p_name";
    public static final String GET_ALL_PRODUCT_NAME_WITH_VARIANT = "select p.p_name, v.v_name, v.price from product p " +
            "inner join variant v on p.id = v.p_id " +
            "where p.p_name like ?";


}
