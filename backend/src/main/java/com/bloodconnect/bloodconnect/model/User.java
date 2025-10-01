package com.bloodconnect.bloodconnect.model;
import jakarta.persistence.*;

@Entity
@Table(name="users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    private String name;
    private String phone;
    @Column(nullable = false)
    private String bloodGroup;

    public User(){}

    public User(String email,String password,String name,String phone,String bloodGroup){
        this.email =email;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.bloodGroup= bloodGroup;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getBloodGroup(){
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup){
        this.bloodGroup=bloodGroup;
    }




}
