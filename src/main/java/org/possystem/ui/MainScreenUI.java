package org.possystem.ui;

import com.sun.javafx.geom.transform.BaseTransform;
import com.sun.org.apache.xpath.internal.operations.Bool;
import jdk.nashorn.internal.scripts.JO;
import org.possystem.domain.Cart;
import org.possystem.service.ItemNotFoundException;
import org.possystem.service.ProductService;
import org.possystem.service.QuantityOverflowException;
import org.possystem.service.SaleDetailsService;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.text.Document;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MainScreenUI {
    private final ProductService productService = new ProductService();
    static Double totalAmount = 0D;
//    public MainScreenUI(){
//        JFrame productScreen = new JFrame("Sale | POS System");
//        productScreen.setLayout(new GridLayout(1,2,10,20));
//
//        JPanel col1 = new JPanel(new BorderLayout());
//        JPanel col2 = new JPanel(new BorderLayout());
//
//        //region searchbox
//        JPanel topOptions = new JPanel();
//        JTextField searchInput = new JTextField(30);
//        topOptions.add(searchInput);
//        col1.add(topOptions,BorderLayout.NORTH);
//        //endregion searchbox
//
//        //region defining display table
//        String[][] data = productService.getAllProductsWithVariants();
//
//        String[] columnNames = {"Product Name", "Variant", "Unit Price",""};
//
//        DefaultTableModel model = new DefaultTableModel(data, columnNames){
//            @Override
//            public boolean isCellEditable(int row, int column) {
//                return false; // Make the entire table uneditable
//            }
//        };
//        JTable table = new JTable(model);
//        //endregion
//
//        JScrollPane scrollPane = new JScrollPane(table);
//
//        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
//
//        col1.add(scrollPane,BorderLayout.CENTER);
//        productScreen.add(col1,BorderLayout.CENTER);
//
//        //====================================================================================
//        //COLUMN 2
//        //====================================================================================
//
//        JButton removeBtn = new JButton("REMOVE");
//        JButton increaseQuantity = new JButton("+");
//        JButton decreaseQuantity = new JButton("-");
//        JLabel setQuantityLbl = new JLabel("Set Quantity: ");
//        JTextField setQuantityTF = new JTextField(10);
//        JButton saveQuantityBtn = new JButton("SET");
//
//        JPanel bottomOptions = new JPanel(new GridLayout(2,1));
//        JPanel bottomOptionsBtns = new JPanel(new FlowLayout(FlowLayout.CENTER));
//        JButton backBtn = new JButton("BACK");
//        JButton saveBtn = new JButton("SAVE");
//        bottomOptionsBtns.add(saveBtn);
//        bottomOptionsBtns.add(backBtn);
//
//        //region Cart table initialization
//        String[] cartColumns = {"Product","Variant","Unit Price","Quantity","Subtotal"};
//
//        DefaultTableModel dtm = new DefaultTableModel(cartColumns,0){
//            @Override
//            public boolean isCellEditable(int row, int column) {
//                return false; // Make the entire table uneditable
//            }
//        };
//        JTable cartTable = new JTable(dtm);
//        cartTable.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
//        //endregion
//
//        //region setting totalPrice section and print/save buttons
//        JPanel totalPnl = new JPanel(new BorderLayout());
//        JLabel totalAmountLabel = new JLabel();
//        totalAmountLabel.setText("Total Price: ");
//        totalAmountLabel.setFont(new Font("Calibri",Font.BOLD,20));
//        JLabel calcAmount = new JLabel();
//        calcAmount.setText("0");
//        calcAmount.setFont(new Font("Calibri",Font.BOLD,20));
//        calcAmount.setForeground(Color.BLUE);
//        totalPnl.add(totalAmountLabel,BorderLayout.WEST);
//        totalPnl.add(calcAmount, BorderLayout.EAST);
//        //endregion
//
//        //region search textfield listener
//        Timer searchTimer;
//        searchTimer = new Timer(200, new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                if(!searchInput.getText().isEmpty()) {
//                    String[][] cartdata = new String[0][];
//                    try {
//                        cartdata = productService.getData(searchInput.getText(), cartTable);
//                        DefaultTableModel updatedModel = new DefaultTableModel(cartdata, cartColumns);
//                        updatedModel.fireTableDataChanged();
//                        cartTable.setModel(updatedModel);
//                        totalAmount = productService.getTotal(cartTable);
//                        calcAmount.setText(String.valueOf(totalAmount));
//                    } catch (ItemNotFoundException ex) {
//                        JOptionPane.showMessageDialog(productScreen, ex.getMessage());
//                    }
//                    catch (QuantityOverflowException ex){
//                        JOptionPane.showMessageDialog(productScreen, ex.getMessage());
//                    }
//                    searchInput.setText("");
//                    searchInput.requestFocus();
//                }
//            }
//        });
//        searchTimer.setRepeats(false); // Fire the timer only once
//
//        searchInput.addKeyListener(new KeyListener() {
//            @Override
//            public void keyTyped(KeyEvent e) {
//                // Not used in this example
//            }
//
//            @Override
//            public void keyPressed(KeyEvent e) {
//                // Not used in this example
//            }
//
//            @Override
//            public void keyReleased(KeyEvent e) {
//                // Start the timer when a key is released
//                searchTimer.restart();
//            }
//        });
//        //endregion
//
//        //region Buttons Listeners
//        removeBtn.addActionListener(e -> {
//            int selectedRow = cartTable.getSelectedRow();
//            System.out.println(selectedRow);
//            if (selectedRow > -1) {
//                DefaultTableModel getModel = (DefaultTableModel) cartTable.getModel();
//                getModel.removeRow(selectedRow);
//                totalAmount = productService.getTotal(cartTable);
//                calcAmount.setText(String.valueOf(totalAmount));
//            }
//            else{
//                JOptionPane.showMessageDialog(productScreen,"Please Select a Row");
//            }
//
//            searchInput.requestFocus();
//        });
//
//        increaseQuantity.addActionListener(e -> {
//            int selectedRow = cartTable.getSelectedRow();
//            if(selectedRow > -1){
//                try {
//                    Boolean checkQuantityExceeds = productService.checkQuantityExceeded(cartTable,selectedRow);
//
//                    if(checkQuantityExceeds){
//                        throw new QuantityOverflowException("Stock is now empty");
//                    }
//                    else{
//                        int quantityColumnIndex = 3;
//                        int increment = 1;
//
//                        String currentQuantityStr = String.valueOf(cartTable.getValueAt(selectedRow, quantityColumnIndex));
//                        int currentQuantity = Integer.parseInt(currentQuantityStr);
//                        int newQuantity = currentQuantity + increment;
//
//                        cartTable.setValueAt(newQuantity, selectedRow, quantityColumnIndex);
//
//                        Double unitPrice = Double.parseDouble((String) cartTable.getValueAt(selectedRow, 2));
//                        Double subtotal = newQuantity * unitPrice;
//                        cartTable.setValueAt(String.valueOf(subtotal), selectedRow, 4);
//
//                        totalAmount += unitPrice;
//                        calcAmount.setText(String.valueOf(totalAmount));
//                    }
//                } catch (QuantityOverflowException ex) {
//                    JOptionPane.showMessageDialog(productScreen, ex.getMessage());
//                }
//            }
//            else{
//                JOptionPane.showMessageDialog(productScreen,"Please Select a Row");
//            }
//
//            searchInput.requestFocus();
//        });
//
//        decreaseQuantity.addActionListener(e -> {
//            int selectedRow = cartTable.getSelectedRow();
//
//            if(selectedRow > -1){
//                int quantityColumnIndex = 3;
//                int decrement = 1;
//
//                int currentQuantity = Integer.parseInt(String.valueOf(cartTable.getValueAt(selectedRow, quantityColumnIndex)));
//
//                if(currentQuantity > 1){
//                    int newQuantity = currentQuantity - decrement;
//
//                    cartTable.setValueAt(newQuantity, selectedRow, quantityColumnIndex);
//
//                    Double unitPrice = Double.parseDouble((String) cartTable.getValueAt(selectedRow, 2));
//                    Double subtotal = newQuantity * unitPrice;
//                    cartTable.setValueAt(String.valueOf(subtotal), selectedRow, 4);
//
//                    totalAmount -= unitPrice;
//                    calcAmount.setText(String.valueOf(totalAmount));
//                }
//                else{
//                    DefaultTableModel getModel = (DefaultTableModel) cartTable.getModel();
//                    getModel.removeRow(selectedRow);
//                    totalAmount = productService.getTotal(cartTable);
//                    calcAmount.setText(String.valueOf(totalAmount));
//                }
//            }
//            else{
//                JOptionPane.showMessageDialog(productScreen,"Please Select a Row");
//            }
//
//            searchInput.requestFocus();
//        });
//
//        saveQuantityBtn.addActionListener(e -> {
//            int selectedRow = cartTable.getSelectedRow();
//            System.out.println(selectedRow);
//            if (selectedRow > -1) {
//                String quantityStr = setQuantityTF.getText();
//                Long newQuantity = Long.valueOf(quantityStr);
//
//                Long quantityInStock = productService.getQuantityInStock(
//                        String.valueOf(cartTable.getValueAt(selectedRow,0)),
//                        String.valueOf(cartTable.getValueAt(selectedRow,1))
//                );
//
//                if(newQuantity > quantityInStock){
//                    JOptionPane.showMessageDialog(productScreen,"Only " + quantityInStock + " total items in Stock.");
//                }
//                else{
//                    if(newQuantity <= 0){
//                        DefaultTableModel getModel = (DefaultTableModel) cartTable.getModel();
//                        getModel.removeRow(selectedRow);
//                        totalAmount = productService.getTotal(cartTable);
//                        calcAmount.setText(String.valueOf(totalAmount));
//                    }
//                    else{
//                        int quantityColumnIndex = 3;
//                        cartTable.setValueAt(newQuantity, selectedRow, quantityColumnIndex);
//
//                        Double unitPrice = Double.parseDouble((String) cartTable.getValueAt(selectedRow, 2));
//                        Double subtotal = newQuantity * unitPrice;
//                        cartTable.setValueAt(String.valueOf(subtotal), selectedRow, 4);
//
//                        totalAmount += unitPrice;
//                        calcAmount.setText(String.valueOf(totalAmount));
//                    }
//                }
//            }
//            else{
//                JOptionPane.showMessageDialog(productScreen,"Please Select a Row");
//            }
//            searchInput.requestFocus();
//        });
//
//        saveBtn.addActionListener(e -> {
//            new SaleCompletionUI(totalAmount, cartTable, productScreen);
//            totalAmount = productService.getTotal(cartTable);
//            setQuantityTF.setText("");
//            searchInput.setText("");
//            searchInput.requestFocus();
//        });
//
//        backBtn.addActionListener(e -> {
//            productScreen.dispose();
//            new NavUI();
//        });
//        //endregion
//
//        JScrollPane cartSP = new JScrollPane(cartTable);
//
//        //region adding elements
//        JPanel quantityPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
//        quantityPanel.add(increaseQuantity);
//        quantityPanel.add(decreaseQuantity);
//        quantityPanel.add(setQuantityLbl);
//        quantityPanel.add(setQuantityTF);
//        quantityPanel.add(saveQuantityBtn);
//
//        JPanel topOptionsRight = new JPanel(new FlowLayout(FlowLayout.CENTER));
//        topOptionsRight.add(removeBtn);
//        topOptionsRight.add(quantityPanel);
//        bottomOptions.add(totalPnl);
//        bottomOptions.add(bottomOptionsBtns);
//        col2.add(topOptionsRight,BorderLayout.NORTH);
//        col2.add(cartSP,BorderLayout.CENTER);
//        col2.add(bottomOptions, BorderLayout.SOUTH);
//        productScreen.add(col2);
//        //endregion
//
//        productScreen.pack();
//        productScreen.setVisible(true);
//        productScreen.setSize(1200,700);
//        productScreen.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
//        productScreen.setLocationRelativeTo(null);
//
//    }

    public MainScreenUI(String role){
        JFrame productScreen = new JFrame("Sale | POS System");
        productScreen.setLayout(new GridLayout(1,2,10,20));

        JPanel col1 = new JPanel(new BorderLayout());
        JPanel col2 = new JPanel(new BorderLayout());

        //region searchbox
        JPanel topOptions = new JPanel();
        JTextField searchInput = new JTextField(30);
        topOptions.add(searchInput);
        col1.add(topOptions,BorderLayout.NORTH);
        //endregion searchbox

        //region defining display table
        String[][] data = productService.getAllProductsWithVariants();

        String[] columnNames = {"Product Name", "Variant", "Unit Price",""};

        DefaultTableModel model = new DefaultTableModel(data, columnNames){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make the entire table uneditable
            }
        };
        JTable table = new JTable(model);
        //endregion

        JScrollPane scrollPane = new JScrollPane(table);

        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        col1.add(scrollPane,BorderLayout.CENTER);
        productScreen.add(col1,BorderLayout.CENTER);

        //====================================================================================
        //COLUMN 2
        //====================================================================================

        JButton removeBtn = new JButton("REMOVE");
        JButton increaseQuantity = new JButton("+");
        JButton decreaseQuantity = new JButton("-");
        JLabel setQuantityLbl = new JLabel("Set Quantity: ");
        JTextField setQuantityTF = new JTextField(10);
        JButton saveQuantityBtn = new JButton("SET");

        JPanel bottomOptions = new JPanel(new GridLayout(2,1));
        JPanel bottomOptionsBtns = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton backBtn = new JButton("BACK");
        JButton saveBtn = new JButton("SAVE");
        JButton logoutBtn = new JButton("LOGOUT");
        bottomOptionsBtns.add(saveBtn);

        if(role=="administrator"){
            bottomOptionsBtns.add(backBtn);
        }
        else{
            bottomOptionsBtns.add(logoutBtn);
        }

        //region Cart table initialization
        String[] cartColumns = {"Product","Variant","Unit Price","Quantity","Subtotal"};

        DefaultTableModel dtm = new DefaultTableModel(cartColumns,0){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make the entire table uneditable
            }
        };
        JTable cartTable = new JTable(dtm);
        cartTable.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        //endregion

        //region setting totalPrice section and print/save buttons
        JPanel totalPnl = new JPanel(new BorderLayout());
        JLabel totalAmountLabel = new JLabel();
        totalAmountLabel.setText("Total Price: ");
        totalAmountLabel.setFont(new Font("Calibri",Font.BOLD,20));
        JLabel calcAmount = new JLabel();
        calcAmount.setText("0");
        calcAmount.setFont(new Font("Calibri",Font.BOLD,20));
        calcAmount.setForeground(Color.BLUE);
        totalPnl.add(totalAmountLabel,BorderLayout.WEST);
        totalPnl.add(calcAmount, BorderLayout.EAST);
        //endregion

        //region search textfield listener
        Timer searchTimer;
        searchTimer = new Timer(200, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!searchInput.getText().isEmpty()) {
                    String[][] cartdata = new String[0][];
                    try {
                        cartdata = productService.getData(searchInput.getText(), cartTable);
                        DefaultTableModel updatedModel = new DefaultTableModel(cartdata, cartColumns);
                        updatedModel.fireTableDataChanged();
                        cartTable.setModel(updatedModel);
                        totalAmount = productService.getTotal(cartTable);
                        calcAmount.setText(String.valueOf(totalAmount));
                    } catch (ItemNotFoundException ex) {
                        JOptionPane.showMessageDialog(productScreen, ex.getMessage());
                    }
                    catch (QuantityOverflowException ex){
                        JOptionPane.showMessageDialog(productScreen, ex.getMessage());
                    }
                    searchInput.setText("");
                    searchInput.requestFocus();
                }
            }
        });
        searchTimer.setRepeats(false); // Fire the timer only once

        searchInput.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                // Not used in this example
            }

            @Override
            public void keyPressed(KeyEvent e) {
                // Not used in this example
            }

            @Override
            public void keyReleased(KeyEvent e) {
                // Start the timer when a key is released
                searchTimer.restart();
            }
        });
        //endregion

        //region Buttons Listeners
        removeBtn.addActionListener(e -> {
            int selectedRow = cartTable.getSelectedRow();
            System.out.println(selectedRow);
            if (selectedRow > -1) {
                DefaultTableModel getModel = (DefaultTableModel) cartTable.getModel();
                getModel.removeRow(selectedRow);
                totalAmount = productService.getTotal(cartTable);
                calcAmount.setText(String.valueOf(totalAmount));
            }
            else{
                JOptionPane.showMessageDialog(productScreen,"Please Select a Row");
            }

            searchInput.requestFocus();
        });

        increaseQuantity.addActionListener(e -> {
            int selectedRow = cartTable.getSelectedRow();
            if(selectedRow > -1){
                try {
                    Boolean checkQuantityExceeds = productService.checkQuantityExceeded(cartTable,selectedRow);

                    if(checkQuantityExceeds){
                        throw new QuantityOverflowException("Stock is now empty");
                    }
                    else{
                        int quantityColumnIndex = 3;
                        int increment = 1;

                        String currentQuantityStr = String.valueOf(cartTable.getValueAt(selectedRow, quantityColumnIndex));
                        int currentQuantity = Integer.parseInt(currentQuantityStr);
                        int newQuantity = currentQuantity + increment;

                        cartTable.setValueAt(newQuantity, selectedRow, quantityColumnIndex);

                        Double unitPrice = Double.parseDouble((String) cartTable.getValueAt(selectedRow, 2));
                        Double subtotal = newQuantity * unitPrice;
                        cartTable.setValueAt(String.valueOf(subtotal), selectedRow, 4);

                        totalAmount += unitPrice;
                        calcAmount.setText(String.valueOf(totalAmount));
                    }
                } catch (QuantityOverflowException ex) {
                    JOptionPane.showMessageDialog(productScreen, ex.getMessage());
                }
            }
            else{
                JOptionPane.showMessageDialog(productScreen,"Please Select a Row");
            }

            searchInput.requestFocus();
        });

        decreaseQuantity.addActionListener(e -> {
            int selectedRow = cartTable.getSelectedRow();

            if(selectedRow > -1){
                int quantityColumnIndex = 3;
                int decrement = 1;

                int currentQuantity = Integer.parseInt(String.valueOf(cartTable.getValueAt(selectedRow, quantityColumnIndex)));

                if(currentQuantity > 1){
                    int newQuantity = currentQuantity - decrement;

                    cartTable.setValueAt(newQuantity, selectedRow, quantityColumnIndex);

                    Double unitPrice = Double.parseDouble((String) cartTable.getValueAt(selectedRow, 2));
                    Double subtotal = newQuantity * unitPrice;
                    cartTable.setValueAt(String.valueOf(subtotal), selectedRow, 4);

                    totalAmount -= unitPrice;
                    calcAmount.setText(String.valueOf(totalAmount));
                }
                else{
                    DefaultTableModel getModel = (DefaultTableModel) cartTable.getModel();
                    getModel.removeRow(selectedRow);
                    totalAmount = productService.getTotal(cartTable);
                    calcAmount.setText(String.valueOf(totalAmount));
                }
            }
            else{
                JOptionPane.showMessageDialog(productScreen,"Please Select a Row");
            }

            searchInput.requestFocus();
        });

        saveQuantityBtn.addActionListener(e -> {
            int selectedRow = cartTable.getSelectedRow();
            System.out.println(selectedRow);
            if (selectedRow > -1) {
                String quantityStr = setQuantityTF.getText();
                Long newQuantity = Long.valueOf(quantityStr);

                Long quantityInStock = productService.getQuantityInStock(
                        String.valueOf(cartTable.getValueAt(selectedRow,0)),
                        String.valueOf(cartTable.getValueAt(selectedRow,1))
                );

                if(newQuantity > quantityInStock){
                    JOptionPane.showMessageDialog(productScreen,"Only " + quantityInStock + " total items in Stock.");
                }
                else{
                    if(newQuantity <= 0){
                        DefaultTableModel getModel = (DefaultTableModel) cartTable.getModel();
                        getModel.removeRow(selectedRow);
                        totalAmount = productService.getTotal(cartTable);
                        calcAmount.setText(String.valueOf(totalAmount));
                    }
                    else{
                        int quantityColumnIndex = 3;
                        cartTable.setValueAt(newQuantity, selectedRow, quantityColumnIndex);

                        Double unitPrice = Double.parseDouble((String) cartTable.getValueAt(selectedRow, 2));
                        Double subtotal = newQuantity * unitPrice;
                        cartTable.setValueAt(String.valueOf(subtotal), selectedRow, 4);

                        totalAmount += unitPrice;
                        calcAmount.setText(String.valueOf(totalAmount));
                    }
                }
            }
            else{
                JOptionPane.showMessageDialog(productScreen,"Please Select a Row");
            }
            searchInput.requestFocus();
        });

        saveBtn.addActionListener(e -> {
            new SaleCompletionUI(totalAmount, cartTable, productScreen);
            totalAmount = productService.getTotal(cartTable);
            setQuantityTF.setText("");
            searchInput.setText("");
            searchInput.requestFocus();
        });

        backBtn.addActionListener(e -> {
            productScreen.dispose();
            new NavUI(role);
        });

        logoutBtn.addActionListener(e -> {
            productScreen.dispose();
            new LoginUI();
        });
        //endregion

        JScrollPane cartSP = new JScrollPane(cartTable);

        //region adding elements
        JPanel quantityPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        quantityPanel.add(increaseQuantity);
        quantityPanel.add(decreaseQuantity);
        quantityPanel.add(setQuantityLbl);
        quantityPanel.add(setQuantityTF);
        quantityPanel.add(saveQuantityBtn);

        JPanel topOptionsRight = new JPanel(new FlowLayout(FlowLayout.CENTER));
        topOptionsRight.add(removeBtn);
        topOptionsRight.add(quantityPanel);
        bottomOptions.add(totalPnl);
        bottomOptions.add(bottomOptionsBtns);
        col2.add(topOptionsRight,BorderLayout.NORTH);
        col2.add(cartSP,BorderLayout.CENTER);
        col2.add(bottomOptions, BorderLayout.SOUTH);
        productScreen.add(col2);
        //endregion

        productScreen.pack();
        productScreen.setVisible(true);
        productScreen.setSize(1200,700);
        productScreen.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        productScreen.setLocationRelativeTo(null);

    }

    public DefaultTableModel addToCart(DefaultTableModel cartTable, String barcode) throws ItemNotFoundException {
        Boolean found = false;
        List<Cart> cartListFromTable = new ArrayList<>();

        Cart cart = productService.getByBarcode(barcode);

        int rowCount = cartTable.getRowCount();

        for (int i = 0; i < rowCount; i++) {
            Cart c = Cart.builder()
                    .productName(String.valueOf(cartTable.getValueAt(i,0)))
                    .variantName(String.valueOf(cartTable.getValueAt(i,1)))
                    .unitPrice(Double.valueOf((String) cartTable.getValueAt(i,2)))
                    .quantity(Long.valueOf((String) cartTable.getValueAt(i,3)))
                    .build();

            Double subTotal = c.getQuantity() * c.getUnitPrice();
            c.setSubTotal(subTotal);

            cartListFromTable.add(c);
        }

        for (int i = 0; i < cartListFromTable.size(); i++) {
            Cart tableItem = cartListFromTable.get(i);
            if (tableItem.getProductName().equals(cart.getProductName())
                    && tableItem.getVariantName().equals(cart.getVariantName())) {
                tableItem.setQuantity(tableItem.getQuantity() + 1);
                tableItem.setSubTotal(tableItem.getUnitPrice() * tableItem.getQuantity());
                cartListFromTable.set(i, tableItem); // Update the item in the list
                found = true;
                break;
            }
        }

        if (!found) {
            cartListFromTable.add(cart);
        }

        return productService.cartListToCartTableModel(cartListFromTable);
    }

}
