package com.backendVn.SWP.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Nationalized;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "Users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UserID", nullable = false)
    private Integer id;

    @Size(max = 50)
    @NotNull
    @Nationalized
    @Column(name = "user_name", nullable = false, length = 50)
    private String userName;

    @Nationalized
    @Lob
    @Column(name = "Password")
    private String password;

    @Size(max = 100)
    @Nationalized
    @Column(name = "Email", length = 100)
    private String email;

    @Nationalized
    @Lob
    @Column(name = "Address")
    private String address;

    @Size(max = 50)
    @Nationalized
    @Column(name = "Title", length = 50)
    private String title;

    @Size(max = 50)
    @Nationalized
    @Column(name = "cus_name", length = 50)
    private String cusName;

    @Size(max = 20)
    @Nationalized
    @Column(name = "phone_num", length = 20)
    private String phoneNum;

}