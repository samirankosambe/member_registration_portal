package com.member.service;

import com.member.entity.Member;
import com.member.models.JWTRequest;
import com.member.models.JWTResponse;
import com.member.repository.IMemberRepo;
import com.member.utility.JWTUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class JWTUserService implements UserDetailsService {

    @Autowired
    private IMemberRepo memberRepo;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    JWTUtility jwtUtility;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        if (memberRepo.findByName(name).isPresent()) {
            Member member = memberRepo.findByName(name).get();
            return new org.springframework.security.core.userdetails.User(member.getName(), member.getPassword(), new ArrayList<>());
        } else {
            throw new UsernameNotFoundException("Member not found with name: " + name);
        }
    }

    public JWTResponse createJwtToken(JWTRequest jwtRequest) throws DisabledException, BadCredentialsException, UsernameNotFoundException {
        String userName = jwtRequest.getUsername();
        String userPassword = jwtRequest.getPassword();
        authenticate(userName, userPassword);
        Member member;

        UserDetails userDetails = loadUserByUsername(userName);
        String newGeneratedToken = jwtUtility.generateToken(userDetails);

        if (memberRepo.findByName(userName).isPresent()) {
            member = memberRepo.findByName(userName).get();
        } else {
            throw new UsernameNotFoundException("Member not found with name: " + userName);
        }
        return new JWTResponse(newGeneratedToken, member);
    }

    public void authenticate(String userName, String userPassword) throws DisabledException, BadCredentialsException {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName, userPassword));
        } catch (DisabledException e) {
            throw new DisabledException("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("INVALID_CREDENTIALS", e);
        }
    }


}
