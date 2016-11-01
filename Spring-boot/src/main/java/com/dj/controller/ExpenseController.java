package com.dj.controller;

import com.dj.model.Expense;
import com.dj.model.Receipt;
import com.dj.model.Report;
import com.dj.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Darshan on 11/1/2016.
 */
@RestController
@RequestMapping("expenses")
public class ExpenseController {
    private Logger logger = LoggerFactory.getLogger(ExpenseController.class);

    @RequestMapping(value="/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Report findReportById(@PathVariable("id") int id) {
        logger.info("Retrieving report: {}", id);
        List<Receipt> receiptList1 = new ArrayList<>();
        Receipt r1 = new Receipt(1, "receipt1.jpg");
        Receipt r2 = new Receipt(2, "receipt2.jpg");
        Receipt r3 = new Receipt(3, "receipt3.jpg");
        Receipt r4 = new Receipt(4, "receipt4.jpg");
        receiptList1.add(r1); receiptList1.add(r2); receiptList1.add(r3); receiptList1.add(r4);

        Expense e1 = new Expense(1, "Taxi", Date.valueOf(LocalDate.of(2016,10,10)),
                "Took taxi because no bus is available.\n No taxi bill was available.\nHad to take taxi from railway station."
                , 500.00f, "", receiptList1);

        List<Receipt> receiptList2 = new ArrayList<>();
        Receipt r5 = new Receipt(5, "receipt5.jpg");
        Receipt r6 = new Receipt(6, "receipt6.jpg");
        Receipt r7 = new Receipt(7, "receipt7.jpg");
        Receipt r8 = new Receipt(8, "receipt8.jpg");
        receiptList2.add(r5); receiptList2.add(r6); receiptList2.add(r7); receiptList2.add(r8);

        Expense e2 = new Expense(2, "Food", Date.valueOf(LocalDate.of(2016,10,10)),
                "Food at nearby hotel"
                , 350.00f, "", receiptList2);

        List<Expense> expenseList = new ArrayList<>();
        expenseList.add(e1); expenseList.add(e2);

        Report report = new Report(1, "Travel to Nagpur Center", Date.valueOf(LocalDate.of(2016,10,10)), expenseList);

        return report;
    }
}
