package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.model.ReviewModel.Review;
import com.example.demo.model.ReviewModel.ReviewService;

@Controller
public class FormController { // controller class for form page
    private final ReviewService reviewService;

    public FormController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("/form")
    public String showForm(@ModelAttribute Review review, 
                            BindingResult bindingResult, Model model) {
                                
        model.addAttribute("review", review);
        return "form";
    }

    @PostMapping("/form/add")
    public String addNewReview(@ModelAttribute Review review, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "form";
        }
        reviewService.saveReview(review);
        return "redirect:/submitted";
    }
}