export class Claim{
    claimId: string = "";
    name: string = "";
    memberName: string = "";
    dob: Date = new Date();
    admissionDate: Date = new Date();
    dischargeDate: Date = new Date();
    provider: string = "";
    totalBill: number = 0;
}