package com.tmir.repository;

import com.tmir.entity.Table;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TableRepository extends JpaRepository<Table, Integer> {

    Table findByNumber(Integer number);
}
