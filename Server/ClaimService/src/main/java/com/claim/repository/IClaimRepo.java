package com.claim.repository;

import com.claim.entity.Claim;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IClaimRepo extends JpaRepository<Claim, String> {
    List<Claim> findByMemberName(String memberName);
}