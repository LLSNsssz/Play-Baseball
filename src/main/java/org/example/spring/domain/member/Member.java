package org.example.spring.domain.member;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.time.Instant;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.spring.constants.Gender;
import org.example.spring.domain.member.dto.MemberModifyRequestDto;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "member")
@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id", nullable = false)
    private Long id;

    @Column(name = "email", nullable = false, unique = true, length = 100)
    private String email;

    @Column(name = "password", nullable = false, length = 68)
    private String password;

    @Column(name = "nickname", nullable = false, unique = true, length = 100)
    private String nickname;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "phone_number", nullable = false, unique = true, length = 20)
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false)
    private Gender gender;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false,nullable = false)
    private Timestamp createdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @Column(name = "deleted_at")
    private Timestamp deletedAt;

    @Column(name = "last_login_date")
    private Timestamp lastLoginDate;

    @ColumnDefault("false")
    @Column(name = "email_verified", nullable = false, columnDefinition = "BOOLEAN DEFAULT false")
    private boolean emailVerified;

    @ColumnDefault("false")
    @Column(name = "otp_verified", nullable = false, columnDefinition = "BOOLEAN DEFAULT false")
    private boolean otpVerified;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false, columnDefinition = "ENUM('USER', 'ADMIN', 'BANNED') DEFAULT 'USER'")
    private MemberRole role;

    public void updateFrom(MemberModifyRequestDto dto) {
        this.nickname = dto.getNickname();
        this.name = dto.getName();
        this.phoneNumber = dto.getPhoneNumber();
        this.gender = dto.getGender();
        this.updatedAt = Timestamp.from(Instant.now());
    }

    public void updateLastLoginDate() {
        this.lastLoginDate = Timestamp.from(Instant.now());
    }

    public void updateDeletedAt() {
        this.deletedAt = Timestamp.from(Instant.now());
    }

    public void updateRole(@NotNull(message = "권한 정보는 필수입니다.") MemberRole role) {
        this.role = role;
    }

    public void updateEmailVerified(boolean isVerified) {
        this.emailVerified = isVerified;
    }

    public void updatePassword(String encodedPassword) {
        this.password = encodedPassword;
    }
}