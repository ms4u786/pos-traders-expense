package pos.traders.expense.postradersexpense.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pos.traders.expense.postradersexpense.beans.ExpenseCategoryBean;
import pos.traders.expense.postradersexpense.dto.ExpenseCategoryDto;
import pos.traders.expense.postradersexpense.dto.ServiceResponseDto;
import pos.traders.expense.postradersexpense.repo.ExpenseCategoryRepo;
import pos.traders.expense.postradersexpense.util.ExpenseServiceResponseCode;

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

    @Autowired
    MessageSource messageSource;

    @PostMapping("/new")
    public ResponseEntity<?> newExpenseCategory(@RequestBody ExpenseCategoryDto model){

        ExpenseCategoryBean categoryBeanRequest = modelMapper.map(model, ExpenseCategoryBean.class);

        Optional<ExpenseCategoryBean> expenseCategory = expenseCategoryRepo.findByExpenseCategoryTitle(categoryBeanRequest.getExpenseCategoryTitle());
        if(expenseCategory.isPresent()){
            serviceResponseDto.setResponseCode("0A");
            serviceResponseDto.setResponseDesc(messageSource.getMessage("expense.category.already.add", null, LocaleContextHolder.getLocale()));
            serviceResponseDto.setPayload(null);
            return ResponseEntity.ok().body(serviceResponseDto);
        }

        categoryBeanRequest.setCreatedAt(new Date());
        ExpenseCategoryBean expenseCategoryNew = expenseCategoryRepo.save(categoryBeanRequest);
        if(Objects.isNull(expenseCategoryNew) || expenseCategoryNew.getExpenseCategoryId() <= 0){
            serviceResponseDto.setResponseCode(ExpenseServiceResponseCode.ERROR.getCode());
            serviceResponseDto.setResponseDesc(messageSource.getMessage("operation.error.desc", null, LocaleContextHolder.getLocale()));
            serviceResponseDto.setPayload(null);
            return ResponseEntity.ok().body(serviceResponseDto);
        }

        URI expenseCategoryUri = URI.create("/id/" + expenseCategoryNew.getExpenseCategoryId());
        ExpenseCategoryDto categoryDtoResponse = modelMapper.map(expenseCategoryNew, ExpenseCategoryDto.class);
        serviceResponseDto.setResponseCode(ExpenseServiceResponseCode.SUCCESS.getCode());
        serviceResponseDto.setResponseDesc(messageSource.getMessage("operation.success.desc", null, LocaleContextHolder.getLocale()));
        serviceResponseDto.setPayload(categoryDtoResponse);
        return ResponseEntity.created(expenseCategoryUri).body(serviceResponseDto);
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateExpenseCategory(@RequestBody ExpenseCategoryBean model){

        ExpenseCategoryBean categoryBeanRequest = modelMapper.map(model, ExpenseCategoryBean.class);

        Optional<ExpenseCategoryBean> expenseCategory = expenseCategoryRepo.findById(categoryBeanRequest.getExpenseCategoryId());
        if(!expenseCategory.isPresent()){
            serviceResponseDto.setResponseCode(ExpenseServiceResponseCode.InValid.getCode());
            serviceResponseDto.setPayload(null);
            serviceResponseDto.setResponseDesc(messageSource.getMessage("expense.category.id.invalid", null, LocaleContextHolder.getLocale()));
            return ResponseEntity.ok().body(serviceResponseDto);
        }

        categoryBeanRequest.setLastUpdatedAt(new Date());
        ExpenseCategoryBean expenseCategoryUpdated = expenseCategoryRepo.save(categoryBeanRequest);
        if(Objects.isNull(expenseCategoryUpdated)){
            serviceResponseDto.setResponseCode(ExpenseServiceResponseCode.ERROR.getCode());
            serviceResponseDto.setResponseDesc(messageSource.getMessage("operation.error.desc", null, LocaleContextHolder.getLocale()));
            serviceResponseDto.setPayload(null);
            return ResponseEntity.ok().body(serviceResponseDto);
        }

        ExpenseCategoryDto categoryDtoResponse = modelMapper.map(expenseCategoryUpdated, ExpenseCategoryDto.class);
        URI expenseCategoryUri = URI.create("/id/" + expenseCategoryUpdated.getExpenseCategoryId());
        serviceResponseDto.setResponseCode(ExpenseServiceResponseCode.SUCCESS.getCode());
        serviceResponseDto.setResponseDesc(messageSource.getMessage("operation.success.desc", null, LocaleContextHolder.getLocale()));
        serviceResponseDto.setPayload(categoryDtoResponse);
        return ResponseEntity.created(expenseCategoryUri).body(serviceResponseDto);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<?> expenseCategoryById(@PathVariable("id") Integer expenseCategoryId){
        Optional<ExpenseCategoryBean> expenseCategoryOpt = expenseCategoryRepo.findById(expenseCategoryId);

        if(!expenseCategoryOpt.isPresent()){
            serviceResponseDto.setResponseCode(ExpenseServiceResponseCode.InValid.getCode());
            serviceResponseDto.setResponseDesc(messageSource.getMessage("expense.category.id.invalid", null, LocaleContextHolder.getLocale()));
            serviceResponseDto.setPayload(null);
            return ResponseEntity.ok().body(serviceResponseDto);
        }

        ExpenseCategoryDto categoryDtoResponse = modelMapper.map(expenseCategoryOpt.get(), ExpenseCategoryDto.class);
        serviceResponseDto.setResponseCode(ExpenseServiceResponseCode.SUCCESS.getCode());
        serviceResponseDto.setResponseDesc(messageSource.getMessage("operation.success.desc", null, LocaleContextHolder.getLocale()));
        serviceResponseDto.setPayload(categoryDtoResponse);
        return ResponseEntity.ok().body(serviceResponseDto);
    }

    @CrossOrigin
    @GetMapping("/all")
    public ResponseEntity<?> expenseCategories(){

        List<ExpenseCategoryBean> expenseCategoryList = expenseCategoryRepo.findAll();
        if(Objects.isNull(expenseCategoryList) || expenseCategoryList.size() < 1) {
            serviceResponseDto.setResponseCode("0N");
            serviceResponseDto.setResponseDesc(messageSource.getMessage("expense.category.not.defined", null, LocaleContextHolder.getLocale()));
            serviceResponseDto.setPayload(null);
            return ResponseEntity.ok().body(serviceResponseDto);
        }

        List<ExpenseCategoryDto> expenseCategoryDtoList = expenseCategoryList.stream()
                .map(user -> modelMapper.map(user, ExpenseCategoryDto.class))
                .collect(Collectors.toList());
        serviceResponseDto.setResponseCode(ExpenseServiceResponseCode.SUCCESS.getCode());
        serviceResponseDto.setResponseDesc(messageSource.getMessage("operation.success.desc", null, LocaleContextHolder.getLocale()));
        serviceResponseDto.setPayload(expenseCategoryDtoList);
        return ResponseEntity.ok().body(serviceResponseDto);
    }
}
