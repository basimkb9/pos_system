package org.possystem.ui;

import com.toedter.calendar.*;
import org.possystem.service.TransactionService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;

public class TransactionsUI {
    TransactionService trService = new TransactionService();
    public TransactionsUI(){
        JFrame trMainFrame = new JFrame("Transactions");
        trMainFrame.setLayout(new BorderLayout());

        JPanel datePanel = new JPanel();
        JDateChooser startDateChooser = new JDateChooser();
        JDateChooser endDateChooser = new JDateChooser();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        startDateChooser.setDateFormatString("yyyy/MM/dd");
        endDateChooser.setDateFormatString("yyyy/MM/dd");
        datePanel.add(new JLabel("Start Date: "));
        datePanel.add(startDateChooser);
        datePanel.add(new JLabel("End Date: "));
        datePanel.add(endDateChooser);

        JButton searchBtn = new JButton("SEARCH");
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        searchPanel.add(datePanel);
        searchPanel.add(searchBtn);

//        searchPanel.add(datePanel, BorderLayout.NORTH);

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
            new NavUI("administrator");
        });

        searchBtn.addActionListener(e -> {
            if(startDateChooser.getDate()==null && endDateChooser.getDate()==null){
                jt.setModel(dtm);
            }
            else{
                String[][] searchData = trService.getByDateRange(startDateChooser, endDateChooser);
                DefaultTableModel newDtm = new DefaultTableModel(searchData,columns);
                jt.setModel(newDtm);
            }
        });

        JPanel otherButtons = new JPanel(new FlowLayout(FlowLayout.CENTER));
        otherButtons.add(deleteBtn);
        otherButtons.add(backBtn);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(searchPanel,BorderLayout.WEST);
        topPanel.add(otherButtons,BorderLayout.EAST);
//        topPanel.add(backBtn);

        trMainFrame.add(topPanel,BorderLayout.NORTH);
        trMainFrame.pack();
        trMainFrame.setVisible(true);
        trMainFrame.setSize(700,500);
        trMainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        trMainFrame.setLocationRelativeTo(null);

    }
}
