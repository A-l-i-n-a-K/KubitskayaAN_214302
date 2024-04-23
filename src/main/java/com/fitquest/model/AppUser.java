package com.fitquest.model;

import com.fitquest.controller.main.Main;
import com.fitquest.model.enums.Gender;
import com.fitquest.model.enums.Role;
import com.fitquest.model.enums.SignStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class AppUser implements UserDetails, Serializable {
    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private Long id;

    private String username;
    private String password;
    private boolean enabled = false;

    @Enumerated(EnumType.STRING)
    private Role role = Role.USER;

    private String file = "";

    private String fio;
    private String photo = "def.png";
    private int age = 18;
    private float weight = 0;
    private int height = 0;
    private int experience = 0;
    private int win = 0;
    private int lose = 0;
    private String tel = "";
    private String email = "";

    @Enumerated(EnumType.STRING)
    private Gender gender = Gender.M;

    @ManyToOne
    private Category category;
    @ManyToOne
    private AppUser trainer;
    @OneToMany(mappedBy = "trainer")
    private List<AppUser> athletes = new ArrayList<>();
    @OneToMany(mappedBy = "owner")
    private List<Training> trainings = new ArrayList<>();
    @OneToMany(mappedBy = "owner")
    private List<Sign> signs = new ArrayList<>();

    public AppUser(String username, String password, String fio) {
        this.username = username;
        this.password = passwordEncoder().encode(password);
        this.fio = fio;
    }

    public float getImt() {
        if (weight == 0 || height == 0) return 0;
        return Main.round(weight / ((float) height / 100 * 2));
    }

    public Sign checkSign(Long trainingId) {
        for (Sign i : signs) {
            if (Objects.equals(i.getTraining().getId(), trainingId)) {
                return i;
            }
        }
        return null;
    }

    public int getQuantity() {
        return signs.stream().reduce(0, (i, sign) -> {
            if (sign.getStatus() == SignStatus.PASSED) return i + 1;
            return i;
        }, Integer::sum);
    }

    public float getRatio() {
        if (category == null) return 0;
        return Main.round((float) getQuantity() / category.getQuantity() * 100);
    }

    public float getRatioWin() {
        if (win == 0) return 0;
        return Main.round((float) win / (win + lose) * 100);
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(getRole());
    }

    private PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }
}