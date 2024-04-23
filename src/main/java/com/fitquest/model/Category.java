package com.fitquest.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Category implements Serializable {
    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private Long id;

    private String name;
    private int quantity;

    @OneToMany(mappedBy = "category")
    private List<AppUser> users = new ArrayList<>();

    public Category(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    public void set(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
    }
}