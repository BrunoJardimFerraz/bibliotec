package com.biblioteca.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.biblioteca.entities.Info;

@Repository
public interface InfoRepository extends JpaRepository<Info, Long>{
	
	Info findByInfoId(Long infoId);
}
