package org.possystem.dao;

import org.possystem.domain.User;
import org.possystem.mapper.IMapper;
import org.possystem.mapper.UserMapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.possystem.dao.SqlQueryConstants.*;

public class UserDAO extends BaseDAO implements ICrud<User> {

    private final IMapper<User> userMapper = new UserMapper();

    public User getUserAndPass(String username, String password){
        try {
            PreparedStatement ps = conn.prepareStatement(GET_USERNAME_AND_PASS);
            ps.setString(1,username);
            ps.setString(2,password);

            ResultSet rs = ps.executeQuery();
            return userMapper.resultSetToObject(rs);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void insert(User obj) {

    }

    @Override
    public List<User> getAll() {
        return null;
    }

    @Override
    public User getById(Long id) {
        return null;
    }

    @Override
    public void update(User obj, Long id) {

    }
    @Override
    public void deleteById(Long id) {

    }

    public User getByName(String name){
        try {
            PreparedStatement ps = conn.prepareStatement(GET_USER_BY_USERNAME);
            ps.setString(1,name);

            ResultSet rs = ps.executeQuery();
            return userMapper.resultSetToObject(rs);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public User getUser(String username, String pass) {
        try {
            PreparedStatement ps = conn.prepareStatement(GET_USERNAME_AND_PASS);
            ps.setString(1,username);
            ps.setString(2,pass);

            ResultSet rs = ps.executeQuery();
            return userMapper.resultSetToObject(rs);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
