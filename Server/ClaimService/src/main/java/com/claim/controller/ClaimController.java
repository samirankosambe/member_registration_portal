package com.claim.controller;

import com.claim.entity.Claim;
import com.claim.service.IClaimService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ClaimController {

    @Autowired
    IClaimService claimService;

    // Get all claims
    @GetMapping("/claims/{memberName}")
    public List<Claim> getClaims(@PathVariable String memberName) {
        return claimService.getAllClaims(memberName);
    }

    // Add a claim
    @PostMapping("/add-claim")
    public String addClaim(@RequestBody Claim claim) {
        return claimService.addClaim(claim);
    }
}
