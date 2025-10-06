package com.demo.votingsystem.controller;


import com.demo.votingsystem.models.Candidate;
import com.demo.votingsystem.models.User;
import com.demo.votingsystem.models.Vote;
import com.demo.votingsystem.repositories.CandidateRepository;
import com.demo.votingsystem.repositories.UserRepository;
import com.demo.votingsystem.repositories.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private VoteRepository voteRepository;

    @PostMapping("/register")
    public String registerUser(@RequestBody User user) {
        if (userRepository.findByEmail(user.getEmail()) != null) {
            return "Error: Email already exists!";
        }

        // Optional: Validate role input
        String role = user.getRole();
        if (!role.equalsIgnoreCase("ADMIN") && !role.equalsIgnoreCase("USER")) {
            return "Error: Role must be either 'ADMIN' or 'USER'";
        }

        user.setRole(role.toUpperCase()); // Normalize role value

        userRepository.save(user);
        return "User registered successfully as " + user.getRole();
    }
    @PostMapping("/login")
    public String loginUser(@RequestBody User loginData) {
        User user = userRepository.findByEmail(loginData.getEmail());
        if (user == null) {
            return "Error: User not found!";
        }
        if (!user.getPassword().equals(loginData.getPassword())) {
            return "Error: Invalid password!";
        }
        return "Login successful! Welcome, " + user.getUsername() + " (" + user.getRole() + ")";
    }

    @GetMapping("/candidates")
    public List<Candidate> getAllCandidates() {
        return candidateRepository.findAll();
    }

    @PostMapping("/vote")
    public String vote(@RequestParam Long userId, @RequestParam Long candidateId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null || !user.getRole().equalsIgnoreCase("USER")) {
            return "Invalid user or not a voter.";
        }

        if (voteRepository.existsByUser(user)) {
            return "User has already voted!";
        }

        Candidate candidate = candidateRepository.findById(candidateId).orElse(null);
        if (candidate == null) {
            return "Candidate not found.";
        }

        // Save vote
        Vote vote = new Vote();
        vote.setUser(user);
        vote.setCandidate(candidate);
        voteRepository.save(vote);

        // Increment vote count
        candidate.setVoteCount(candidate.getVoteCount() + 1);
        candidateRepository.save(candidate);

        return "Vote cast successfully!";
    }

    @GetMapping("/admin/results")
    public List<Candidate> getResults() {
        return candidateRepository.findAll();
    }

    @RestController
    @RequestMapping("/api/admin")
    public class AdminController {

        private final CandidateRepository candidateRepository;

        public AdminController(CandidateRepository candidateRepository) {
            this.candidateRepository = candidateRepository;
        }

        @PostMapping("/candidates")
        public ResponseEntity<?> addCandidate(@RequestBody Candidate candidate, @RequestParam Long adminUserId) {
            User user = userRepository.findById(adminUserId).orElse(null);
            if (user == null || !user.getRole().equalsIgnoreCase("ADMIN")) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied");
            }
            candidate.setVoteCount(0);
            Candidate savedCandidate = candidateRepository.save(candidate);
            return ResponseEntity.ok(savedCandidate);
        }

    }


}
