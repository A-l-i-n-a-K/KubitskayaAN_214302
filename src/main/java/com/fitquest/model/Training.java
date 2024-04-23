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
public class Training implements Serializable {
    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private Long id;

    private String dateAndTime;

    @ManyToOne
    private AppUser owner;
    @OneToMany(mappedBy = "training", cascade = CascadeType.ALL)
    private List<Sign> signs = new ArrayList<>();

    public Training(String dateAndTime, AppUser owner) {
        this.dateAndTime = dateAndTime;
        this.owner = owner;
    }

    public String getDateAndTime() {
        return dateAndTime.substring(0, 10) + " " + dateAndTime.substring(11, 16);
    }
}