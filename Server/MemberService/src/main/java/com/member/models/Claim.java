package com.member.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Claim {
    private String claimId;
    private String name;
    private String memberName;
    private LocalDate dob;
    private LocalDate admissionDate;
    private LocalDate dischargeDate;
    private String provider;
    private double totalBill;
}
