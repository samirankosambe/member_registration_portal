package com.member.controller;

import com.member.entity.Dependent;
import com.member.entity.Member;
import com.member.models.Claim;
import com.member.models.JWTRequest;
import com.member.models.JWTResponse;
import com.member.service.IMemberService;
import com.member.service.JWTUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@CrossOrigin
public class MemberController {

    @Autowired
    IMemberService memberService;
    @Autowired
    JWTUserService jwtUserService;
    @Autowired
    private RestTemplate restTemplate;

    public static final String BASE_URL = "http://Localhost:8001";

    // For Signing in
    @PostMapping("/sign-in")
    public JWTResponse authenticate(@RequestBody JWTRequest jwtRequest) throws Exception {
        return jwtUserService.createJwtToken(jwtRequest);
    }

    //For Sign-Up
    @PostMapping("/sign-up")
    public String addMember(@RequestBody Member member) {
        return memberService.addMember(member);
    }
    //Update a member
    @PutMapping("/update-member")
    public Member updateMember(@RequestBody Member member) {
        return memberService.updateMember(member);
    }

    @GetMapping ("/find/{name}")
    public Member findUserByName(@PathVariable String name){
        return memberService.findByName(name);
    }

    //Get all dependent
    @GetMapping("/dependents/{memberName}")
    public List<Dependent> getDependents(@PathVariable String memberName){
        return memberService.getAllDependents(memberName);
    }

    //Add a dependent
    @PostMapping("/add-dependent")
    public long addDependent(@RequestBody Dependent dependent) {
        return memberService.addDependent(dependent);
    }

    //Update a dependent
    @PutMapping("/update-dependent")
    public Dependent updateDependent(@RequestBody Dependent dependent) {
        return memberService.updateDependent(dependent);
    }

    //Delete a dependent
    @DeleteMapping("/delete-dependent/{dependentId}")
    public void deleteDependent(@PathVariable Long dependentId) {
        memberService.deleteDependent(dependentId);
    }

    // Get all claims
    @GetMapping("/claims/{memberName}")
    public List<Claim> getClaims(@PathVariable String memberName){
        return restTemplate.getForObject(BASE_URL + "/claims/" + memberName, List.class);
    }

    // Add a claim
    @PostMapping("/add-claim")
    public String addClaim(@RequestBody Claim claim){
        String claimId = restTemplate.postForObject(BASE_URL + "/add-claim", claim, String.class);
        return claimId;
    }
}
