package com.carrotfriend.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Builder @Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String userID;
    @Column
    private String pw;
    @Column
    private String nickName;
    @Column
    private LocalDateTime birthday;
    @Column
    private String email;
    @Enumerated(EnumType.STRING)
    private Gender gender;
}
