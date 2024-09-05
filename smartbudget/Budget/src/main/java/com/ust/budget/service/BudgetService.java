package com.ust.budget.service;


import com.ust.budget.exception.BudgetAlreadyExistsException;
import com.ust.budget.exception.BudgetNotFoundException;
import com.ust.budget.model.Budget;
import com.ust.budget.repository.BudgetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class BudgetService {

    @Autowired
    private BudgetRepository budgetRepository;

    public List<Budget> getBudgetByEmail(String email) {
        List<Budget> budgets = budgetRepository.findAllByEmail(email);
        if (budgets.isEmpty()) {
            throw new BudgetNotFoundException("No budgets found for email: " + email);
        }
        return budgets;
    }

    // Create new Budget
    public Budget createBudget(Budget budget) {
        String email = budget.getEmail();
        List<Budget> Budgetvalues = budgetRepository.findAllByEmail(email);

        if(Budgetvalues.stream().filter(s-> budget.getCategory().equalsIgnoreCase(s.getCategory())).count() != 0)
        {
           throw  new BudgetAlreadyExistsException("Budget already exists for this email id " + budget.getEmail());
        }

        return budgetRepository.save(budget);
    }

    //fetch ALL budgets
    public List<Budget> getAllBudgets() {
        return budgetRepository.findAll();
    }


    // Get Budget By ID
    public Budget getBudgetById(Long id) {
        return budgetRepository.findById(id)
                .orElseThrow(() -> new BudgetNotFoundException("Budget not found with ID: " + id));
    }


    // Get Budget By Category
    public Budget getBudgetByCategory(String category) {
        return budgetRepository.findByCategory(category)
                .orElseThrow(() -> new BudgetNotFoundException("Budget not found for category: " + category));

    }
    //Update existing Budget
    public Budget updateBudget(Long id, Budget updatedBudget) {
        Budget existingBudget = budgetRepository.findById(id)
                .orElseThrow(() -> new BudgetNotFoundException("Budget not found with ID: " + id));

        existingBudget.setCategory(updatedBudget.getCategory());
        existingBudget.setAmount(updatedBudget.getAmount());
        existingBudget.setStartDate(updatedBudget.getStartDate());
        existingBudget.setEndDate(updatedBudget.getEndDate());

        return budgetRepository.save(existingBudget);
    }
    
    
    public Budget updateBudgetByCategoryAndEmail(String email, String category, double Amount ) {
        Budget existingBudget = budgetRepository.findByEmailAndCategory(email, category)
                .orElseThrow(() -> new BudgetNotFoundException("Budget not found for email: " + email + " and category: " + category));

        existingBudget.setAmount(Amount);
//        existingBudget.setStartDate(updatedBudget.getStartDate());
//        existingBudget.setEndDate(updatedBudget.getEndDate());

        return budgetRepository.save(existingBudget);
    }
    


    public void deleteBudgetById(Long id) {
        Budget existingBudget = budgetRepository.findById(id)
                .orElseThrow(() -> new BudgetNotFoundException("Budget not found with ID: " + id));

        budgetRepository.delete(existingBudget);
    }




}