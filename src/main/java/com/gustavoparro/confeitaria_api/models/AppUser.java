package com.gustavoparro.confeitaria_api.models;

import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "app_user")
public class AppUser implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @Email
    @NotBlank
    @Column(unique = true)
    private String username;

    @NotBlank
    @Length(min = 6)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "app_user_authority",
            joinColumns = @JoinColumn(name = "app_user_id", nullable = false, foreignKey = @ForeignKey(name = "fk_app_user_authority__app_user")),
            inverseJoinColumns = @JoinColumn(name = "authority_id", nullable = false, foreignKey = @ForeignKey(name = "fk_app_user_authority__authority")),
            uniqueConstraints = @UniqueConstraint(columnNames = {"app_user_id", "authority_id"}, name = "unique_app_user_authority"))
    private List<Authority> authorities;

    @OneToMany(mappedBy = "appUser")
    @ToString.Exclude
    private List<Adress> adressList;

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @PrePersist
    private void onPersist() {
        setPassword(new BCryptPasswordEncoder().encode(getPassword()));
    }

    @PreUpdate
    private void onUpdate() {
        setPassword(new BCryptPasswordEncoder().encode(getPassword()));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        AppUser appUser = (AppUser) o;
        return id != null && Objects.equals(id, appUser.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
