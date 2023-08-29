package org.possystem.ui;

import org.possystem.service.CategoryService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class CategoryUI {
    private final CategoryService categoryService = new CategoryService();
    public CategoryUI(){
        JFrame mainFrame = new JFrame("Category Management | POS System");
        mainFrame.setLayout(new GridLayout(1,2,10,20));

        JPanel col1 = new JPanel(new BorderLayout());
        JPanel col2 = new JPanel(new BorderLayout());

        JPanel searchBox = new JPanel();
        JTextField searchInput = new JTextField(30);
        searchBox.add(searchInput);
        col1.add(searchBox,BorderLayout.NORTH);

        String data[][] = categoryService.getAllCategories();

        String[] columns = {"ID", "Name"};

        DefaultTableModel dtm = new DefaultTableModel(data,columns);
        JTable jt = new JTable(dtm);

        JScrollPane scrollPane = new JScrollPane(jt);

        searchInput.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (searchInput.getText().isEmpty()) {
                    // Reload the initial table data
                    DefaultTableModel dtm = new DefaultTableModel(data, columns);
                    jt.setModel(dtm);
                } else {
                    // Perform search based on the entered text
                    String[][] searchData = categoryService.searchProduct(searchInput.getText());
                    DefaultTableModel dtm = new DefaultTableModel(searchData, columns);
                    jt.setModel(dtm);
                }

            }
        });

        //---------------------------------------------------------------------------
        //RIGHT COLUMN


        JButton addBtn = new JButton("Add");
        JButton editBtn = new JButton("Edit");
        JButton deleteBtn = new JButton("Delete");
        JButton backBtn = new JButton("Back");

        col2.setLayout(new FlowLayout(FlowLayout.CENTER));

        addBtn.addActionListener(e -> {
            mainFrame.dispose();
            new AddCategoryUI();
        });

        editBtn.addActionListener(e -> {
            mainFrame.dispose();
            if(jt.getSelectedRow()>=0){
                String id = (String) jt.getValueAt(jt.getSelectedRow(),0);
                String name = (String) jt.getValueAt(jt.getSelectedRow(),1);

                new EditCategoryUI(id,name);
            }
        });

        deleteBtn.addActionListener(e -> {
            if(jt.getSelectedRow()>=0){
                String id = (String) jt.getValueAt(jt.getSelectedRow(),0);
                categoryService.deleteItem(Integer.valueOf(id));
                JOptionPane.showMessageDialog(jt,"Successfully Deleted");
                new ProductUI();
            }
        });

        backBtn.addActionListener(e -> {

            mainFrame.dispose();
            new NavUI("admin");
        });

        col2.add(addBtn);
        col2.add(editBtn);
        col2.add(deleteBtn);
        col2.add(backBtn);


        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        col1.add(scrollPane);
        mainFrame.add(col1);

        mainFrame.add(col2,BorderLayout.CENTER);
        mainFrame.pack();
        mainFrame.setVisible(true);
        mainFrame.setSize(1200,700);
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFrame.setLocationRelativeTo(null);
    }
}
