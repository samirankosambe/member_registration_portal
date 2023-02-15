package com.member.repository;

import com.member.entity.Dependent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IDependentRepo extends JpaRepository<Dependent, Long> {
    Optional<Dependent> findByName(String name);

    List<Dependent> findByMemberName(String memberName);
}
