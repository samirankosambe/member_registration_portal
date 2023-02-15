package com.claim.service;

import com.claim.entity.Claim;

import java.util.List;

public interface IClaimService {
    public List<Claim> getAllClaims(String memberName);

    public String addClaim(Claim claim);
}
