package com.verizon.tsp.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.verizon.tsp.models.TelecomCircle;

@Repository
public interface TelecomCircleDao extends JpaRepository<TelecomCircle, Long>{

	 TelecomCircle findByState(String state);
	 TelecomCircle findByCity(String city);
}
