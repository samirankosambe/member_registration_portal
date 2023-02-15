package com.member.repository;

import com.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IMemberRepo extends JpaRepository<Member, String> {
    Optional<Member> findByName(String name);
}
