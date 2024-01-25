package com.tmir.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@jakarta.persistence.Table(name = "_tables")
public class Table {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "no")
    private Integer number;

    @ManyToOne
    @JoinColumn(name = "_type", referencedColumnName = "id")
    private TableType type;

    @OneToMany(mappedBy = "table")
    private List<Booking> bookingList;

}
