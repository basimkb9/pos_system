package org.possystem.ui;

import org.possystem.service.CategoryService;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class AddCategoryUI {

    private final CategoryService categoryService = new CategoryService();

    public AddCategoryUI(){
        JFrame mainFrame = new JFrame("Add a Category | POS System");
        mainFrame.setLayout(new BorderLayout(10,30));

        JPanel fields = new JPanel(new FlowLayout(FlowLayout.CENTER));

        JLabel nameLabel = new JLabel();
        nameLabel.setText("Category Name:");
        fields.add(nameLabel);

        JTextField nameTF = new JTextField(15);
        fields.add(nameTF);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton saveBtn = new JButton("SAVE");
        JButton backBtn = new JButton("BACK");

        bottomPanel.add(saveBtn);
        bottomPanel.add(backBtn);

        saveBtn.addActionListener(e -> {
            try{
                categoryService.saveCategory(nameTF.getText());
                mainFrame.dispose();
                new CategoryUI();
            }catch (SQLException x){
                JOptionPane.showMessageDialog(mainFrame,x.getMessage());
            }
        });

        backBtn.addActionListener(e -> {
            mainFrame.dispose();
            new ProductUI();
        });

        mainFrame.add(fields,BorderLayout.CENTER);
        mainFrame.add(bottomPanel,BorderLayout.SOUTH);
        mainFrame.pack();
        mainFrame.setVisible(true);
        mainFrame.setSize(500,200);
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFrame.setLocationRelativeTo(null);
    }
}
