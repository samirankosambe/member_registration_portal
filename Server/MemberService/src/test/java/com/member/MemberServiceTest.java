package com.member;

import com.member.entity.Dependent;
import com.member.entity.Member;
import com.member.exception.DependentNotFoundExceptionHandler;
import com.member.exception.MemberNotFoundExceptionHandler;
import com.member.repository.IDependentRepo;
import com.member.repository.IMemberRepo;
import com.member.service.MemberServiceImpl;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class MemberServiceTest {

    @InjectMocks
    MemberServiceImpl memberService;

    @Mock
    IMemberRepo memberRepo;

    @Mock
    IDependentRepo dependentRepo;

    Member member;

    Dependent dependent;

    @Before("")
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void memberIsSavedSuccessfully() {
        member = getMember();
        Mockito.when(memberRepo.findByName("Sam")).thenReturn(Optional.empty());
        Mockito.when(memberRepo.save(member)).thenReturn(member);
        String memberId = memberService.addMember(member);
        assertEquals("R-001", memberId);
    }

    @Test
    public void forExistingMemberWhenSavingExceptionIsThrown() {
        member = getMember();
        Mockito.when(memberRepo.findByName("Sam")).thenReturn(Optional.of(member));
        assertThrows(MemberNotFoundExceptionHandler.class, () -> memberService.addMember(member));
    }

    @Test
    public void memberIsUpdatedSuccessfully() {
        member = getMember();
        Mockito.when(memberRepo.findById("R-001")).thenReturn(Optional.of(member));
        member.setContact("8855050428");
        Mockito.when(memberRepo.save(member)).thenReturn(member);
        Member updatedMember = memberService.updateMember(member);
        assertEquals("8855050428", updatedMember.getContact());
    }

    @Test
    public void forNonExistingMemberWhenUpdatingExceptionIsThrown() {
        member = getMember();
        Mockito.when(memberRepo.findById("R-001")).thenReturn(Optional.empty());
        assertThrows(MemberNotFoundExceptionHandler.class, () -> memberService.updateMember(member));
    }

    @Test
    public void getMemberByNameSuccessfully() {
        member = getMember();
        Mockito.when(memberRepo.findByName("Sam")).thenReturn(Optional.of(member));
        Member existingMember = memberService.findByName("Sam");
        assertEquals("Sam", existingMember.getName());
    }

    @Test
    public void getAllDependentsSuccessfully() {
        dependent = getDependent();
        List<Dependent> dependents = new ArrayList<>();
        dependents.add(dependent);
        Mockito.when(dependentRepo.findByMemberName("Sam")).thenReturn(dependents);
        List<Dependent> existingDependents = memberService.getAllDependents("Sam");
        assertEquals(1, existingDependents.size());
    }

    @Test
    public void dependentIsSavedSuccessfully() {
        dependent = getDependent();
        Mockito.when(dependentRepo.findByName("Jay")).thenReturn(Optional.empty());
        Mockito.when(dependentRepo.save(dependent)).thenReturn(dependent);
        Long dependentId = memberService.addDependent(dependent);
        assertEquals(1, dependentId);
    }

    @Test
    public void whileSavingExceptionIsThrownForExistingDependent() {
        dependent = getDependent();
        Mockito.when(dependentRepo.findByName("Jay")).thenReturn(Optional.of(dependent));
        assertThrows(DependentNotFoundExceptionHandler.class, () -> memberService.addDependent(dependent));
    }

    @Test
    public void dependentIsUpdatedSuccessfully() {
        dependent = getDependent();
        Mockito.when(dependentRepo.findById(1l)).thenReturn(Optional.of(dependent));
        dependent.setName("Jai");
        Mockito.when(dependentRepo.save(dependent)).thenReturn(dependent);
        Dependent updatedDependent = memberService.updateDependent(dependent);
        assertEquals("Jai", updatedDependent.getName());
    }

    @Test
    public void forNonExistingDependentWhenUpdatingExceptionIsThrown() {
        dependent = getDependent();
        Mockito.when(dependentRepo.findById(1l)).thenReturn(Optional.empty());
        assertThrows(DependentNotFoundExceptionHandler.class, () -> memberService.updateDependent(dependent));
    }

    @Test
    public void memberIsDeletedSuccessfully() {
        memberService.deleteDependent(1l);
        Mockito.verify(dependentRepo).deleteById(1l);
    }

    public Member getMember() {
        return new Member("R-001", "Sam", "Pune", "Maharashtra", "India", "sam@gmail.com", "GXDPK2019B", "8149593575", LocalDate.of(1997, 11, 6), 19, "sam123");
    }

    public Dependent getDependent() {
        return new Dependent(1l, "R-001", "Jay", LocalDate.of(1994, 10, 30));
    }

}
