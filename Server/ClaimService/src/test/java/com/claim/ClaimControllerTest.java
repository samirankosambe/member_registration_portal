package com.claim;

import com.claim.controller.ClaimController;
import com.claim.entity.Claim;
import com.claim.service.IClaimService;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class ClaimControllerTest {
    @InjectMocks
    ClaimController claimController;

    @Mock
    IClaimService claimService;

    Claim claim;

    @Before("")
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getAllClaimsSuccessfully() {
        claim = getClaim();
        List<Claim> claims = new ArrayList<>();
        claims.add(claim);
        Mockito.when(claimService.getAllClaims("Sam")).thenReturn(claims);
        List<Claim> existingClaims = claimController.getClaims("Sam");
        assertEquals(1, existingClaims.size());
    }

    @Test
    public void dependentIsSavedSuccessfully() {
        claim = getClaim();
        Mockito.when(claimService.addClaim(claim)).thenReturn(claim.getClaimId());
        String claimId = claimController.addClaim(claim);
        assertEquals("0000000001", claimId);
    }

    public  Claim getClaim(){
        return  new Claim("0000000001", "sam", "Sam", LocalDate.of(1997,11,06), LocalDate.of(2023,02,01), LocalDate.of(2023,02,9), "HDFCLife", 5000);
    }

}
