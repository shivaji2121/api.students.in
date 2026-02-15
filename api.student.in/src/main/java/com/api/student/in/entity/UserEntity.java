package com.api.student.in.entity;

import com.api.student.in.utils.UserStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "email",unique = true,nullable = false)
    private  String email;

    @Column(name = "password",nullable = false)
    private  String password;

    @Column(name = "phone")
    private String phone;

    @Column(name = "date_of_birth")
    private Date dateOfBirth;

    @Column(name = "user_status",nullable = false)
    @Enumerated(EnumType.STRING)
    private UserStatus userStatus;


    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;


    @PrePersist
    protected  void onCreate(){
        this.createdAt= LocalDateTime.now();

        if(this.userStatus==null){
            this.userStatus=UserStatus.ACTIVE;
        }

        this.deletedAt=null;

    }

    @PreUpdate
    protected  void onUpdate(){
        this.updatedAt=LocalDateTime.now();
    }


}
