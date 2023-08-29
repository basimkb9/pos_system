package org.possystem.ui;

import javax.swing.*;
import java.awt.*;

public class HomeUI {
    public HomeUI() {
        JFrame homeScreen = new JFrame();
        homeScreen.setTitle("Home | POS System");
        homeScreen.setLayout(new FlowLayout(FlowLayout.CENTER,20,10));

        JButton productBtn = new JButton("Products");
        productBtn.setFont(new Font("Calibri",2,24));
        addImageOnButton(productBtn, "C:\\Users\\dod20\\IdeaProjects\\pos_system\\src\\main\\resources\\product-icon.png",100,100);
//        productBtn.setPreferredSize(new Dimension(200,100));

        productBtn.addActionListener(e -> {
            homeScreen.dispose();
//            new ProductUI();
        });

        homeScreen.add(productBtn);
        homeScreen.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        homeScreen.setSize(1200, 700);
        homeScreen.setVisible(true);
        homeScreen.setLocationRelativeTo(null);

//        JPanel tablePanel = new JPanel();
//        tablePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 100));
//
//        String data[][] = {
//                {"101", "Amit", "670000"},
//                {"102", "Jai", "780000"},
//                {"103", "Sachin", "700000"}
//        };
//        String column[] = {"ID", "NAME", "SALARY"};
//        JTable jt = new JTable(data, column);
//        JScrollPane sp = new JScrollPane(jt);
//        tablePanel.add(sp);
//
//        add(new JButton("Table"), BorderLayout.NORTH);
//        add(tablePanel, BorderLayout.CENTER);
//
//        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
//        setSize(1200, 700);
//        setVisible(true);
//        setLocationRelativeTo(null);
    }

    static void addImageOnButton(JButton button, String path, int width, int height) {
        ImageIcon imageIcon = new ImageIcon(path);
        Image newImage = imageIcon.getImage().getScaledInstance(width,height,Image.SCALE_SMOOTH);
        button.setIcon(new ImageIcon(newImage));
    }

}
