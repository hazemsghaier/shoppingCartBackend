package com.shop.e_comerce.model;

import jakarta.persistence.*;
import com.shop.e_comerce.model.User;
import java.util.Collection;
import java.util.HashSet;

@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    public Role(String name) {
        this.name = name;
    }
    public Role() {

    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    @ManyToMany(mappedBy = "roles")
    private Collection<User> users=new HashSet<>();
}
