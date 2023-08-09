package pos.traders.expense.postradersexpense.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pos.traders.expense.postradersexpense.beans.ExpenseCategoryBean;
import pos.traders.expense.postradersexpense.dto.ExpenseCategoryDto;
import pos.traders.expense.postradersexpense.dto.ServiceResponseDto;
import pos.traders.expense.postradersexpense.repo.ExpenseCategoryRepo;

import java.net.URI;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("expense/category")
public class ExpenseCategoryController {

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    ServiceResponseDto serviceResponseDto;

    @Autowired
    ExpenseCategoryRepo expenseCategoryRepo;

    @PostMapping("/new")
    public ResponseEntity<?> newExpenseCategory(@RequestBody ExpenseCategoryDto model){

        ExpenseCategoryBean categoryBeanRequest = modelMapper.map(model, ExpenseCategoryBean.class);

        Optional<ExpenseCategoryBean> expenseCategory = expenseCategoryRepo.findByExpenseCategoryTitle(categoryBeanRequest.getExpenseCategoryTitle());
        if(expenseCategory.isPresent()){
            serviceResponseDto.setResponseCode("0A");
            serviceResponseDto.setResponseDesc("Expense category already added!");
            serviceResponseDto.setPayload(null);
            return ResponseEntity.ok().body(serviceResponseDto);
        }
        categoryBeanRequest.setCreatedAt(new Date());
        ExpenseCategoryBean expenseCategoryNew = expenseCategoryRepo.save(categoryBeanRequest);
        if(!Objects.isNull(expenseCategoryNew) && expenseCategoryNew.getExpenseCategoryId() > 0){
            URI expenseCategoryUri = URI.create("/id/" + expenseCategoryNew.getExpenseCategoryId());
            ExpenseCategoryDto categoryDtoResponse = modelMapper.map(expenseCategoryNew, ExpenseCategoryDto.class);
            serviceResponseDto.setResponseCode("00");
            serviceResponseDto.setResponseDesc("SUCCESS");
            serviceResponseDto.setPayload(categoryDtoResponse);
            return ResponseEntity.created(expenseCategoryUri).body(serviceResponseDto);
        }

        serviceResponseDto.setResponseCode("0E");
        serviceResponseDto.setResponseDesc("ERROR");
        return ResponseEntity.ok().body(serviceResponseDto);
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateExpenseCategory(@RequestBody ExpenseCategoryBean model){

        ExpenseCategoryBean categoryBeanRequest = modelMapper.map(model, ExpenseCategoryBean.class);

        Optional<ExpenseCategoryBean> expenseCategory = expenseCategoryRepo.findById(categoryBeanRequest.getExpenseCategoryId());
        if(!expenseCategory.isPresent()){
            serviceResponseDto.setResponseCode("0I");
            serviceResponseDto.setResponseDesc("Expense category ID is invalid!");
            serviceResponseDto.setPayload(null);
            return ResponseEntity.ok().body(serviceResponseDto);
        }
        categoryBeanRequest.setLastUpdatedAt(new Date());
        ExpenseCategoryBean expenseCategoryUpdated = expenseCategoryRepo.save(categoryBeanRequest);
        if(!Objects.isNull(expenseCategoryUpdated)){
            ExpenseCategoryDto categoryDtoResponse = modelMapper.map(expenseCategoryUpdated, ExpenseCategoryDto.class);
            URI expenseCategoryUri = URI.create("/id/" + expenseCategoryUpdated.getExpenseCategoryId());
            serviceResponseDto.setResponseCode("00");
            serviceResponseDto.setResponseDesc("SUCCESS");
            serviceResponseDto.setPayload(categoryDtoResponse);
            return ResponseEntity.created(expenseCategoryUri).body(serviceResponseDto);
        }

        serviceResponseDto.setResponseCode("0E");
        serviceResponseDto.setResponseDesc("ERROR");
        serviceResponseDto.setPayload(null);
        return ResponseEntity.ok().body(serviceResponseDto);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<?> expenseCategoryById(@PathVariable("id") Integer expenseCategoryId){
        Optional<ExpenseCategoryBean> expenseCategoryOpt = expenseCategoryRepo.findById(expenseCategoryId);
        if(expenseCategoryOpt.isPresent()){
            ExpenseCategoryDto categoryDtoResponse = modelMapper.map(expenseCategoryOpt.get(), ExpenseCategoryDto.class);
            serviceResponseDto.setResponseCode("00");
            serviceResponseDto.setResponseDesc("SUCCESS");
            serviceResponseDto.setPayload(categoryDtoResponse);
            return ResponseEntity.ok().body(serviceResponseDto);
        }
        serviceResponseDto.setResponseCode("0I");
        serviceResponseDto.setResponseDesc("Expense category ID is invalid!");
        serviceResponseDto.setPayload(null);
        return ResponseEntity.ok().body(serviceResponseDto);
    }

    @CrossOrigin
    @GetMapping("/all")
    public ResponseEntity<?> expenseCategories(){

        List<ExpenseCategoryBean> expenseCategoryList = expenseCategoryRepo.findAll();
        if(Objects.isNull(expenseCategoryList) || expenseCategoryList.size() < 1) {
            serviceResponseDto.setResponseCode("0N");
            serviceResponseDto.setResponseDesc("Expense categories not defined!");
            serviceResponseDto.setPayload(null);
            return ResponseEntity.ok().body(serviceResponseDto);
        }

        List<ExpenseCategoryDto> expenseCategoryDtoList = expenseCategoryList.stream()
                .map(user -> modelMapper.map(user, ExpenseCategoryDto.class))
                .collect(Collectors.toList());
        serviceResponseDto.setResponseCode("00");
        serviceResponseDto.setResponseDesc("Success");
        serviceResponseDto.setPayload(expenseCategoryDtoList);
        return ResponseEntity.ok().body(serviceResponseDto);
    }
}
