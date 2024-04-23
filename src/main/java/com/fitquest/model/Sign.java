package com.fitquest.model;

import com.fitquest.model.enums.SignStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Sign implements Serializable {
    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private Long id;

    private float weight = 0;
    private int pulse = 0;

    @Enumerated(EnumType.STRING)
    private SignStatus status = SignStatus.WAITING;

    @ManyToOne
    private AppUser owner;
    @ManyToOne
    private Training training;

    public Sign(AppUser owner, Training training) {
        this.owner = owner;
        this.training = training;
    }

    public String getDateAndTime() {
        return training.getDateAndTime();
    }
}