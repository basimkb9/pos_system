package org.possystem.service;

import org.possystem.dao.TransactionDetailDAO;
import org.possystem.dao.VariantDAO;
import org.possystem.domain.Cart;
import org.possystem.domain.TransactionDetail;
import org.possystem.domain.Variant;

import javax.swing.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SaleDetailsService {

    private final TransactionDetailDAO saleDetailsDAO = new TransactionDetailDAO();
    private final VariantDAO variantDAO = new VariantDAO();

    public boolean saveTransactionAndDetails(JTable cartTable, Double totalAmount) throws SQLException {
        List<Cart> cartList = savetoCart(cartTable);

        return saleDetailsDAO.saveTransactionAndDetails(cartList,totalAmount);
    }

    public List<Cart> savetoCart(JTable cartTable) {
        List<Cart> cartItems = new ArrayList<>();
        for (int i = 0; i < cartTable.getModel().getRowCount(); i++) {
            Cart item = Cart.builder()
                    .productName(String.valueOf(cartTable.getValueAt(i,0)))
                    .variantName(String.valueOf(cartTable.getValueAt(i,1)))
                    .unitPrice(Double.valueOf(String.valueOf(cartTable.getValueAt(i,2))))
                    .quantity(Long.valueOf(String.valueOf(cartTable.getValueAt(i,3))))
                    .subTotal(Double.valueOf(String.valueOf(cartTable.getValueAt(i,4))))
                    .build();

            item.setCostPrice(variantDAO.getCostPrice(item.getProductName(),item.getVariantName()));

            cartItems.add(item);
        }
        return cartItems;
    }


    public String[][] getAllInString() {
        List<String[]> transactionDetailList = saleDetailsDAO.getAllStringList();

        String[][] trDetailsArray = transactionDetailList.toArray(new String[0][0]);

        return trDetailsArray;
    }

    public void deleteItem(Integer id) {
        saleDetailsDAO.deleteById(Long.valueOf(id));
    }


    public List<Cart> saveToCart(JTable cartTable) {
        List<Cart> cartA = new ArrayList<>(); // will store data coming from table
        List<Cart> cartB = new ArrayList<>(); // will store data without duplicates (i.e. it will increase
                                        // the quantity of product instead of having same product more than once


        Integer rowCount = cartTable.getModel().getRowCount();

        for (int i = 0; i < rowCount; i++) {
            String prodName = String.valueOf(cartTable.getValueAt(i,0));
            String variantName = String.valueOf(cartTable.getValueAt(i,1));
            String price = String.valueOf(cartTable.getValueAt(i,2));

            Cart c = Cart.builder()
                    .productName(prodName)
                    .variantName(variantName)
                    .unitPrice(Double.valueOf(price))
                    .quantity(1L)
                    .build();

            Double subTotal = c.getUnitPrice() * c.getQuantity();
            c.setSubTotal(subTotal);

            cartA.add(c);
        }


        for (Cart itemA : cartA) {
            boolean found = false;

            for (Cart itemB : cartB) {
                if (itemA.getProductName().equals(itemB.getProductName()) &&
                        itemA.getVariantName().equals(itemB.getVariantName())) {
                    // Item already exists in List B, update quantity and subtotal
                    itemB.setQuantity(itemB.getQuantity() + 1);
                    itemB.setSubTotal(itemB.getUnitPrice() * itemB.getQuantity());
                    found = true;
                    break;
                }
            }

            if (!found) {
                // Item doesn't exist in List B, add it as a new item
                Cart newItem = Cart.builder()
                        .productName(itemA.getProductName())
                        .variantName(itemA.getVariantName())
                        .unitPrice(itemA.getUnitPrice())
                        .quantity(1L)
                        .subTotal(itemA.getUnitPrice() * itemA.getQuantity())
                        .build();

                cartB.add(newItem);
            }
        }

        return cartB;
    }

//    public List<Cart> toCartList(List<String[]> cartItems) {
//        List<Cart> cartObjectList = new ArrayList<>();
//
//        Cart c = Cart.builder()
//                .productName(cartItems.get(0)[0])
//                .variantName(cartItems.get(0)[1])
//                .unitPrice(Double.valueOf(cartItems.get(0)[2]))
//                .quantity(1l)
//                .build();
//
//        c.setSubTotal((c.getQuantity() * c.getUnitPrice()));
//
//        cartObjectList.add(c);
//
////        for (int i = 1; i < cartItems.size(); i++) {
////            String pName = cartItems.get(i)[0];
////            String vName = cartItems.get(i)[1];
////            String price = cartItems.get(i)[2];
////
////            if(alre)
////            for(Cart cart : cartObjectList){
////                if(cart.getProductName().equalsIgnoreCase(pName) == true
////                        && cart.getVariantName().equalsIgnoreCase(vName) == true){
////                    Long newQuantity = cart.getQuantity() + 1;
////                    Double newSubTotal = cart.getUnitPrice() * newQuantity;
////                    cart.setQuantity(newQuantity);
////                    cart.setSubTotal(newSubTotal);
////                    break;
////                }
////            }
////
////        }
////
////        return cartObjectList;
//
//        for (Cart cart : cartItems) {
//            boolean found = false;
//
//            for (Cart itemB : listB) {
//                if (itemA.getName().equals(itemB.getName()) &&
//                        itemA.getVariantName().equals(itemB.getVariantName())) {
//                    // Item already exists in List B, update quantity and subtotal
//                    itemB.setQuantity(itemB.getQuantity() + 1);
//                    itemB.setSubtotal(itemB.getUnitPrice() * itemB.getQuantity());
//                    found = true;
//                    break;
//                }
//            }
//
//            if (!found) {
//                // Item doesn't exist in List B, add it as a new item
//                Cart newItem = new Cart(itemA.getName(), itemA.getVariantName(),
//                        itemA.getUnitPrice(), 1,
//                        itemA.getUnitPrice()); // Initial quantity and subtotal
//                listB.add(newItem);
//            }
//        }
//
//    }
//


//    private Boolean addNoRepeat(List<Cart> cartItems, String[] values) {
//        try{
//
//            for(Cart c : cartItems){
//                if(c.getProductName().equalsIgnoreCase(values[0]) == true
//                && c.getVariantName().equalsIgnoreCase(values[1]) == true){
//                        Long newQuantity = c.getQuantity() + 1;
//                        Double newSubTotal = c.getUnitPrice() * newQuantity;
//                        c.setQuantity(newQuantity);
//                        c.setSubTotal(newSubTotal);
//
//                        return c;
//                }
//            }
//
//            Cart c = Cart.builder()
//                    .productName(values[0])
//                    .variantName(values[1])
//                    .unitPrice(Double.valueOf(values[2]))
//                    .quantity(1L)
//                    .build();
//
//            Double subTotal = c.getUnitPrice() * c.getQuantity();
//            c.setSubTotal(subTotal);
//
//            return c;
////            for (int i = 0; i < cartItems.size(); i++) {
////                if(cartItems.get(i).getProductName().equalsIgnoreCase(values[0])
////                        && cartItems.get(i).getVariantName().equalsIgnoreCase(values[1])){
////                    Long increaseQuantity = cartItems.get(i).getQuantity() + 1L;
////                    Double increaseSubTotal = cartItems.get(i).getSubTotal() * increaseQuantity;
////                    cartItems.get(i).setQuantity(increaseQuantity);
////                    cartItems.get(i).setSubTotal(increaseSubTotal);
////                    return cartItems.get(i);
////                }
////            }
////
////            Cart c = Cart.builder()
////                    .productName(values[0])
////                    .variantName(values[1])
////                    .unitPrice(Double.valueOf(values[2]))
////                    .quantity(1L)
////                    .build();
////
////            c.setSubTotal((c.getQuantity() * c.getUnitPrice()));
////
////            return c;
//        }catch(Exception e){
//            throw new RuntimeException(e);
//        }
//    }

    private Cart checkIfContains(List<Cart> cartObjectList, String productName, String variantName) {
        for (int i = 0; i < cartObjectList.size(); i++) {
            if(cartObjectList.get(i).getProductName().equalsIgnoreCase(productName)
                    && cartObjectList.get(i).getVariantName().equalsIgnoreCase(variantName)){
                cartObjectList.get(i).setQuantity(cartObjectList.get(i).getQuantity()+1L);
            }
        }

        for(Cart c : cartObjectList){
            if(c.getProductName().equalsIgnoreCase(productName)
                    && c.getVariantName().equalsIgnoreCase(variantName)){
                Long updatedQuantity = c.getQuantity() + 1L;
                c.setQuantity(updatedQuantity);

                return c;
            }
        }

        return null;
    }

    public Boolean updateQuantities(JTable cartTable) {
        List<Cart> cartList = savetoCart(cartTable);

        return variantDAO.updateQuantities(cartList);
    }
}
