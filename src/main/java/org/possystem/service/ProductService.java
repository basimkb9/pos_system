package org.possystem.service;

import org.possystem.dao.CategoryDAO;
import org.possystem.dao.ICrud;
import org.possystem.dao.ProductDAO;
import org.possystem.dao.VariantDAO;
import org.possystem.domain.Cart;
import org.possystem.domain.Category;
import org.possystem.domain.Product;
import org.possystem.domain.Variant;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.text.DefaultTextUI;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductService {

    private final ProductDAO productDAO = new ProductDAO();
    private final VariantDAO variantDAO = new VariantDAO();

    private final CategoryDAO categoryDAO = new CategoryDAO();

    public String[][] getAllProductsWithVariants(){
        List<String[]> productListWithVariant = productDAO.getAllProductsAndVariant();

        String[][] resultArray = productListWithVariant.toArray(new String[0][0]);

        return resultArray;
    }

    public String[][] getAllProductsWithVariantsByName(String name){
        List<String[]> productListWithVariant = productDAO.getProductWithVariantByName(name);

        return transformToTable(productListWithVariant);
    }


    public Cart getByBarcode(String barcode) throws ItemNotFoundException{
        Cart item = productDAO.getProductWithVariantByBarcode(barcode);
        return item;
    }
    private String[][] transformToTable(List<String[]> items){
        String[][] resultArray = items.toArray(new String[0][0]);

        return resultArray;
    }

    public String[][] searchProduct(String name){
        return transformToTable(productDAO.getProductByName(name));
    }

    public String[][] getAllProducts(){
        List<String[]> productListWithVariant = productDAO.getAllInString();

        String[][] resultArray = productListWithVariant.toArray(new String[0][0]);

        return resultArray;
    }

    public void saveProd(String name, String categoryId, List<String[]> variantInfo) {
        List<Variant> variantList = new ArrayList<>();

        Product product = Product.builder()
                .name(name)
                .categoryId(Long.valueOf(categoryId))
                .build();

        for (int i = 0; i < variantInfo.size(); i++) {
            String vName = variantInfo.get(i)[0];
            String vCostPrice = variantInfo.get(i)[1];
            String vSalePrice = variantInfo.get(i)[2];
            String vBarcode = variantInfo.get(i)[4];
            String vQuantity = variantInfo.get(i)[3];

            Variant variant = Variant.builder()
                    .name(vName)
                    .costPrice(Double.valueOf(vCostPrice))
                    .price(Double.valueOf(vSalePrice))
                    .barcode(vBarcode.trim())
                    .quantityInStock(Long.valueOf(vQuantity))
                    .build();

            variantList.add(variant);
        }

        productDAO.insertProdAndVariant(product,variantList);
    }

    public void editProd(Integer id, String name) {

        Product product = Product.builder()
                .name(name)
                .build();

        productDAO.update(product, Long.valueOf(id));

    }


    public String[] getAllCategories(){
        List<Category> categoryList = categoryDAO.getAll();
        String[] categoryStringlist = new String[categoryList.size()];
        for (int i = 0; i < categoryList.size(); i++) {
            categoryStringlist[i] = String.valueOf(categoryList.get(i).getId());
            categoryStringlist[i] += ", " + categoryList.get(i).getName();
        }

        return categoryStringlist;
    }

    public void deleteItem(Integer id) {
        try{
            productDAO.deleteById(Long.valueOf(id));
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public List<String[]> variantNameAndPriceList(List<JPanel> variantInputFields){
        List<String[]> variantList = new ArrayList<>();

        for (int i = 0; i < variantInputFields.size(); i++) {
            JPanel panel = variantInputFields.get(i);

            JTextField textField1 = (JTextField) panel.getComponent(1);
            JTextField textField2 = (JTextField) panel.getComponent(3);
            JTextField textField3 = (JTextField) panel.getComponent(5);
            JTextField textField4 = (JTextField) panel.getComponent(7);

            String vName = textField1.getText();
            String vPrice = textField2.getText();
            String vBarcode = textField3.getText();
            String vQuantity = textField4.getText();

            String[] variantRow = {vName, vPrice, vBarcode, vQuantity};
            variantList.add(variantRow);
        }

        return variantList;
    }

    public DefaultTableModel cartListToCartTableModel(List<Cart> cartList) {
        String[] columns = {"Product","Variant","Unit Price","Quantity","Subtotal"};
        DefaultTableModel dtm = new DefaultTableModel(columns, 0);

        for(Cart cart : cartList){
            String pName = cart.getProductName();
            String vName = cart.getVariantName();
            String unitPrice = String.valueOf(cart.getUnitPrice());
            String quantity = String.valueOf(cart.getQuantity());
            Double total = cart.getSubTotal();

            String[] row = {pName,vName,unitPrice,quantity, String.valueOf(total)};
            dtm.addRow(row);
        }
        return dtm;
    }

    public Double getTotal(JTable cartTable) {
        Double totalAmount = 0D;

        for (int i = 0; i < cartTable.getModel().getRowCount(); i++) {
            Double convertedValue = Double.parseDouble((String) cartTable.getValueAt(i,4));
            totalAmount += convertedValue;
        }

        return totalAmount;
    }

    public String[][] getData(String barcode, JTable table) throws ItemNotFoundException, QuantityOverflowException{
        Cart c = productDAO.getProductWithVariantByBarcode(barcode);

        if(c == null){
            throw new ItemNotFoundException("Item not found");
        }

        String pName = c.getProductName();
        String vName = c.getVariantName();

        List<Cart> cartTableItems = convertCartTableToList(table);

        Boolean found = false;

        for (int i = 0; i < cartTableItems.size(); i++) {
            Cart tableItem = cartTableItems.get(i);

            if (tableItem.getProductName().equals(pName)
                    && tableItem.getVariantName().equals(vName)) {

                try{
                    Long quantityInStock = variantDAO.getQuantityInStock(tableItem.getProductName(),tableItem.getVariantName());

                    if(tableItem.getQuantity() < quantityInStock){

                        tableItem.setQuantity(tableItem.getQuantity() + 1);
                        tableItem.setSubTotal(tableItem.getUnitPrice() * tableItem.getQuantity());
                        cartTableItems.set(i, tableItem); // Update the item in the list
                        found = true;
                        break;

                    }
                    else{
                        throw new QuantityOverflowException("Quantity Exceeded, max no. of available items is: "+quantityInStock);
                    }
                }
                catch(SQLException e){
                    throw new RuntimeException(e);
                }
            }
        }

        if(!found){

            try{
                Long quantityInStock = variantDAO.getQuantityInStock(c.getProductName(),c.getVariantName());

                if(c.getQuantity() <= quantityInStock){

                    Double subTotal = c.getUnitPrice() * c.getQuantity();
                    c.setSubTotal(subTotal);

                    cartTableItems.add(c);

                }
                else{
                    throw new QuantityOverflowException("Quantity Exceeded, max no. of available items is: "+quantityInStock);
                }

            }catch (SQLException e){
                throw new RuntimeException(e);
            }

        }

        return convertCartListToTable(cartTableItems);
    }

    private String[][] convertCartListToTable(List<Cart> cartTableItems) {
        List<String[]> cartListItems = new ArrayList<>();

        for (Cart cartTableItem : cartTableItems) {
            String pName = cartTableItem.getProductName();
            String vName = cartTableItem.getVariantName();
            String unitPrice = String.valueOf(cartTableItem.getUnitPrice());
            String quantity = String.valueOf(cartTableItem.getQuantity());
            String subTotal = String.valueOf(cartTableItem.getSubTotal());

            String[] row = {pName, vName, unitPrice, quantity, subTotal};
            cartListItems.add(row);
        }

        String[][] cartTable = cartListItems.toArray(new String[0][0]);
        return cartTable;
    }

    private List<Cart> convertCartTableToList(JTable table) {
        List<Cart> cartListFromTable = new ArrayList<>();
        for (int i = 0; i < table.getRowCount(); i++) {
            String pName = String.valueOf(table.getValueAt(i,0));
            String vName = String.valueOf(table.getValueAt(i,1));
            String unitPrice = String.valueOf(table.getValueAt(i,2));
            String quantity = String.valueOf(table.getValueAt(i,3));
            String subTotal = String.valueOf(table.getValueAt(i,4));

            Cart c = Cart.builder()
                    .productName(pName)
                    .variantName(vName)
                    .unitPrice(Double.valueOf(unitPrice))
                    .quantity(Long.valueOf(quantity))
                    .subTotal(Double.valueOf(subTotal))
                    .build();

            cartListFromTable.add(c);
        }

        return cartListFromTable;
    }


    public Boolean checkQuantityExceeded(JTable cartTable, int selectedRow) throws QuantityOverflowException{
        try {
            Long quantitySelected = Long.valueOf(String.valueOf(cartTable.getValueAt(selectedRow,3)));
            Long quantityInStock = variantDAO.getQuantityInStock(
                    String.valueOf(cartTable.getValueAt(selectedRow,0)),
                    String.valueOf(cartTable.getValueAt(selectedRow,1))
            );

            if(quantitySelected >= quantityInStock){
                return true;
            }
            else{
                return false;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ItemNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public DefaultTableModel increaseQuantityByOne(JTable cartTable, int selectedRow) throws QuantityOverflowException {
        Boolean quantityExceeded = checkQuantityExceeded(cartTable, selectedRow);
        if(quantityExceeded){
            throw new QuantityOverflowException("Stock empty");
        }
        else{
            String newQuantity = String.valueOf(cartTable.getValueAt(selectedRow,3));
            DefaultTableModel dtm = (DefaultTableModel) cartTable.getModel();
            dtm.setValueAt(newQuantity,selectedRow,3);
            dtm.fireTableCellUpdated(selectedRow,3);
            return dtm;
        }
    }

    public Long getQuantityInStock(String pName, String vName){
        try {
            return variantDAO.getQuantityInStock(pName,vName);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ItemNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Variant> getAllVariantsWithProductId(String id) {
        List<Variant> variantList = new ArrayList<>();
        return variantDAO.getAllByProductId(Long.valueOf(id));
    }

    public void UpdateProdAndWithVariant(Integer prodId, String productName, List<Variant> variants) throws SQLException {
        productDAO.updateProductsAndVariants(prodId, productName, variants);
    }

}
