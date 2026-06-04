package com.laptrinhjavaweb.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "customer")
public class CustomerEntity extends  BaseEntity{

    @Column(name = "fullname")
    private  String fullName;

    @Column(name = "phone")
    private  String phone;

    @Column(name = "email")
    private  String email;

    @Column(name = "status")
    private String status;

    @Column(name = "demand")
    private String demand;

    @Column(name = "note")
    private String note;

    @OneToMany(mappedBy = "customerEntity",fetch = FetchType.LAZY,cascade = CascadeType.REMOVE)
    private List<AssignmentCustomerEntity> assignmentCustomerEntities = new ArrayList<>();

    @OneToMany(mappedBy = "customerEntity",fetch = FetchType.LAZY,cascade = CascadeType.REMOVE)
    private List<TransactionEntity> transactionEntities = new ArrayList<>();

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<AssignmentCustomerEntity> getAssignmentCustomerEntities() {
        return assignmentCustomerEntities;
    }

    public void setAssignmentCustomerEntities(List<AssignmentCustomerEntity> assignmentCustomerEntities) {
        this.assignmentCustomerEntities = assignmentCustomerEntities;
    }

    public List<TransactionEntity> getTransactionEntities() {
        return transactionEntities;
    }

    public void setTransactionEntities(List<TransactionEntity> transactionEntities) {
        this.transactionEntities = transactionEntities;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDemand() {
        return demand;
    }

    public void setDemand(String demand) {
        this.demand = demand;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
