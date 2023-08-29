package org.possystem.ui;

import jdk.nashorn.internal.scripts.JO;
import org.possystem.domain.Cart;
import org.possystem.service.ProductService;
import org.possystem.service.SaleDetailsService;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.List;

public class SaleCompletionUI {
    private final SaleDetailsService saleDetailsService = new SaleDetailsService();

    public SaleCompletionUI(Double totalAmount, JTable cartTable, JFrame mainScreen) {
        JFrame mainFrame = new JFrame("Receive Payment");
        mainFrame.setLayout(new BorderLayout());

        JPanel billingPanel = new JPanel(new BorderLayout());
        String convertedTotalAmount = String.valueOf(totalAmount);
        JLabel total = new JLabel("Total Amount = " + convertedTotalAmount);
        JLabel cashRecvdLbl = new JLabel("Enter Cash Received");
        JTextField cashRecvdTF = new JTextField(15);
        JPanel cashInputPnl = new JPanel(new FlowLayout(FlowLayout.CENTER));
        cashInputPnl.add(cashRecvdLbl);
        cashInputPnl.add(cashRecvdTF);

        JLabel changeLbl = new JLabel();
        changeLbl.setLayout(new FlowLayout(FlowLayout.CENTER));
        JPanel cashExchangePnl = new JPanel(new GridLayout(2,1));
        cashExchangePnl.add(cashInputPnl);
        cashExchangePnl.add(changeLbl);

        billingPanel.add(total,BorderLayout.NORTH);
        billingPanel.add(cashInputPnl,BorderLayout.CENTER);
        billingPanel.add(cashExchangePnl,BorderLayout.SOUTH);

        JPanel bottomOptions = new JPanel(new FlowLayout(FlowLayout.CENTER));

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton saveBtn = createButton("SAVE");
        JButton goBackBtn = createButton("BACK");
        buttonsPanel.add(saveBtn);
        buttonsPanel.add(goBackBtn);

        bottomOptions.add(buttonsPanel);

        cashRecvdTF.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {
                if(!cashRecvdTF.getText().isEmpty()){
                    Double amountRecvd = Double.valueOf(cashRecvdTF.getText());
                    if(amountRecvd > totalAmount){
                        Double returnCash = amountRecvd-totalAmount;
                        changeLbl.setText("You need to return: " + returnCash +"rs");
                    }
                    else{
                        changeLbl.setText("You need to return: 0rs");
                    }
                }
            }
        });

        goBackBtn.addActionListener(e -> {
            mainFrame.dispose();
        });

        saveBtn.addActionListener(e -> {
            try {
                Boolean checkTransactionCompleted = saleDetailsService.saveTransactionAndDetails(cartTable,totalAmount);
                if(checkTransactionCompleted == true){
                    boolean checkUpdate = saleDetailsService.updateQuantities(cartTable);
                    if(checkUpdate){
                        JOptionPane.showMessageDialog(mainFrame, "Transaction Completed");
                        mainFrame.dispose();
                        setDefaultModel(cartTable);
                    }
                    else{
                        JOptionPane.showMessageDialog(mainFrame,"Something went wrong");
                    }
                }
                else{
                    JOptionPane.showMessageDialog(mainFrame, "Some Error Occurred");
                    mainFrame.dispose();
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(mainFrame,ex.getMessage());
            }
            catch (RuntimeException ex){
                JOptionPane.showMessageDialog(mainFrame,ex.getMessage());
            }

        });

        mainFrame.add(billingPanel);
        mainFrame.add(bottomOptions,BorderLayout.SOUTH);
        mainFrame.pack();
        mainFrame.setVisible(true);
        mainFrame.setSize(500,200);
        mainFrame.setResizable(false);
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFrame.setLocationRelativeTo(null);
    }

    private void setDefaultModel(JTable cartTable) {
        String[] columnNames = {"Product","Variant","Price","Quantity","Sub-total"};
        DefaultTableModel dtm = new DefaultTableModel(null, columnNames);
        cartTable.setModel(dtm);
    }

    private JButton createButton(String btnText) {
        JButton button = new JButton(btnText);
        return button;
    }

    private JPanel getTotal(List<Cart> cartItems) {
        JPanel totalPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        Double totalAmount = 0D;

        for (Cart cartItem : cartItems) {
            totalAmount += cartItem.getSubTotal();
        }

        totalPanel.add(createLabel("Total Amount: "));
        totalPanel.add(createLabel(String.valueOf(totalAmount)));

        return totalPanel;
    }

    private JLabel createLabel(String labelText) {
        JLabel label = new JLabel();
        label.setText(labelText);

        return label;
    }


}
