//package com.assignment.springboot.Model;
//
//
//import javax.persistence.*;
//import java.util.Collection;
//
//@Entity
//@Table(name = "User" , uniqueConstraints = @UniqueConstraint(columnNames = "email"))
//public class User {
//
//    @Id
//    @GeneratedValue( strategy = GenerationType.AUTO)
//    private Long id;
//    private String fistName;
//    private String lastName;
//    private String email;
//    private String password;
//    private String address;
//    private String phone;
//
//    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
//    @JoinTable(name = "users_roles",
//        joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
//            inverseJoinColumns = @JoinColumn(name = "role_id",referencedColumnName = "id"))
//    private Collection<Role> roles;
//
//    public User() {
//    }
//    public User(String fistName,String lastName,String email,String password,String address,String phone) {
//        this.fistName = fistName;
//        this.lastName = lastName;
//        this.email = email;
//        this.password = password;
//        this.address = address;
//        this.phone = phone;
//    }
//    public User(String fistName,String lastName,String email,String password,String address,String phone,Collection<Role> roles) {
//        this.fistName = fistName;
//        this.lastName = lastName;
//        this.email = email;
//        this.password = password;
//        this.address = address;
//        this.phone = phone;
//        this.roles = roles;
//    }
//
//    @Override
//    public String toString() {
//        return "User{" +
//                "id=" + id +
//                ", fistName='" + fistName + '\'' +
//                ", lastName='" + lastName + '\'' +
//                ", email='" + email + '\'' +
//                ", password='" + password + '\'' +
//                ", address='" + address + '\'' +
//                ", phone='" + phone + '\'' +
//                ", roles=" + roles +
//                '}';
//    }
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getFistName() {
//        return fistName;
//    }
//
//    public void setFistName(String fistName) {
//        this.fistName = fistName;
//    }
//
//    public String getLastName() {
//        return lastName;
//    }
//
//    public void setLastName(String lastName) {
//        this.lastName = lastName;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//    public String getAddress() {
//        return address;
//    }
//
//    public void setAddress(String address) {
//        this.address = address;
//    }
//
//    public String getPhone() {
//        return phone;
//    }
//
//    public void setPhone(String phone) {
//        this.phone = phone;
//    }
//
//    public Collection<Role> getRoles() {
//        return roles;
//    }
//
//    public void setRoles(Collection<Role> roles) {
//        this.roles = roles;
//    }
//}
