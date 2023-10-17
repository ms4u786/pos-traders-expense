package com.msm.service.expense.core.service;


import com.msm.service.expense.common.dto.ExpenseDto;
import com.msm.service.expense.common.dto.ServiceResponseDto;
import com.msm.service.expense.common.util.ExpenseServiceResponseCode;
import com.msm.service.expense.core.entity.ExpenseEntity;
import com.msm.service.expense.core.entity.ExpenseCategoryEntity;
import com.msm.service.expense.core.repo.ExpenseCategoryRepo;
import com.msm.service.expense.core.repo.ExpenseRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ExpenseServiceImpl implements ExpenseService {

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    ExpenseRepo expenseRepo;

    @Autowired
    ServiceResponseDto serviceResponseDto;

    @Autowired
    ExpenseCategoryRepo expenseCategoryRepo;

    @Autowired
    MessageSource messageSource;

    @Override
    public ServiceResponseDto saveExpense(ExpenseDto model) {

        ExpenseEntity expenseRequest = modelMapper.map(model, ExpenseEntity.class);

        if(Objects.isNull(expenseRequest.getExpenseCategoryEntity())){
            serviceResponseDto.setPayload(null);
            serviceResponseDto.setResponseCode(ExpenseServiceResponseCode.InValid.getCode());
            serviceResponseDto.setResponseDesc(messageSource.getMessage("expense.category.id.invalid", null, LocaleContextHolder.getLocale()));
            return serviceResponseDto;
        }

        Optional<ExpenseCategoryEntity> expenseCategory = expenseCategoryRepo.findById(expenseRequest.getExpenseCategoryEntity().getExpenseCategoryId());
        if(!expenseCategory.isPresent()){
            serviceResponseDto.setResponseDesc(messageSource.getMessage("expense.category.id.invalid", null, LocaleContextHolder.getLocale()));
            serviceResponseDto.setPayload(null);
            serviceResponseDto.setResponseCode(ExpenseServiceResponseCode.InValid.getCode());
            return serviceResponseDto;
        }

        expenseRequest.setCreatedAt(new Date());
        if(null != expenseRequest.getExpenseId() && expenseRequest.getExpenseId() >= 0){
            Optional<ExpenseEntity> selectedExpenseOpt = expenseRepo.findById(expenseRequest.getExpenseId());
            if(selectedExpenseOpt.isPresent()){
                expenseRequest.setCreatedAt(selectedExpenseOpt.get().getCreatedAt());
                expenseRequest.setLastUpdatedAt(new Date());
            } else {
                serviceResponseDto.setPayload(null);
                serviceResponseDto.setResponseCode(ExpenseServiceResponseCode.ERROR.getCode());
                serviceResponseDto.setResponseDesc(messageSource.getMessage("error.general", null, LocaleContextHolder.getLocale()));
                return serviceResponseDto;
            }
        }
        expenseRequest.setExpenseCategoryEntity(expenseCategory.get());

        ExpenseEntity expenseNew = expenseRepo.save(expenseRequest);
        if(!Objects.isNull(expenseNew) && expenseNew.getExpenseId() > 0){
            ExpenseDto expenseDtoResponse = modelMapper.map(expenseNew, ExpenseDto.class);

            serviceResponseDto.setResponseCode(ExpenseServiceResponseCode.SUCCESS.getCode());
            serviceResponseDto.setResponseDesc(messageSource.getMessage("expense.resource.created.success", null, LocaleContextHolder.getLocale()));
            serviceResponseDto.setPayload(expenseDtoResponse);
            return serviceResponseDto;
        }

        serviceResponseDto.setPayload(null);
        serviceResponseDto.setResponseDesc(messageSource.getMessage("error.general", null, LocaleContextHolder.getLocale()));
        serviceResponseDto.setResponseCode(ExpenseServiceResponseCode.ERROR.getCode());
        return serviceResponseDto;
    }

    @Override
    public ServiceResponseDto getExpensesCurrentMonth() {

        LocalDate localDate = LocalDate.now();
        Date dateStart = Date.from(localDate.withDayOfMonth(1).atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date dateEnd = Date.from(localDate.withDayOfMonth(localDate.lengthOfMonth()).atStartOfDay(ZoneId.systemDefault()).toInstant());

        Optional<List<ExpenseEntity>> expenseOpt = expenseRepo.findByExpenseDateBetweenOrderByExpenseDateDesc(dateStart, dateEnd);

        if(!expenseOpt.isPresent()){
            serviceResponseDto.setResponseCode(ExpenseServiceResponseCode.NoRecord.getCode());
            serviceResponseDto.setResponseDesc(messageSource.getMessage("expense.category.no.resource", null, LocaleContextHolder.getLocale()));
            serviceResponseDto.setPayload(null);
            return serviceResponseDto;
        }

        List<ExpenseDto> expenseCurrentDtoList = expenseOpt.get().stream()
                .map(expenseBean -> modelMapper.map(expenseBean, ExpenseDto.class))
                .collect(Collectors.toList());

        serviceResponseDto.setResponseCode(ExpenseServiceResponseCode.SUCCESS.getCode());
        serviceResponseDto.setResponseDesc(messageSource.getMessage("operation.success.desc", null, LocaleContextHolder.getLocale()));
        serviceResponseDto.setPayload(expenseCurrentDtoList);
        return serviceResponseDto;
    }

    @Override
    public ServiceResponseDto getExpensesBetweenDate(String startDate, String endDate, String expenseCategory) {

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

        Optional<List<ExpenseEntity>> expensesOpt = null;

        if(!Objects.isNull(expenseCategory) && !ObjectUtils.isEmpty(expenseCategory)){
            List<String> categoriesStringList = Arrays.asList(expenseCategory.split("\\s*,\\s*"));
            List<Integer> categoriesList = categoriesStringList.stream().map(Integer::parseInt).collect(Collectors.toList());
            Iterable<Integer> iterable = (Iterable<Integer>)categoriesList;
            List<ExpenseCategoryEntity> expenseCategoryBeansOpt = expenseCategoryRepo.findAllById(iterable);
            if(!Objects.isNull(expenseCategoryBeansOpt) && !ObjectUtils.isEmpty(expenseCategoryBeansOpt)){
                expensesOpt = expenseRepo.findByExpenseDateBetweenAndExpenseCategoryEntityInOrderByExpenseDateDesc(dateStart, dateEnd, expenseCategoryBeansOpt);
            } else {
                expensesOpt = expenseRepo.findByExpenseDateBetweenOrderByExpenseDateDesc(dateStart, dateEnd);
            }
        } else {
            expensesOpt = expenseRepo.findByExpenseDateBetweenOrderByExpenseDateDesc(dateStart, dateEnd);
        }

        if(!expensesOpt.isPresent()){
            serviceResponseDto.setResponseDesc(messageSource.getMessage("expense.category.no.resource", null, LocaleContextHolder.getLocale()));
            serviceResponseDto.setResponseCode(ExpenseServiceResponseCode.NoRecord.getCode());
            serviceResponseDto.setPayload(null);
            return serviceResponseDto;
        }

        serviceResponseDto.setResponseDesc(messageSource.getMessage("operation.success.desc", null, LocaleContextHolder.getLocale()));
        serviceResponseDto.setResponseCode(ExpenseServiceResponseCode.SUCCESS.getCode());
        List<ExpenseDto> expenseBetweenDtoList = expensesOpt.get().stream()
                .map(expenseBean -> modelMapper.map(expenseBean, ExpenseDto.class))
                .collect(Collectors.toList());
        serviceResponseDto.setPayload(expenseBetweenDtoList);
        return serviceResponseDto;
    }

    @Override
    public ServiceResponseDto getExpenseById(Integer expenseId) {

        Optional<ExpenseEntity> expenseOpt = expenseRepo.findById(expenseId);
        if(!expenseOpt.isPresent()){
            serviceResponseDto.setPayload(null);
            serviceResponseDto.setResponseCode(ExpenseServiceResponseCode.NoRecord.getCode());
            serviceResponseDto.setResponseDesc(messageSource.getMessage("expense.category.no.resource", null, LocaleContextHolder.getLocale()));
            return serviceResponseDto;
        }
        serviceResponseDto.setResponseCode(ExpenseServiceResponseCode.SUCCESS.getCode());
        serviceResponseDto.setResponseDesc(messageSource.getMessage("operation.success.desc", null, LocaleContextHolder.getLocale()));
        serviceResponseDto.setPayload(expenseOpt.get());
        return serviceResponseDto;
    }

    @Override
    public ServiceResponseDto getExpenseByCategoryId(Integer categoryId) {
        Optional<ExpenseCategoryEntity> expensesByCategoryOpt = expenseCategoryRepo.findById(categoryId);
        if(!expensesByCategoryOpt.isPresent()){
            serviceResponseDto.setResponseCode(ExpenseServiceResponseCode.InValid.getCode());
            serviceResponseDto.setPayload(null);
            serviceResponseDto.setResponseDesc(messageSource.getMessage("expense.id.invalid", null, LocaleContextHolder.getLocale()));
            return serviceResponseDto;
        }

        Optional<List<ExpenseEntity>> expenseByCategoryListOpt = expenseRepo.findByExpenseCategoryEntityOrderByExpenseDateDesc(expensesByCategoryOpt.get());
        if(!expenseByCategoryListOpt.isPresent()){
            serviceResponseDto.setResponseDesc(messageSource.getMessage("expense.no.resource", null, LocaleContextHolder.getLocale()));
            serviceResponseDto.setResponseCode(ExpenseServiceResponseCode.NoRecord.getCode());
            serviceResponseDto.setPayload(null);
            return serviceResponseDto;
        }

        List<ExpenseDto> expenseCategoryDtoList = expenseByCategoryListOpt.get().stream()
                .map(expenseBean -> modelMapper.map(expenseBean, ExpenseDto.class))
                .collect(Collectors.toList());
        serviceResponseDto.setPayload(expenseCategoryDtoList);
        serviceResponseDto.setResponseCode(ExpenseServiceResponseCode.SUCCESS.getCode());
        serviceResponseDto.setResponseDesc(messageSource.getMessage("operation.success.desc", null, LocaleContextHolder.getLocale()));
        return serviceResponseDto;
    }

    @Override
    public ServiceResponseDto deleteExpenseById(Integer expenseId) {

        Optional<ExpenseEntity> expenseOpt = expenseRepo.findById(expenseId);
        if(!expenseOpt.isPresent()){
            serviceResponseDto.setResponseCode(ExpenseServiceResponseCode.InValid.getCode());
            serviceResponseDto.setResponseDesc(messageSource.getMessage("expense.id.invalid", null, LocaleContextHolder.getLocale()));
            serviceResponseDto.setPayload(null);
            return serviceResponseDto;
        }

        expenseRepo.deleteById(expenseId);

        serviceResponseDto.setResponseCode(ExpenseServiceResponseCode.SUCCESS.getCode());
        serviceResponseDto.setResponseDesc(messageSource.getMessage("resource.deleted.success", null, LocaleContextHolder.getLocale()));
        serviceResponseDto.setPayload(null);
        return serviceResponseDto;
    }
}
