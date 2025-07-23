package com.progressoft.warehouse.repositories;

import com.progressoft.warehouse.entities.Deal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DealRepository extends JpaRepository<Deal,String> {
}
