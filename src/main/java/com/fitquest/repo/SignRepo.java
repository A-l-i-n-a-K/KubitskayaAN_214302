package com.fitquest.repo;

import com.fitquest.model.Sign;
import com.fitquest.model.enums.SignStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SignRepo extends JpaRepository<Sign, Long> {
    List<Sign> findAllByOwner_IdAndStatus(Long ownerId, SignStatus status);
}
