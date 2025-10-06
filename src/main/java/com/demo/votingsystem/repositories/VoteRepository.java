package com.demo.votingsystem.repositories;


import com.demo.votingsystem.models.User;
import com.demo.votingsystem.models.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoteRepository extends JpaRepository<Vote, Long> {
    boolean existsByUser(User user);
}
