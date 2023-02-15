package com.claim.entity;

import com.claim.generator.ClaimSequenceIdGenerator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Claim {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "claim_seq")
    @GenericGenerator(
            name = "claim_seq",
            strategy = "com.claim.generator.ClaimSequenceIdGenerator",
            parameters = {
                    @Parameter(name = ClaimSequenceIdGenerator.INCREMENT_PARAM, value = "50"),
                    @Parameter(name = ClaimSequenceIdGenerator.NUMBER_FORMAT_PARAMETER, value = "%010d")})
    private String claimId;
    private String name;
    private String memberName;
    private LocalDate dob;
    private LocalDate admissionDate;
    private LocalDate dischargeDate;
    private String provider;
    private double totalBill;
}
