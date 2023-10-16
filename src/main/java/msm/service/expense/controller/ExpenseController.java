package msm.service.expense.controller;

import msm.service.expense.dto.ExpenseDto;
import msm.service.expense.dto.ServiceResponseDto;
import msm.service.expense.service.ExpenseService;
import msm.service.expense.util.ExpenseServiceResponseCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;


@RestController
@RequestMapping("/expense")
public class ExpenseController {

    @Autowired
    ExpenseService expenseService;

    @PostMapping("/save")
    public ResponseEntity<?> newExpense(@RequestBody ExpenseDto model){

        ServiceResponseDto serviceResponseDto = expenseService.saveExpense(model);
        if(ExpenseServiceResponseCode.SUCCESS.getCode().equals(serviceResponseDto.getResponseCode())){
            ExpenseDto savedExpenseDto = (ExpenseDto) serviceResponseDto.getPayload();
            URI expenseCategoryUri = URI.create("/id/" + savedExpenseDto.getExpenseId());
            return ResponseEntity.created(expenseCategoryUri).body(serviceResponseDto);
        }
        return ResponseEntity.ok().body(serviceResponseDto);
    }

    @GetMapping("/current")
    public ResponseEntity<?> expensesCurrentMonth(){

        return ResponseEntity.ok().body(expenseService.getExpensesCurrentMonth());
    }

    @GetMapping("/between")
    public ResponseEntity<?> expensesBetweenDate(@RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate, @RequestParam("expenseCategory") String expenseCategory){

        return ResponseEntity.ok().body(expenseService.getExpensesBetweenDate(startDate, endDate, expenseCategory));
    }

    @GetMapping("/id/{expenseId}")
    public ResponseEntity<?> expenseById(@PathVariable("expenseId") Integer expenseId){

        return ResponseEntity.ok().body(expenseService.getExpenseById(expenseId));
    }

    @GetMapping("/delete/id/{expenseId}")
    public ResponseEntity<?> expenseDeleteById(@PathVariable("expenseId") Integer expenseId){

        return ResponseEntity.ok().body(expenseService.deleteExpenseById(expenseId));
    }

    @GetMapping("/by/category/{categoryId}")
    public ResponseEntity<?> expenseByCategoryId(@PathVariable("categoryId") Integer categoryId){

        return ResponseEntity.ok().body(expenseService.getExpenseByCategoryId(categoryId));

    }

}
