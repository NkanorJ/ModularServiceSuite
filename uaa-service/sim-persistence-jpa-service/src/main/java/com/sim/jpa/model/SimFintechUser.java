package com.sim.jpa.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sim.commons.dto.registrationdto.SimFintechUserDto;
import com.sim.commons.enumeration.Gender;
import com.sim.commons.enumeration.LoanStatus;
import com.sim.commons.enumeration.Role;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

@Builder
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"password", "resetToken", "tries", "deletedAt"})
@Table(name = "users")
public class SimFintechUser extends AbstractAuditingEntity implements Serializable {


    @Id
    @Builder.Default
    private UUID id = UUID.randomUUID();

    @Column(nullable = false, length = 64)
    private String firstName;

    @Column(nullable = false, length = 64)
    private String lastName;

    private LocalDate dateOfBirth;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @JsonIgnore
    private String password;

    @Column(length = 56)
    private String email;

    @Column(length = 16)
    private String mobile;

    private String secret;

    private UUID publicId;

    @Enumerated(EnumType.STRING)
    private Role role;

    private boolean deleted;

    private LoanStatus loanStatus;

    public SimFintechUser(UUID id, String firstName, String lastName, LocalDate dateOfBirth, Gender gender, String password,
                          String email, String mobile, String secret, UUID publicId, Role role) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.password = password;
        this.email = email;
        this.mobile = mobile;
        this.secret = secret;
        this.publicId = publicId;
        this.role = role;
    }

    public SimFintechUserDto toDto() {
        return new SimFintechUserDto(id, firstName, lastName, gender, password, email, mobile, secret, publicId, role, dateOfBirth, loanStatus);
    }
}