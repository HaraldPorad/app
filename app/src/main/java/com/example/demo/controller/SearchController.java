package com.example.demo.controller;

import java.util.List;
import java.util.ArrayList;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.ReviewModel.*;
import com.example.demo.model.ConsultantModel.*;
import com.example.demo.model.SalesPeopleModel.*;
import com.example.demo.model.ProjectModel.*;

@Controller
public class SearchController {

    private final ConsultantService consultantService;
    private final SalesService salesService;
    private final ProjectService projectService;
    private final ReviewService reviewService;


    public SearchController(ConsultantService consultantService, 
                            SalesService salesService,
                            ProjectService projectService,
                            ReviewService reviewService) {

        this.consultantService = consultantService;
        this.salesService = salesService;
        this.projectService = projectService;                                               
        this.reviewService = reviewService;
    }

    @GetMapping("/search")
    public String Search(
            @RequestParam(required = false) String customer, // filtrera alla reviews en viss kund har
            @RequestParam(required = false) String consultant, // filtrera alla reviews en viss konsult har
            Model model) {

            model.addAttribute("reviews", reviewService.searchReviews(customer, consultant));
            model.addAttribute("kund", customer);
            model.addAttribute("konsult", consultant);

        return "search";
    }
}