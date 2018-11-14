package com.verizon.tsp.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.verizon.tsp.models.Admin;


@Repository
public interface UserRepository extends JpaRepository<Admin, Long> {

public Admin findOneByUsername(String username);
}
