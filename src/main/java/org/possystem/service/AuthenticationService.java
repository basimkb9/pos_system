package org.possystem.service;

import org.possystem.dao.UserDAO;
import org.possystem.domain.User;
import org.possystem.dao.ProductDAO;
import org.possystem.domain.Product;

public class AuthenticationService {

    private final UserDAO userDAO = new UserDAO();

    public User checkLogin(String username, String pass){
        return userDAO.getUser(username,pass);
    }

    public boolean checkAdmin(String name) {
        if((userDAO.getByName(name).getRole().equalsIgnoreCase("administrator"))){
            return true;
        }
        return false;
    }

}
