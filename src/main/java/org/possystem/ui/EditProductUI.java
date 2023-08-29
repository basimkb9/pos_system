package org.possystem.ui;

import org.possystem.domain.Variant;
import org.possystem.service.ProductService;
import java.util.List;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import static javax.swing.BoxLayout.Y_AXIS;

public class EditProductUI {
    private final ProductService prodService = new ProductService();

    public EditProductUI(String id, String name){
        JFrame editFrame = new JFrame("Edit Product | POS System");
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, Y_AXIS));

        JPanel productPnl = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel nameLabel = new JLabel("Product Name");
        productPnl.add(nameLabel);
        JTextField nameTF = new JTextField(30);
        nameTF.setText(name);
        productPnl.add(nameTF);

        mainPanel.add(productPnl);

        List<Variant> variantList = prodService.getAllVariantsWithProductId(id);
        JPanel variantContainer = setVariantFields(variantList);
        variantContainer.setSize(0,400);
        variantContainer.setLayout(new BoxLayout(variantContainer, Y_AXIS));
        JScrollPane variantSp = new JScrollPane(variantContainer);
        variantSp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        JButton backBtn = new JButton("BACK");
        JButton saveBtn = new JButton("SAVE");
        JPanel buttonsPnl = new JPanel(new FlowLayout(FlowLayout.CENTER));

        mainPanel.add(variantSp);

        buttonsPnl.add(saveBtn);
        buttonsPnl.add(backBtn);
        mainPanel.add(buttonsPnl);

        backBtn.addActionListener(e -> {
            editFrame.dispose();
            new ProductUI();
        });

        saveBtn.addActionListener(e -> {
//            try{
//                prodService.UpdateProdAndWithVariant(Integer.valueOf(id),nameTF.getText(),variantContainer);
//                new ProductUI();
//            }catch (Exception exc){
//                JOptionPane.showMessageDialog(editFrame,"Something went wrong, please try again.");
//            }
            try {
                List<Variant> updatedVariants = new ArrayList<>();
                for (Component component : variantContainer.getComponents()) {
                    if (component instanceof JPanel) {
                        JPanel variantPanel = (JPanel) component;

                        // Retrieve text from JTextFields in the variantPanel
                        JTextField variantNameField = (JTextField) variantPanel.getComponent(1); // Assuming name is at index 1
                        JTextField costPriceField = (JTextField) variantPanel.getComponent(3); // Assuming cost price is at index 3
                        JTextField salePriceField = (JTextField) variantPanel.getComponent(5); // Assuming sale price is at index 5
                        JTextField barcodeField = (JTextField) variantPanel.getComponent(7); // Assuming barcode is at index 7
                        JTextField quantityField = (JTextField) variantPanel.getComponent(9); // Assuming quantity is at index 9

                        String variantName = variantNameField.getText();
                        double costPrice = Double.parseDouble(costPriceField.getText());
                        double salePrice = Double.parseDouble(salePriceField.getText());
                        String barcode = barcodeField.getText();
                        int quantity = Integer.parseInt(quantityField.getText());

                        Variant variant = Variant.builder()
                                .name(variantName)
                                .price(salePrice)
                                .costPrice(costPrice)
                                .barcode(barcode)
                                .quantityInStock((long) quantity)
                                .build();

                        updatedVariants.add(variant);
                    }
                }

                prodService.UpdateProdAndWithVariant(Integer.valueOf(id), nameTF.getText(), updatedVariants);
                new ProductUI();
            } catch (Exception exc) {
                JOptionPane.showMessageDialog(editFrame, "Something went wrong, please try again.");
            }
        });

        editFrame.add(mainPanel);
        editFrame.pack();
        editFrame.setVisible(true);
        editFrame.setSize(400,500);
        editFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        editFrame.setLocationRelativeTo(null);
    }

    private JPanel setVariantFields(List<Variant> variantList) {
        JPanel containerPnl = new JPanel();

        for (Variant variant : variantList) {
            JPanel panel = new JPanel();
            panel.setLayout(new FlowLayout(FlowLayout.LEFT));

            JLabel label1 = new JLabel("Variant name:");
            JTextField textField1 = new JTextField(10);
            textField1.setText(variant.getName());

            JLabel label2 = new JLabel("Cost Price:");
            JTextField textField2 = new JTextField(5);
            textField2.setText(String.valueOf(variant.getCostPrice()));

            JLabel label3 = new JLabel("Sale Price:");
            JTextField textField3 = new JTextField(5);
            textField3.setText(String.valueOf(variant.getPrice()));

            JLabel label4 = new JLabel("Barcode #");
            JTextField textField4 = new JTextField(20);
            textField4.setText(variant.getBarcode());

            JLabel label5 = new JLabel("Quantity:");
            JTextField textField5 = new JTextField(5);
            textField5.setText(String.valueOf(variant.getQuantityInStock()));

            panel.add(label1);
            panel.add(textField1);
            panel.add(label2);
            panel.add(textField2);
            panel.add(label3);
            panel.add(textField3);
            panel.add(label4);
            panel.add(textField4);
            panel.add(label5);
            panel.add(textField5);

            containerPnl.add(panel);
        }

        return containerPnl;
    }
}
