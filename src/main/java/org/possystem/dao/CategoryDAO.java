package org.possystem.dao;

import org.possystem.domain.Category;
import org.possystem.mapper.CategoryMapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static org.possystem.dao.SqlQueryConstants.*;

public class CategoryDAO extends BaseDAO implements ICrud<Category>{
    private final CategoryMapper categoryMapper = new CategoryMapper();

    @Override
    public void insert(Category obj) {
        try {
            PreparedStatement ps = conn.prepareStatement(INSERT_INTO_CATEGORY);
            ps.setString(1,obj.getName());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Category> getAll() {
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(GET_ALL_CATEGORY);
            return categoryMapper.resultSetToList(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    @Override
    public Category getById(Long id) {
        try {
            PreparedStatement stmt = conn.prepareStatement(GET_CATEGORY_BY_ID);
            stmt.setInt(1,Math.toIntExact(id));
            ResultSet rs = stmt.executeQuery();
            return categoryMapper.resultSetToObject(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void update(Category obj, Long id) {
        try{
            PreparedStatement ps = conn.prepareStatement(UPDATE_CATEGORY_BY_ID);
            ps.setString(1,obj.getName());
            ps.setInt(2,Math.toIntExact(obj.getId()));
            ps.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void deleteById(Long id) {
        try{
            PreparedStatement ps = conn.prepareStatement(DELETE_CATEGORY_BY_ID);
            ps.setInt(1,Math.toIntExact(id));
            ps.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public List<String[]> getAllInStringList() {
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(GET_ALL_CATEGORY);
            return categoryMapper.resultSetToStringList(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public List<String[]> getCategoryByName(String name) {
        List<String[]> categoryData = new ArrayList<>();
        try {
            PreparedStatement ps = conn.prepareStatement("select * from category where c_name like '%" + name +"%'");

            ResultSet rs = ps.executeQuery();
            categoryData = categoryMapper.resultSetToStringList(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categoryData;
    }
}
