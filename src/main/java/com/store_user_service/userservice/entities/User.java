package com.store_user_service.userservice.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "user")
public class User implements Serializable{
    private static final long serialVersionUID = 1L;
    
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long userId;
    @NotNull
    @Column(unique = true)
    private String username;
    
    @NotNull
    @Column(unique = true)
    private String email;
    @NotNull
    private String password;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @NotNull
    private Date birthday;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_rol", joinColumns = @JoinColumn(name = "user_id"),
    inverseJoinColumns = @JoinColumn(name = "rol_id"))
    private Set<Rol> rols = new HashSet<>();

}
