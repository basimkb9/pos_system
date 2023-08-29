package org.possystem.mapper;

import org.possystem.domain.Category;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryMapper implements IMapper<Category> {
    private final String ID = "id";
    private final String NAME = "c_name";

    @Override
    public List<Category> resultSetToList(ResultSet rs) throws SQLException {
        List<Category> categoryList = new ArrayList<>();

        while(rs.next()){
            Category category = Category.builder()
                    .id((long) rs.getInt(ID))
                    .name(rs.getString(NAME))
                    .build();

            categoryList.add(category);
        }

        return categoryList;
    }

    @Override
    public Category resultSetToObject(ResultSet rs) throws SQLException {
        if(rs.next()){
            return Category.builder()
                    .id((long) rs.getInt(ID))
                    .name(rs.getString(NAME))
                    .build();
        }
        return null;
    }

    public List<String[]> resultSetToStringList(ResultSet rs) throws SQLException{
        List<String[]> categoryList = new ArrayList<>();

        while(rs.next()){
            String categoryId = String.valueOf(rs.getInt("id"));
            String categoryName = rs.getString("c_name");
            String[] row = {categoryId, categoryName};

            categoryList.add(row);
        }

        return categoryList;
    }
}
