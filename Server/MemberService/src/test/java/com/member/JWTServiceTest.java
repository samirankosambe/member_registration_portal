package com.member;

import com.member.entity.Dependent;
import com.member.entity.Member;
import com.member.models.JWTRequest;
import com.member.models.JWTResponse;
import com.member.repository.IDependentRepo;
import com.member.repository.IMemberRepo;
import com.member.service.JWTUserService;
import com.member.service.MemberServiceImpl;
import com.member.utility.JWTUtility;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class JWTServiceTest {

    @InjectMocks
    JWTUserService jwtUserService;

    @Mock
    IMemberRepo memberRepo;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    JWTUtility jwtUtility;

    Member member;

    @Before("")
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void loadUserByUsernameSuccessfully(){
        member = getMember();
        Mockito.when(memberRepo.findByName("Sam")).thenReturn(Optional.of(member));
        UserDetails user = jwtUserService.loadUserByUsername("Sam");
        assertEquals("Sam", user.getUsername());
    }

    @Test
    public void ForNonExistingUserExceptionIsThrown(){
        member = getMember();
        Mockito.when(memberRepo.findByName("Sam")).thenReturn(Optional.empty());
        assertThrows(UsernameNotFoundException.class, () -> jwtUserService.loadUserByUsername("Sam"));
    }

    @Test
    public void signInSuccessfully() throws Exception {
        member = getMember();
        UserDetails user = new User(member.getName(), member.getPassword(), new ArrayList<>());
        JWTRequest jwtRequest = new JWTRequest("Sam", "sam123");
        JWTResponse jwtResponse = new JWTResponse("eyJhbGciOiJIUzUxMiJ9", member);

        Mockito.when(memberRepo.findByName("Sam")).thenReturn(Optional.of(member));
        Mockito.when(jwtUtility.generateToken(user)).thenReturn("eyJhbGciOiJIUzUxMiJ9");
        JWTResponse response = jwtUserService.createJwtToken(jwtRequest);
        assertEquals("eyJhbGciOiJIUzUxMiJ9", response.getJwtToken());
    }

    @Test
    public void  authenticateSuccessfully() throws Exception {
        jwtUserService.authenticate("Sam", "sam123");
        Mockito.verify(authenticationManager).authenticate(new UsernamePasswordAuthenticationToken("Sam", "sam123"));
    }

    public Member getMember() {
        return new Member("R-001", "Sam", "Pune", "Maharashtra", "India", "sam@gmail.com", "GXDPK2019B", "8149593575", LocalDate.of(1997, 11, 06), 19, "sam123");
    }
}
