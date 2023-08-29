package org.possystem.service;

import org.possystem.dao.TransactionDAO;
import org.possystem.domain.Transaction;

import java.util.List;

public class TransactionService {
    private final TransactionDAO trDao = new TransactionDAO();
    public String[][] getAllTransactions(){
        List<String[]> transactionItems = trDao.getAllInStringList();

        String[][] trItemsArray = transactionItems.toArray(new String[0][0]);

        return trItemsArray;
    }

    public void deleteItem(Integer id) {
        try{
            trDao.deleteById(Long.valueOf(id));
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
