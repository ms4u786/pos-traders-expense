package pos.traders.expense.postradersexpense.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import pos.traders.expense.postradersexpense.beans.ExpenseBean;
import pos.traders.expense.postradersexpense.beans.ExpenseCategoryBean;
import pos.traders.expense.postradersexpense.dto.ExpenseDto;
import pos.traders.expense.postradersexpense.dto.ServiceResponseDto;
import pos.traders.expense.postradersexpense.repo.ExpenseCategoryRepo;
import pos.traders.expense.postradersexpense.repo.ExpenseRepo;

import java.net.URI;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/expense")
public class ExpenseController {

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    ExpenseRepo expenseRepo;

    @Autowired
    ServiceResponseDto serviceResponseDto;

    @Autowired
    ExpenseCategoryRepo expenseCategoryRepo;

    @PostMapping("/save")
    public ResponseEntity<?> newExpense(@RequestBody ExpenseDto model){

        ExpenseBean expenseRequest = modelMapper.map(model, ExpenseBean.class);

        if(Objects.isNull(expenseRequest.getExpenseCategoryBean())){
            serviceResponseDto.setResponseCode("0C");
            serviceResponseDto.setResponseDesc("Expense category object is null!");
            serviceResponseDto.setPayload(null);
            return ResponseEntity.ok().body(serviceResponseDto);
        }

        Optional<ExpenseCategoryBean> expenseCategory = expenseCategoryRepo.findById(expenseRequest.getExpenseCategoryBean().getExpenseCategoryId());
        if(!expenseCategory.isPresent()){
            serviceResponseDto.setResponseCode("0C");
            serviceResponseDto.setResponseDesc("Expense category is invalid!");
            serviceResponseDto.setPayload(null);
            return ResponseEntity.ok().body(serviceResponseDto);
        }

        expenseRequest.setCreatedAt(new Date());
        if(null != expenseRequest.getExpenseId() && expenseRequest.getExpenseId() >= 0){
            Optional<ExpenseBean> selectedExpenseOpt = expenseRepo.findById(expenseRequest.getExpenseId());
            if(selectedExpenseOpt.isPresent()){
                expenseRequest.setCreatedAt(selectedExpenseOpt.get().getCreatedAt());
                expenseRequest.setLastUpdatedAt(new Date());
            } else {
                serviceResponseDto.setResponseCode("0E");
                serviceResponseDto.setResponseDesc("ERROR");
                serviceResponseDto.setPayload(null);
                return ResponseEntity.ok().body(serviceResponseDto);
            }
        }
        expenseRequest.setExpenseCategoryBean(expenseCategory.get());

        ExpenseBean expenseNew = expenseRepo.save(expenseRequest);
        if(!Objects.isNull(expenseNew) && expenseNew.getExpenseId() > 0){
            ExpenseDto expenseDtoResponse = modelMapper.map(expenseNew, ExpenseDto.class);
            URI expenseCategoryUri = URI.create("/id/" + expenseDtoResponse.getExpenseId());
            serviceResponseDto.setResponseCode("00");
            serviceResponseDto.setResponseDesc("SUCCESS");
            serviceResponseDto.setPayload(expenseDtoResponse);
            return ResponseEntity.created(expenseCategoryUri).body(serviceResponseDto);
        }

        serviceResponseDto.setResponseCode("0E");
        serviceResponseDto.setResponseDesc("ERROR");
        serviceResponseDto.setPayload(null);
        return ResponseEntity.ok().body(serviceResponseDto);
    }

