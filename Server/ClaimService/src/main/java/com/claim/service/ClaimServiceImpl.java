package com.claim.service;

import com.claim.entity.Claim;
import com.claim.repository.IClaimRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClaimServiceImpl implements IClaimService{
    @Autowired
    IClaimRepo claimRepo;

    @Override
    public List<Claim> getAllClaims(String memberName) {
        return claimRepo.findByMemberName(memberName);
    }

    @Override
    public String addClaim(Claim claim) {
        Claim savedMember = claimRepo.save(claim);
        return savedMember.getClaimId();
    }

}
