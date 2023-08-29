package org.possystem;

import com.mysql.cj.x.protobuf.MysqlxCrud;
import org.possystem.dao.CategoryDAO;
import org.possystem.dao.ProductDAO;
import org.possystem.dao.TransactionDetailDAO;
import org.possystem.domain.Category;
import org.possystem.domain.Product;
import org.possystem.domain.TransactionDetail;
import org.possystem.mapper.ProductMapper;
import org.possystem.service.ProductService;
import org.possystem.ui.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        new LoginUI();
//        new AddProductUI();
    }
}