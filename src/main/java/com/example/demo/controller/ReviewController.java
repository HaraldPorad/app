package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.model.ReviewModel.ReviewRepository;

@Controller
public class ReviewController {
    
    private final ReviewRepository repository;
    
    public ReviewController(ReviewRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/reviews")
    public String showAllReviews(Model model) {
        model.addAttribute("reviews", repository.findAll());
        return "reviews";
    }
}