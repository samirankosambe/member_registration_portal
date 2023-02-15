package com.claim;

import com.claim.entity.Claim;
import com.claim.repository.IClaimRepo;
import com.claim.service.ClaimServiceImpl;
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
public class ClaimServiceTest {
    @InjectMocks
    ClaimServiceImpl claimService;

    @Mock
    IClaimRepo claimRepo;

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
        Mockito.when(claimRepo.findByMemberName("Sam")).thenReturn(claims);
        List<Claim> existingClaims = claimService.getAllClaims("Sam");
        assertEquals(1, existingClaims.size());
    }

    @Test
    public void dependentIsSavedSuccessfully() {
        claim = getClaim();
        Mockito.when(claimRepo.save(claim)).thenReturn(claim);
        String claimId = claimService.addClaim(claim);
        assertEquals("0000000001", claimId);
    }

    public Claim getClaim() {
        return new Claim("0000000001", "sam", "Sam", LocalDate.of(1997, 11, 06), LocalDate.of(2023, 02, 01), LocalDate.of(2023, 02, 9), "HDFCLife", 5000);
    }
}
