package org.possystem.ui;

import org.possystem.service.CategoryService;

import javax.swing.*;
import java.awt.*;

public class EditCategoryUI {
    private final CategoryService categoryService = new CategoryService();

    public EditCategoryUI(String id, String name) {
        JFrame editFrame = new JFrame("Edit Category | POS System");
        editFrame.setLayout(new GridLayout(6,2,20,20));

        JLabel nameLabel = new JLabel("Category Name");
        editFrame.add(nameLabel);
        JTextField nameTF = new JTextField(30);
        nameTF.setText(name);
        editFrame.add(nameTF);


        JButton backBtn = new JButton("BACK");
        JButton saveBtn = new JButton("SAVE");

        editFrame.add(saveBtn);
        editFrame.add(backBtn);

        backBtn.addActionListener(e -> {
            editFrame.dispose();
            new CategoryUI();
        });

        saveBtn.addActionListener(e -> {
            try{
                categoryService.editCategory(Integer.valueOf(id),nameTF.getText());
                new CategoryUI();
            }catch (Exception exc){
                JOptionPane.showMessageDialog(editFrame,"Something went wrong, please try again.");
            }
        });

        editFrame.pack();
        editFrame.setVisible(true);
        editFrame.setSize(400,500);
        editFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        editFrame.setLocationRelativeTo(null);
    }
}
