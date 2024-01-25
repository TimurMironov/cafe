package com.tmir.entity;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@jakarta.persistence.Table(name = "table_types")
public class TableType implements Comparable<TableType>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "status")
    private String status;

    @OneToMany(mappedBy = "type")
    private List<Table> tables;

    @Override
    public int compareTo(TableType tableType) {
        return this.getId().compareTo(tableType.getId());
    }
}