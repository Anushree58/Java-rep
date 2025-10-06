package com.demo.votingsystem.repositories;


import com.demo.votingsystem.models.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CandidateRepository extends JpaRepository<Candidate, Long> {}

