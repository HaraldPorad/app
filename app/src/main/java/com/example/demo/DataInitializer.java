package com.example.demo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.demo.model.ConsultantModel.Consultant;
import com.example.demo.model.ConsultantModel.ConsultantRepository;
import com.example.demo.model.ProjectModel.Project;
import com.example.demo.model.ProjectModel.ProjectRepository;
import com.example.demo.model.ReviewModel.Review;
import com.example.demo.model.ReviewModel.ReviewRepository;
import com.example.demo.model.SalesPeopleModel.SalesPerson;
import com.example.demo.model.SalesPeopleModel.SalesRepository;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initDatabase(ProjectRepository projectRepository,
                                  ConsultantRepository consultantRepository,
                                  SalesRepository salesRepository,
                                  ReviewRepository reviewRepository) {
        return args -> {
            if (reviewRepository.count() == 0) {
                Random random = new Random();

                List<String> customerNames = List.of(
                    "Volvo", "Spotify", "IKEA", "Klarna", "Scania", "H&M", "Ericsson", "SEB",
                    "Swedbank", "Nordea", "Handelsbanken", "Telia", "Electrolux", "King", 
                    "Vattenfall", "AstraZeneca", "Storytel", "Kry", "Atlas Copco", "ICA"
                );

                List<String> consultantNames = List.of(
                    "Anna Svensson", "Erik Lindqvist", "Sara Nilsson", "Johan Berg", "Maria Ek", "Karl Karlsson",
                    "Mikael Andersson", "Sofia Hedlund", "Oskar Blom", "Frida Sandström", 
                    "Alexander Nyberg", "Elinor Falk", "Marcus Engström", "Linnea Sjöberg"
                );

                List<String> salesNames = List.of(
                    "Lars Holm", "Elin Ström", "Fredrik Dahl", "Emma Lund",
                    "Patrik Nyström", "Camilla Björk", "Jonas Wallin", "Isabella Forsberg"
                );

                List<String> comments = List.of(
                    "Över förväntan!",
                    "Mycket bra samarbete och tydlig kommunikation.",
                    "Bra leverans trots tajt deadline.",
                    "Fungerade helt okej, viss förbättringspotential.",
                    "Fantastiskt engagemang från hela teamet.",
                    "Professionellt bemötande och hög teknisk kompetens.",
                    "Snabb respons och smidig problemlösning.",
                    "Tydlig struktur och bra uppföljning genom hela projektet.",
                    "Levererade enligt specifikation och inom budget.",
                    "Kunde ha varit snabbare återkoppling i början, men bra slutresultat.",
                    "Imponerande initiativförmåga och proaktivt arbetssätt.",
                    "Mycket nöjd med kvaliteten på slutprodukten.",
                    "Trevliga att arbeta med och lätta att samarbeta med.",
                    "Skulle definitivt anlita eller rekommendera igen."
                );

                // 1. Seed unique SalesPeople
                List<SalesPerson> savedSales = new ArrayList<>();
                for (String name : salesNames) {
                    savedSales.add(salesRepository.save(new SalesPerson(name)));
                }

                // 2. Seed Projects (2-3 projects per sales person across the customer list)
                List<Project> savedProjects = new ArrayList<>();
                for (String cust : customerNames) {
                    SalesPerson assignedSales = savedSales.get(random.nextInt(savedSales.size()));
                    savedProjects.add(projectRepository.save(new Project(cust, assignedSales)));
                }

                // 3. Seed Consultants (assigned to projects; manager matches project's salesperson)
                List<Consultant> savedConsultants = new ArrayList<>();
                for (String consName : consultantNames) {
                    Project assignedProj = savedProjects.get(random.nextInt(savedProjects.size()));
                    SalesPerson manager = assignedProj.getSalesPerson();
                    savedConsultants.add(consultantRepository.save(new Consultant(consName, manager, assignedProj)));
                }
                LocalDate startDate = LocalDate.of(2025, 1, 1);
                long start = startDate.toEpochDay();

                LocalDate endDate = LocalDate.now();
                long end = endDate.toEpochDay();
                
                
                // 4. Seed Reviews linking the generated consultants and projects
                for (int i = 0; i < 40; i++) {

                    long randomEpochDate = ThreadLocalRandom.current().nextLong(start, end);
                    Consultant c = savedConsultants.get(random.nextInt(savedConsultants.size()));
                    Project p = c.getProject();

                    Review r = new Review();
                    r.setProject(p);
                    r.setConsultant(c);

                    r.setConsultantInformed(random.nextBoolean());

                    r.setDate(LocalDate.ofEpochDay(randomEpochDate));

                    r.setResultScore(random.nextInt(5) + 1);
                    r.setResponsibilityScore(random.nextInt(5) + 1);
                    r.setSimplicityScore(random.nextInt(5) + 1);
                    r.setJoyScore(random.nextInt(5) + 1);

                    r.setResult(comments.get(random.nextInt(comments.size())));
                    r.setResponsibility(comments.get(random.nextInt(comments.size())));
                    r.setSimplicity(comments.get(random.nextInt(comments.size())));
                    r.setJoy(comments.get(random.nextInt(comments.size())));

                    reviewRepository.save(r);
                }

                System.out.println("Seeded database with relational hierarchy (sales, multiple projects, consultants, reviews).");
            }
        };
    }
}