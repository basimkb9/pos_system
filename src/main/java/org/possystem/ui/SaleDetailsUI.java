package org.possystem.ui;

import org.possystem.service.SaleDetailsService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class SaleDetailsUI {
    private final SaleDetailsService saleDetailService = new SaleDetailsService();

    public SaleDetailsUI(){
        JFrame mainFrame = new JFrame("Sale Details");
        mainFrame.setLayout(new BorderLayout());

        //--------------------------------------
        //TOP OPTIONS
        //--------------------------------------

        JPanel topOptions = new JPanel();
        topOptions.setLayout(new FlowLayout(FlowLayout.CENTER));

        JButton deleteBtn = new JButton("DELETE");
        JButton backBtn = new JButton("BACK");

        topOptions.add(deleteBtn);
        topOptions.add(backBtn);


        //--------------------------------------
        //TABLE
        //--------------------------------------

        String[][] data = saleDetailService.getAllInString();
        String[] columns = {"ID", "TRANSACTION_ID", "PRODUCT_ID", "UNIT PRICE", "QUANTITY SOLD", "SUBTOTAL", "COST PRICE", "PROFIT"};

        DefaultTableModel dtm = new DefaultTableModel(data,columns);
        JTable jt = new JTable(dtm);

        JScrollPane sp = new JScrollPane(jt);

        //--------------------------------------
        //BUTTON ACTIONS
        //--------------------------------------
        deleteBtn.addActionListener(e -> {
            if(jt.getSelectedRow()>=0){
                String id = (String) jt.getValueAt(jt.getSelectedRow(),0);
                saleDetailService.deleteItem(Integer.valueOf(id));
                JOptionPane.showMessageDialog(jt,"Successfully Deleted");
                mainFrame.dispose();
                new SaleDetailsUI();
            }
        });

        backBtn.addActionListener(e -> {
            mainFrame.dispose();
            new NavUI("admin");
        });


        mainFrame.add(topOptions,BorderLayout.NORTH);
        mainFrame.add(sp,BorderLayout.CENTER);

        mainFrame.pack();
        mainFrame.setVisible(true);
        mainFrame.setSize(1200,700);
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFrame.setLocationRelativeTo(null);
    }
}
