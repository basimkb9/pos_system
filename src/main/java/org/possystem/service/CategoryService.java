package org.possystem.service;

import org.possystem.dao.CategoryDAO;
import org.possystem.domain.Category;
import org.possystem.domain.Product;

import java.sql.SQLException;
import java.util.List;

public class CategoryService {
    private final CategoryDAO categoryDAO = new CategoryDAO();

    public String[][] getAllCategories(){
        List<String[]> productListWithVariant = categoryDAO.getAllInStringList();

        String[][] resultArray = productListWithVariant.toArray(new String[0][0]);

        return resultArray;
    }

    public String[][] searchProduct(String name){
        return transformToTable(categoryDAO.getCategoryByName(name));
    }

    private String[][] transformToTable(List<String[]> categoryList) {
        String[][] resultArray = categoryList.toArray(new String[0][0]);

        return resultArray;
    }

    public void deleteItem(Integer id) {
        try{
            categoryDAO.deleteById(Long.valueOf(id));
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public void editCategory(Integer id, String name) {
        Category category = Category.builder()
                .name(name)
                .build();

        categoryDAO.update(category, Long.valueOf(id));
    }

    public void saveCategory(String name) throws SQLException {
        Category category = Category.builder()
                .name(name)
                .build();

        categoryDAO.insert(category);
    }
}
