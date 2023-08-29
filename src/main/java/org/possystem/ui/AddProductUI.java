package org.possystem.ui;

import org.possystem.domain.Variant;
import org.possystem.service.ProductService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static javax.swing.BoxLayout.Y_AXIS;

public class AddProductUI {
    private final ProductService prodService = new ProductService();

    public AddProductUI(){
        JFrame mainScreen = new JFrame("Products | POS System");
        JPanel mainFrame = new JPanel();
//        mainFrame.setLayout(new GridLayout(3,1,10,20));
        mainFrame.setLayout(new BoxLayout(mainFrame,Y_AXIS));

        JPanel brandPnl = new JPanel();
        JPanel variantPnl = new JPanel();
        JPanel buttonsPnl = new JPanel();

        brandPnl.setLayout(new GridLayout(3,2,10,10));
        variantPnl.setLayout(new BoxLayout(variantPnl, Y_AXIS));
        buttonsPnl.setLayout(new FlowLayout(FlowLayout.CENTER));

        //Heading
        JLabel brandText = new JLabel();
        brandText.setText("Brand info");
        brandText.setFont(new Font("Arial", Font.PLAIN, 24));
        brandText.setForeground(Color.BLACK);

        JPanel emptyGap1 = new JPanel();
//        emptyGap.setLayout(new FlowLayout(FlowLayout.LEFT));

        JLabel nameLabel = new JLabel("Product Name");
        JTextField nameTF = new JTextField(30);

        JLabel categoryLabel = new JLabel("Category ID");
        String[] categoryOptions = prodService.getAllCategories();
        JComboBox<String> categories = new JComboBox<>(categoryOptions);

//        JTextField categoryTF = new JTextField(30);


        brandPnl.add(brandText);
        brandPnl.add(emptyGap1);
        brandPnl.add(nameLabel);
        brandPnl.add(nameTF);
        brandPnl.add(categoryLabel);
        brandPnl.add(categories);

        //==========================================================================
        //VARIANT INFO
        //==========================================================================

        //region Heading and + button
        JLabel variantText = new JLabel();
        variantText.setText("Variant(s) info");
        variantText.setFont(new Font("Arial", Font.PLAIN, 24));
        variantText.setForeground(Color.BLACK);
        JPanel vTextPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        vTextPanel.add(variantText);

        JButton addAnotherBtn = new JButton("+");
        JPanel addAnotherBtnPnl = new JPanel(new FlowLayout(FlowLayout.CENTER));
        addAnotherBtnPnl.add(addAnotherBtn);

        JPanel topField = new JPanel();
        topField.setLayout(new BorderLayout());
        topField.add(vTextPanel,BorderLayout.WEST);
        topField.add(addAnotherBtnPnl,BorderLayout.EAST);
        //endregion



        JPanel panelContainer = new JPanel();
        panelContainer.setSize(0,400);
        panelContainer.setLayout(new BoxLayout(panelContainer, Y_AXIS));
        JScrollPane variantSp = new JScrollPane(panelContainer);
        variantSp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        List<JPanel> variantInputFields = new ArrayList<>();



        addAnotherBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JPanel newPanel = createPanel();
                    variantInputFields.add(newPanel);
                    panelContainer.add(newPanel);

                    // Update the preferred size of the panelContainer
                    panelContainer.setPreferredSize(new Dimension(panelContainer.getWidth(), panelContainer.getPreferredSize().height + newPanel.getPreferredSize().height));

                    mainFrame.revalidate();
                    mainFrame.repaint();

                }
        });

        //==========================================================================
        //BACK AND SAVE INFO BUTTONS
        //==========================================================================


        JButton backBtn = new JButton("BACK");
        JButton saveBtn = new JButton("SAVE");

        backBtn.addActionListener(e -> {
            mainScreen.dispose();
            new ProductUI();
        });

        saveBtn.addActionListener(e -> {
            try{
                String category = (String) categories.getSelectedItem();
                String[] category_items = category.split(",");
                Integer id = Integer.valueOf(category_items[0]);

                List<String[]> variantList = prodService.variantNameAndPriceList(variantInputFields);

                prodService.saveProd(nameTF.getText(),String.valueOf(id),variantList);
                mainScreen.dispose();
                new ProductUI();
            }catch (Exception exc){
                JOptionPane.showMessageDialog(mainFrame,"Something went wrong, please try again.");
            }
        });

        JPanel btnPnlRow = new JPanel(new FlowLayout(FlowLayout.CENTER));

        variantPnl.add(topField);
        variantPnl.add(variantSp);


        btnPnlRow.add(backBtn);
        btnPnlRow.add(saveBtn);
        buttonsPnl.add(btnPnlRow);

        mainFrame.add(brandPnl);
        mainFrame.add(variantPnl);
        mainFrame.add(buttonsPnl);
        mainScreen.add(mainFrame);
        mainScreen.pack();
        mainScreen.setSize(900, 400);
        mainScreen.setVisible(true);
//        mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
//        mainFrame.setSize(900,700);
        mainScreen.setResizable(false);
        mainScreen.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainScreen.setLocationRelativeTo(null);
    }


    private static JPanel createPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));

        JLabel label1 = new JLabel("Variant name:");
        JTextField textField1 = new JTextField(10);

        JLabel label2 = new JLabel("Cost Price:");
        JTextField textField2 = new JTextField(5);

        JLabel label3 = new JLabel("Sale Price:");
        JTextField textField3 = new JTextField(5);

        JLabel label4 = new JLabel("Barcode #");
        JTextField textField4 = new JTextField(20);

        JLabel label5 = new JLabel("Quantity:");
        JTextField textField5 = new JTextField(5);

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

        return panel;
    }
}
