package com.Agri.AgriBack.Query.entity;

import com.Agri.AgriBack.Command.entity.Employee;

import jakarta.persistence.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Employee")
public class EmployeeQ {
    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String address;
    private String mobile;
    private Employee.Role role;

    public enum Role {
        admin,
        farmer
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Employee.Role getRole() {
        return role;
    }

    public void setRole(Employee.Role role) {
        this.role = role;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    public EmployeeQ(){}
}
