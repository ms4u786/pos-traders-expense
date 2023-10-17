package com.msm.service.expense.core.service;

import com.msm.service.expense.common.dto.ExpenseCategoryDto;
import com.msm.service.expense.common.dto.ServiceResponseDto;
import com.msm.service.expense.common.util.ExpenseServiceResponseCode;
import com.msm.service.expense.core.entity.ExpenseCategoryEntity;
import com.msm.service.expense.core.repo.ExpenseCategoryRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;


import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ExpenseCategoryServiceImpl implements ExpenseCategoryService {

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    ServiceResponseDto serviceResponseDto;

    @Autowired
    ExpenseCategoryRepo expenseCategoryRepo;

    @Autowired
    MessageSource messageSource;

    @Override
    public ServiceResponseDto newExpenseCategory(ExpenseCategoryDto model) {

        ExpenseCategoryEntity categoryBeanRequest = modelMapper.map(model, ExpenseCategoryEntity.class);

        Optional<ExpenseCategoryEntity> expenseCategory = expenseCategoryRepo.findByExpenseCategoryTitle(categoryBeanRequest.getExpenseCategoryTitle());
        if(expenseCategory.isPresent()){
            serviceResponseDto.setResponseCode("0A");
            serviceResponseDto.setResponseDesc(messageSource.getMessage("expense.category.already.add", null, LocaleContextHolder.getLocale()));
            serviceResponseDto.setPayload(null);
            return serviceResponseDto;
        }

        categoryBeanRequest.setCreatedAt(new Date());
        ExpenseCategoryEntity expenseCategoryNew = expenseCategoryRepo.save(categoryBeanRequest);
        if(Objects.isNull(expenseCategoryNew) || expenseCategoryNew.getExpenseCategoryId() <= 0){
            serviceResponseDto.setResponseCode(ExpenseServiceResponseCode.ERROR.getCode());
            serviceResponseDto.setResponseDesc(messageSource.getMessage("error.general", null, LocaleContextHolder.getLocale()));
            serviceResponseDto.setPayload(null);
            return serviceResponseDto;
        }

        serviceResponseDto.setResponseCode(ExpenseServiceResponseCode.SUCCESS.getCode());
        serviceResponseDto.setResponseDesc(messageSource.getMessage("operation.success.desc", null, LocaleContextHolder.getLocale()));
        ExpenseCategoryDto categoryDtoResponse = modelMapper.map(expenseCategoryNew, ExpenseCategoryDto.class);
        serviceResponseDto.setPayload(categoryDtoResponse);
        return serviceResponseDto;
    }

    @Override
    public ServiceResponseDto updateExpenseCategory(ExpenseCategoryDto model) {

        ExpenseCategoryEntity categoryBeanRequest = modelMapper.map(model, ExpenseCategoryEntity.class);

        Optional<ExpenseCategoryEntity> expenseCategory = expenseCategoryRepo.findById(categoryBeanRequest.getExpenseCategoryId());
        if(!expenseCategory.isPresent()){
            serviceResponseDto.setResponseCode(ExpenseServiceResponseCode.InValid.getCode());
            serviceResponseDto.setPayload(null);
            serviceResponseDto.setResponseDesc(messageSource.getMessage("expense.category.id.invalid", null, LocaleContextHolder.getLocale()));
            return serviceResponseDto;
        }

        categoryBeanRequest.setLastUpdatedAt(new Date());
        ExpenseCategoryEntity expenseCategoryUpdated = expenseCategoryRepo.save(categoryBeanRequest);
        if(Objects.isNull(expenseCategoryUpdated)){
            serviceResponseDto.setResponseCode(ExpenseServiceResponseCode.ERROR.getCode());
            serviceResponseDto.setResponseDesc(messageSource.getMessage("error.general", null, LocaleContextHolder.getLocale()));
            serviceResponseDto.setPayload(null);
            return serviceResponseDto;
        }

        ExpenseCategoryDto categoryDtoResponse = modelMapper.map(expenseCategoryUpdated, ExpenseCategoryDto.class);
        serviceResponseDto.setResponseCode(ExpenseServiceResponseCode.SUCCESS.getCode());
        serviceResponseDto.setResponseDesc(messageSource.getMessage("operation.success.desc", null, LocaleContextHolder.getLocale()));
        serviceResponseDto.setPayload(categoryDtoResponse);
        return serviceResponseDto;
    }

    @Override
    public ServiceResponseDto getExpenseCategoryById(Integer expenseCategoryId) {

        Optional<ExpenseCategoryEntity> expenseCategoryOpt = expenseCategoryRepo.findById(expenseCategoryId);
        if(!expenseCategoryOpt.isPresent()){
            serviceResponseDto.setResponseCode(ExpenseServiceResponseCode.InValid.getCode());
            serviceResponseDto.setResponseDesc(messageSource.getMessage("expense.category.id.invalid", null, LocaleContextHolder.getLocale()));
            serviceResponseDto.setPayload(null);
            return serviceResponseDto;
        }

        ExpenseCategoryDto categoryDtoResponse = modelMapper.map(expenseCategoryOpt.get(), ExpenseCategoryDto.class);
        serviceResponseDto.setResponseCode(ExpenseServiceResponseCode.SUCCESS.getCode());
        serviceResponseDto.setResponseDesc(messageSource.getMessage("operation.success.desc", null, LocaleContextHolder.getLocale()));
        serviceResponseDto.setPayload(categoryDtoResponse);
        return serviceResponseDto;
    }

    @Override
    public ServiceResponseDto getAllExpenseCategories() {
        List<ExpenseCategoryEntity> expenseCategoryList = expenseCategoryRepo.findAll();
        if(Objects.isNull(expenseCategoryList) || expenseCategoryList.size() < 1) {
            serviceResponseDto.setResponseCode("0N");
            serviceResponseDto.setResponseDesc(messageSource.getMessage("expense.category.not.defined", null, LocaleContextHolder.getLocale()));
            serviceResponseDto.setPayload(null);
            return serviceResponseDto;
        }

        List<ExpenseCategoryDto> expenseCategoryDtoList = expenseCategoryList.stream()
                .map(user -> modelMapper.map(user, ExpenseCategoryDto.class))
                .collect(Collectors.toList());
        serviceResponseDto.setResponseCode(ExpenseServiceResponseCode.SUCCESS.getCode());
        serviceResponseDto.setResponseDesc(messageSource.getMessage("operation.success.desc", null, LocaleContextHolder.getLocale()));
        serviceResponseDto.setPayload(expenseCategoryDtoList);
        return serviceResponseDto;
    }
}
