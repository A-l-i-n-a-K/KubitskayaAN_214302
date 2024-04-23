package com.fitquest.repo;

import com.fitquest.model.AppUser;
import com.fitquest.model.enums.Gender;
import com.fitquest.model.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepo extends JpaRepository<AppUser, Long> {
    AppUser findByUsername(String username);

    AppUser findByFio(String fio);

    List<AppUser> findAllByRoleAndCategory_Id(Role role, Long categoryId);

    List<AppUser> findAllByRole(Role role);

    List<AppUser> findAllByGenderAndRole(Gender gender, Role role);
}
