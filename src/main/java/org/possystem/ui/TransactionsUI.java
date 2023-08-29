package org.possystem.ui;

import org.possystem.service.TransactionService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class TransactionsUI {
    TransactionService trService = new TransactionService();
    public TransactionsUI(){
        JFrame trMainFrame = new JFrame("Transactions");
        trMainFrame.setLayout(new BorderLayout());

        JPanel topOptions = new JPanel(new FlowLayout(FlowLayout.LEFT));

        String[][] data = trService.getAllTransactions();

        String[] columns = {"ID","TRANSACTION DATE","TOTAL AMOUNT","PAYMENT METHOD"};

        DefaultTableModel dtm = new DefaultTableModel(data,columns);
        JTable jt = new JTable(dtm);

        JScrollPane scrollPane = new JScrollPane(jt);

        trMainFrame.add(scrollPane,BorderLayout.CENTER);

        JButton deleteBtn = new JButton("Delete");
        JButton backBtn = new JButton("Back");

        deleteBtn.addActionListener(e -> {
            if(jt.getSelectedRow()>=0){
                String id = (String) jt.getValueAt(jt.getSelectedRow(),0);
                trService.deleteItem(Integer.valueOf(id));
                JOptionPane.showMessageDialog(jt,"Successfully Deleted");
                new TransactionsUI();
            }
        });

        backBtn.addActionListener(e -> {
            trMainFrame.dispose();
            new NavUI("admin");
        });


        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        topPanel.add(deleteBtn);
        topPanel.add(backBtn);

        trMainFrame.add(topPanel,BorderLayout.NORTH);
        trMainFrame.pack();
        trMainFrame.setVisible(true);
        trMainFrame.setSize(700,500);
        trMainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        trMainFrame.setLocationRelativeTo(null);

    }
}