    @GetMapping("/current")
    public ResponseEntity<?> expensesCurrentMonth(){

        LocalDate localDate = LocalDate.now();
        Date dateStart = Date.from(localDate.withDayOfMonth(1).atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date dateEnd = Date.from(localDate.withDayOfMonth(localDate.lengthOfMonth()).atStartOfDay(ZoneId.systemDefault()).toInstant());
        Optional<List<ExpenseBean>> expenseOpt = expenseRepo.findByExpenseDateBetweenOrderByExpenseDateDesc(dateStart, dateEnd);
        if(expenseOpt.isPresent()){
            serviceResponseDto.setResponseCode("00");
            serviceResponseDto.setResponseDesc("Success");
            List<ExpenseDto> expenseCurrentDtoList = expenseOpt.get().stream()
                    .map(expenseBean -> modelMapper.map(expenseBean, ExpenseDto.class))
                    .collect(Collectors.toList());
            serviceResponseDto.setPayload(expenseCurrentDtoList);
            return ResponseEntity.ok().body(serviceResponseDto);
        }
        serviceResponseDto.setResponseCode("0N");
        serviceResponseDto.setResponseDesc("No Record Found!");
        serviceResponseDto.setPayload(null);
        return ResponseEntity.ok().body(serviceResponseDto);
    }

    @GetMapping("/between")
    public ResponseEntity<?> expensesBetweenDate(@RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate, @RequestParam("expenseCategory") String expenseCategory){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date dateStart, dateEnd = null;
        try{
            dateStart = simpleDateFormat.parse(startDate);
            dateEnd = simpleDateFormat.parse(endDate);
        } catch (Exception ex){
            LocalDate localDate = LocalDate.now();
            dateStart = Date.from(localDate.withDayOfMonth(1).atStartOfDay(ZoneId.systemDefault()).toInstant());
            dateEnd = Date.from(localDate.withDayOfMonth(localDate.lengthOfMonth()).atStartOfDay(ZoneId.systemDefault()).toInstant());
        }

        Optional<List<ExpenseBean>> expensesOpt = null;

        if(!Objects.isNull(expenseCategory) && !ObjectUtils.isEmpty(expenseCategory)){
            List<String> categoriesStringList = Arrays.asList(expenseCategory.split("\\s*,\\s*"));
            List<Integer> categoriesList = categoriesStringList.stream().map(Integer::parseInt).collect(Collectors.toList());
            Iterable<Integer> iterable = (Iterable<Integer>)categoriesList;
            List<ExpenseCategoryBean> expenseCategoryBeansOpt = expenseCategoryRepo.findAllById(iterable);
            if(!Objects.isNull(expenseCategoryBeansOpt) && !ObjectUtils.isEmpty(expenseCategoryBeansOpt)){
                expensesOpt = expenseRepo.findByExpenseDateBetweenAndExpenseCategoryBeanInOrderByExpenseDateDesc(dateStart, dateEnd, expenseCategoryBeansOpt);
            } else {
                expensesOpt = expenseRepo.findByExpenseDateBetweenOrderByExpenseDateDesc(dateStart, dateEnd);
            }
        } else {
            expensesOpt = expenseRepo.findByExpenseDateBetweenOrderByExpenseDateDesc(dateStart, dateEnd);
        }

        if(expensesOpt.isPresent()){
            serviceResponseDto.setResponseDesc("Success");
            serviceResponseDto.setResponseCode("00");
            List<ExpenseDto> expenseBetweenDtoList = expensesOpt.get().stream()
                    .map(expenseBean -> modelMapper.map(expenseBean, ExpenseDto.class))
                    .collect(Collectors.toList());
            serviceResponseDto.setPayload(expenseBetweenDtoList);
            return ResponseEntity.ok().body(serviceResponseDto);
        }
        serviceResponseDto.setResponseCode("0N");
        serviceResponseDto.setResponseDesc("No Record Found!");
        serviceResponseDto.setPayload(null);
        return ResponseEntity.ok().body(serviceResponseDto);
    }

    @GetMapping("/id/{expenseId}")
    public ResponseEntity<?> expenseById(@PathVariable("expenseId") Integer expenseId){
        Optional<ExpenseBean> expenseOpt = expenseRepo.findById(expenseId);
        if(!expenseOpt.isPresent()){
            serviceResponseDto.setResponseCode("0N");
            serviceResponseDto.setResponseDesc("No Record Found!");
            serviceResponseDto.setPayload(null);
            return ResponseEntity.ok().body(serviceResponseDto);
        }
        serviceResponseDto.setResponseCode("00");
        serviceResponseDto.setResponseDesc("Success");
        serviceResponseDto.setPayload(expenseOpt.get());
        return ResponseEntity.ok().body(serviceResponseDto);
    }

    @GetMapping("/delete/id/{expenseId}")
    public ResponseEntity<?> expenseDeleteById(@PathVariable("expenseId") Integer expenseId){
        Optional<ExpenseBean> expenseOpt = expenseRepo.findById(expenseId);
        if(!expenseOpt.isPresent()){
            serviceResponseDto.setResponseCode("0N");
            serviceResponseDto.setResponseDesc("No Record Found!");
            serviceResponseDto.setPayload(null);
            return ResponseEntity.ok().body(serviceResponseDto);
        }

        expenseRepo.deleteById(expenseId);

        serviceResponseDto.setResponseCode("00");
        serviceResponseDto.setResponseDesc("Deleted");
        serviceResponseDto.setPayload(null);
        return ResponseEntity.ok().body(serviceResponseDto);
    }

    @GetMapping("/by/category/{categoryId}")
    public ResponseEntity<?> expenseByCategoryId(@PathVariable("categoryId") Integer categoryId){
        Optional<ExpenseCategoryBean> expensesByCategoryOpt = expenseCategoryRepo.findById(categoryId);
        if(!expensesByCategoryOpt.isPresent()){
            serviceResponseDto.setResponseCode("0I");
            serviceResponseDto.setResponseDesc("Expense category is invalid!");
            serviceResponseDto.setPayload(null);
            return ResponseEntity.ok().body(serviceResponseDto);
        }

        Optional<List<ExpenseBean>> expenseByCategoryListOpt = expenseRepo.findByExpenseCategoryBeanOrderByExpenseDateDesc(expensesByCategoryOpt.get());
        if(!expenseByCategoryListOpt.isPresent()){
            serviceResponseDto.setResponseDesc("No Record Found!");
            serviceResponseDto.setResponseCode("0N");
            serviceResponseDto.setPayload(null);
            return ResponseEntity.ok().body(serviceResponseDto);
        }

        List<ExpenseDto> expenseCategoryDtoList = expenseByCategoryListOpt.get().stream()
                .map(expenseBean -> modelMapper.map(expenseBean, ExpenseDto.class))
                .collect(Collectors.toList());
        serviceResponseDto.setResponseCode("00");
        serviceResponseDto.setResponseDesc("Success");
        serviceResponseDto.setPayload(expenseCategoryDtoList);
        return ResponseEntity.ok().body(serviceResponseDto);
    }

}
