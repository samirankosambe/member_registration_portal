package com.member.service;

import com.member.entity.Dependent;
import com.member.entity.Member;

import java.util.List;

public interface IMemberService {
    String addMember(Member member);

    Member updateMember(Member member);

    Member findByName(String name);

    List<Dependent> getAllDependents(String memberName);

    Long addDependent(Dependent dependent);

    Dependent updateDependent(Dependent dependent);

    void deleteDependent(Long dependentId);
}
