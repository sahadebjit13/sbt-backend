package com.ust.budget.controller;

import com.ust.budget.exception.BudgetNotFoundException;
import com.ust.budget.model.Budget;
import com.ust.budget.service.BudgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/budgets")
public class BudgetController {

    @Autowired
    private BudgetService budgetService;

    // Create a new Budget
    @PostMapping("/create")
    public ResponseEntity<Budget> createBudget(@RequestBody Budget budget) {
        Budget createdBudget = budgetService.createBudget(budget);
        return new ResponseEntity<>(createdBudget, HttpStatus.CREATED);
    }

    // Get all Budgets
    @GetMapping()
    public ResponseEntity<List<Budget>> getAllBudgets() {
        List<Budget> budgets = budgetService.getAllBudgets();
        return new ResponseEntity<>(budgets, HttpStatus.OK);
    }

    // Get a Budget by ID
    @GetMapping("/{id}")
    public ResponseEntity<Budget> getBudgetById(@PathVariable Long id) {
        Budget budget = budgetService.getBudgetById(id);
        return new ResponseEntity<>(budget, HttpStatus.OK);
    }

    // Get Budgets by Email
    @GetMapping("/email/{email}")
    public ResponseEntity<List<Budget>> getBudgetByEmail(@PathVariable String email) {
        List<Budget> budgets = budgetService.getBudgetByEmail(email);
        
        return new ResponseEntity<>(budgets, HttpStatus.OK);
    }

    // Get a Budget by Category
    @GetMapping("/category/{category}")
    public ResponseEntity<Budget> getBudgetByCategory(@PathVariable String category) {
        Budget budget = budgetService.getBudgetByCategory(category);
        return new ResponseEntity<>(budget, HttpStatus.OK);
    }
    
    //total by email
    @GetMapping("/emailtotal/{email}")
    public ResponseEntity<Double> getTotalBudgetByEmail(@PathVariable String email) {
        double totalAmount = budgetService.getTotalBudgetAmountByEmail(email);
        return new ResponseEntity<>(totalAmount, HttpStatus.OK);
    }

    // Update a Budget
    @PutMapping("/{id}")
    public ResponseEntity<Budget> updateBudget(@PathVariable Long id, @RequestBody Budget updatedBudget) {
        Budget budget = budgetService.updateBudget(id, updatedBudget);
        return new ResponseEntity<>(budget, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<Budget> updateBudgetByCategoryAndEmail(
            @RequestParam String email,
            @RequestParam String category,
            @RequestParam double Amount) {
        try {
            Budget budget = budgetService.updateBudgetByCategoryAndEmail(email, category, Amount);
            return new ResponseEntity<>(budget, HttpStatus.OK);
        } catch (BudgetNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    // Delete a Budget by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBudgetById(@PathVariable Long id) {
        budgetService.deleteBudgetById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
