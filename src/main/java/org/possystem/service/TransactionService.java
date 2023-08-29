package org.possystem.service;

import com.toedter.calendar.JDateChooser;
import org.possystem.dao.TransactionDAO;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

public class TransactionService {
    private final TransactionDAO trDao = new TransactionDAO();
    public String[][] getAllTransactions(){
        List<String[]> transactionItems = trDao.getAllInStringList();

        return transactionItems.toArray(new String[0][0]);
    }

    public void deleteItem(Integer id) {
        try{
            trDao.deleteById(Long.valueOf(id));
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public String[][] getByDateRange(JDateChooser startDateChooser, JDateChooser endDateChooser) {
        List<String[]> result;
        LocalDate startDate;
        LocalDate endDate;

        if(startDateChooser.getDate() == null){
            endDate = endDateChooser.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            result = trDao.getByDate(endDate);
        }
        else if(endDateChooser.getDate() == null){
            startDate = startDateChooser.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            result = trDao.getByDate(startDate);
        }
        else{
            startDate = startDateChooser.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            endDate = endDateChooser.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            result = trDao.getByDateBetween(startDate,endDate);
        }
        return result.toArray(new String[0][0]);
    }
}
