package org.possystem.mapper;

import org.possystem.domain.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserMapper implements IMapper<User>{
    private final String ID = "id";
    private final String USERNAME = "username";
    private final String PASSWORD = "pass";
    private final String ROLE = "role";

    @Override
    public List<User> resultSetToList(ResultSet rs) throws SQLException {
        List<User> users = new ArrayList<>();

        while(rs.next()){
            User user = User.builder()
                    .id(rs.getInt(ID))
                    .password(rs.getString(PASSWORD))
                    .username(rs.getString(USERNAME))
                    .build();

            users.add(user);
        }
        return users;
    }

    @Override
    public User resultSetToObject(ResultSet rs) throws SQLException {
        if(rs.next()){
            return User.builder()
                    .id(rs.getInt(ID))
                    .password(rs.getString(PASSWORD))
                    .username(rs.getString(USERNAME))
                    .role(rs.getString(ROLE))
                    .build();
        }
        return null;
    }
}
