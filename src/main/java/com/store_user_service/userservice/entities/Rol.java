package com.store_user_service.userservice.entities;

import java.io.Serializable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.store_user_service.userservice.enums.RolName;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="rol")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Rol implements Serializable{
    private static final long serialVersionUID = 1L;
    
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long rolId;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "rol_name")
    private RolName rolName;
}
