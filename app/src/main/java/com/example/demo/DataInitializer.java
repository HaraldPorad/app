package com.example.demo;

import java.util.List;
import java.util.Random;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.demo.model.ConsultantModel.*;
import com.example.demo.model.ProjectModel.*;
import com.example.demo.model.ReviewModel.*;
import com.example.demo.model.SalesPeopleModel.*;


@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initDatabase(ProjectRepository projectRepository,
                                    ConsultantRepository consultantRepository,
                                    SalesRepository salesRepository,
                                    ReviewRepository reviewRepository) { 
        return args -> {
            // Only seed data if the database is currently empty
            if (reviewRepository.count() == 0) {
                Random random = new Random();

            List<String> customers = List.of(
                "Volvo", "Spotify", "IKEA", "Klarna", "Scania", "H&M", "Ericsson", "SEB",
                "Swedbank", "Nordea", "Handelsbanken", "Telia", "Electrolux", "King", 
                "Vattenfall", "AstraZeneca", "Storytel", "Kry", "Atlas Copco", "ICA"
            );

            List<String> consultants = List.of(
                "Anna Svensson", "Erik Lindqvist", "Sara Nilsson", "Johan Berg", "Maria Ek", "Karl Karlsson",
                "Mikael Andersson", "Sofia Hedlund", "Oskar Blom", "Frida Sandström", 
                "Alexander Nyberg", "Elinor Falk", "Marcus Engström", "Linnea Sjöberg"
            );

            List<String> salesPeople = List.of(
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

                for (int i = 0; i < 25; i++) {

                    SalesPerson s = new SalesPerson(); // Create sales with random name and save to salesrepo

                    s.setName(salesPeople.get(random.nextInt(salesPeople.size())));
                    salesRepository.save(s);

                    Consultant c = new Consultant(); // Create consultant and set name, manager and save to repo
                    c.setName(consultants.get(random.nextInt(consultants.size())));
                    c.setManager(s);
                    consultantRepository.save(c);

                    Project p = new Project(); // Create project, set customer, salesperson and save to repo
                    p.setCustomer(customers.get(random.nextInt(customers.size())));
                    p.setSalesPerson(s);

                    projectRepository.save(p);

                    c.setProject(p); //assign consultant to project
 
                    // Create and set review parameters
                    Review r = new Review();
                    r.setProject(p);
                    r.setConsultant(c);
                    r.setConsultantInformed(random.nextBoolean());

                    // Random scores between 1 and 5
                    r.setResultScore(random.nextInt(5) + 1);
                    r.setResponsibilityScore(random.nextInt(5) + 1);
                    r.setSimplicityScore(random.nextInt(5) + 1);
                    r.setJoyScore(random.nextInt(5) + 1);

                    // Random comments
                    r.setResult(comments.get(random.nextInt(comments.size())));
                    r.setResponsibility(comments.get(random.nextInt(comments.size())));
                    r.setSimplicity(comments.get(random.nextInt(comments.size())));
                    r.setJoy(comments.get(random.nextInt(comments.size())));

                    reviewRepository.save(r);

                }

                System.out.println("Seeded 25 sample reviews into the database.");
            }
        };
    }
}