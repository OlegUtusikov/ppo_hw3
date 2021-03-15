package com.utusikov.hw3;

import com.utusikov.hw3.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long> { }
