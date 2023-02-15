package com.member.service;

import com.member.entity.Dependent;
import com.member.entity.Member;
import com.member.exception.DependentNotFoundExceptionHandler;
import com.member.exception.MemberNotFoundExceptionHandler;
import com.member.repository.IDependentRepo;
import com.member.repository.IMemberRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Service
public class MemberServiceImpl implements IMemberService {

    @Autowired
    IMemberRepo memberRepo;

    @Autowired
    IDependentRepo dependentRepo;

    @Override
    public String addMember(Member member) {
        if (memberRepo.findByName(member.getName()).isPresent()) {
            throw new MemberNotFoundExceptionHandler("Member already exists with name: " + member.getName());
        }
        Period duration = Period.between(member.getDob(), LocalDate.now());
        member.setAge(duration.getYears());
        Member savedMember = memberRepo.save(member);
        return savedMember.getMemberId();
    }

    @Override
    public Member updateMember(Member member) {
        Member exisitngMember = memberRepo.findById(member.getMemberId()).orElseThrow(() -> new MemberNotFoundExceptionHandler("Member not found with id: " +member.getMemberId()));
        exisitngMember.setAddress(member.getAddress());
        exisitngMember.setState(member.getState());
        exisitngMember.setEmail(member.getEmail());
        exisitngMember.setPan(member.getPan());
        exisitngMember.setContact(member.getContact());
        exisitngMember.setDob(member.getDob());
        return memberRepo.save(exisitngMember);
    }

    @Override
    public Member findByName(String name) {
        return memberRepo.findByName(name).get();
    }

    @Override
    public List<Dependent> getAllDependents(String memberName) {
        return dependentRepo.findByMemberName(memberName);
    }

    @Override
    public Long addDependent(Dependent dependent) {
        if (dependentRepo.findByName(dependent.getName()).isPresent()) {
            throw new DependentNotFoundExceptionHandler("Dependent already exists with name: " + dependent.getName());
        }
        Dependent savedDependant = dependentRepo.save(dependent);
        return savedDependant.getDependentId();
    }

    @Override
    public Dependent updateDependent(Dependent dependent) {
        Dependent existingDependent = dependentRepo.findById(dependent.getDependentId()).orElseThrow(() -> new DependentNotFoundExceptionHandler("Member not found with id: " +dependent.getDependentId()));
        existingDependent.setName(dependent.getName());
        existingDependent.setDob(dependent.getDob());
        return dependentRepo.save(existingDependent);
    }

    @Override
    public void deleteDependent(Long dependentId) {
        dependentRepo.deleteById(dependentId);
    }
}
