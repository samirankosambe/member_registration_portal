package com.member;

import com.member.controller.MemberController;
import com.member.entity.Dependent;
import com.member.entity.Member;
import com.member.models.Claim;
import com.member.models.JWTRequest;
import com.member.models.JWTResponse;
import com.member.service.IMemberService;
import com.member.service.JWTUserService;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class MemberControllerTest {
    @InjectMocks
    MemberController memberController;

    @Mock
    RestTemplate restTemplate;

    @Mock
    IMemberService memberService;

    @Mock
    JWTUserService jwtUserService;

    Member member;

    Dependent dependent;

    Claim claim;

    public static final String BASE_URL = "http://Localhost:8001";

    @Before("")
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void signInSuccessfully() throws Exception {
        member = getMember();
        JWTRequest jwtRequest = new JWTRequest("Sam", "sam123");
        JWTResponse jwtResponse = new JWTResponse("eyJhbGciOiJIUzUxMiJ9", member);
        Mockito.when((jwtUserService.createJwtToken(jwtRequest))).thenReturn(jwtResponse);
        JWTResponse response = memberController.authenticate(jwtRequest);
        assertEquals("eyJhbGciOiJIUzUxMiJ9", response.getJwtToken());
    }

    @Test
    public void signUpSuccessfully() {
        member = getMember();
        Mockito.when(memberService.addMember(member)).thenReturn(member.getMemberId());
        assertEquals("R-001", memberController.addMember(member));
    }

    @Test
    public void updateMemberSuccessfully() {
        member = getMember();
        member.setContact("8855050428");
        Mockito.when(memberService.updateMember(member)).thenReturn(member);
        Member updatedMember = memberController.updateMember(member);
        assertEquals("8855050428", updatedMember.getContact());
    }

    @Test
    public void getAllDependentsSuccessfully() {
        dependent = getDependent();
        List<Dependent> dependents = new ArrayList<>();
        dependents.add(dependent);
        Mockito.when(memberService.getAllDependents("Sam")).thenReturn(dependents);
        List<Dependent> existingDependents = memberController.getDependents("Sam");
        assertEquals(1, existingDependents.size());
    }

    @Test
    public void dependentIsSavedSuccessfully() {
        dependent = getDependent();
        Mockito.when(memberService.addDependent(dependent)).thenReturn(dependent.getDependentId());
        long dependentId = memberController.addDependent(dependent);
        assertEquals(1, dependentId);
    }

    @Test
    public void dependentIsUpdatedSuccessfully() {
        dependent = getDependent();
        dependent.setName("Jai");
        Mockito.when(memberService.updateDependent(dependent)).thenReturn(dependent);
        Dependent updatedDependent = memberController.updateDependent(dependent);
        assertEquals("Jai", dependent.getName());
    }

    @Test
    public void memberIsDeletedSuccessfully() {
        memberController.deleteDependent(1l);
        Mockito.verify(memberService).deleteDependent(1l);
    }

    @Test
    public void getAllClaimsSuccessfully() {
        claim = getClaim();
        List<Claim> claims = new ArrayList<>();
        claims.add(claim);
        String memberName = "Sam";
        Mockito.when(restTemplate.getForObject(BASE_URL + "/claims/" + memberName, List.class)).thenReturn(claims);
        List<Claim> existingClaims = memberController.getClaims("Sam");
        assertEquals(1, existingClaims.size());
    }

    @Test
    public void claimIsSavedSuccessfully() {
        claim = getClaim();
        Mockito.when(restTemplate.postForObject(BASE_URL + "/add-claim", claim, String.class)).thenReturn(claim.getClaimId());
        String claimId = memberController.addClaim(claim);
        assertEquals("0000000001", claimId);
    }

    public Member getMember() {
        return new Member("R-001", "Sam", "Pune", "Maharashtra", "India", "sam@gmail.com", "GXDPK2019B", "8149593575", LocalDate.of(1997, 11, 06), 19, "sam123");
    }

    public Dependent getDependent() {
        return new Dependent(1l, "Sam", "Jay", LocalDate.of(1994, 10, 30));
    }

    public Claim getClaim() {
        return new Claim("0000000001", "sam", "Sam", LocalDate.of(1997, 11, 06), LocalDate.of(2023, 02, 01), LocalDate.of(2023, 02, 9), "HDFCLife", 5000);
    }
}
