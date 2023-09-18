package pos.traders.expense.postradersexpense.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pos.traders.expense.postradersexpense.dto.ExpenseCategoryDto;
import pos.traders.expense.postradersexpense.dto.ServiceResponseDto;
import pos.traders.expense.postradersexpense.service.ExpenseCategoryService;
import pos.traders.expense.postradersexpense.util.ExpenseServiceResponseCode;

import java.net.URI;

@RestController
@RequestMapping("expense/category")
public class ExpenseCategoryController {

    @Autowired
    ServiceResponseDto serviceResponseDto;

    @Autowired
    ExpenseCategoryService expenseCategoryService;

    @PostMapping("/new")
    public ResponseEntity<?> newExpenseCategory(@RequestBody ExpenseCategoryDto model){

        ServiceResponseDto serviceResponseDto = expenseCategoryService.newExpenseCategory(model);
        if(ExpenseServiceResponseCode.SUCCESS.getCode().equals(serviceResponseDto.getResponseCode())) {
            ExpenseCategoryDto newExpenseCategory = (ExpenseCategoryDto) serviceResponseDto.getPayload();
            URI newExpenseCategoryUri = URI.create("/id/" + newExpenseCategory.getExpenseCategoryId());
            return ResponseEntity.created(newExpenseCategoryUri).body(serviceResponseDto);
        }
        return ResponseEntity.ok().body(serviceResponseDto);
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateExpenseCategory(@RequestBody ExpenseCategoryDto model){

        serviceResponseDto = expenseCategoryService.updateExpenseCategory(model);
        if(ExpenseServiceResponseCode.SUCCESS.getCode().equals(serviceResponseDto.getResponseCode())){
            URI updatedExpenseCategoryUri = URI.create("/id/" + model.getExpenseCategoryId());
            return ResponseEntity.created(updatedExpenseCategoryUri).body(serviceResponseDto);
        }
        return ResponseEntity.ok().body(serviceResponseDto);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<?> expenseCategoryById(@PathVariable("id") Integer expenseCategoryId){

        return ResponseEntity.ok().body(expenseCategoryService.getExpenseCategoryById(expenseCategoryId));

    }

    @CrossOrigin
    @GetMapping("/all")
    public ResponseEntity<?> expenseCategories(){

        return ResponseEntity.ok().body(expenseCategoryService.getAllExpenseCategories());

    }
}
