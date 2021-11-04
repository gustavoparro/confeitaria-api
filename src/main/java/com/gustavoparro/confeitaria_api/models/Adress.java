package com.gustavoparro.confeitaria_api.models;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Objects;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "adress")
public class Adress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name = "zip_code")
    private String zipCode;

    @NotBlank
    private String avenue;

    @NotBlank
    private String district;

    @NotBlank
    private String number;

    private String complement;

    @NotBlank
    private String city;

    @NotBlank
    private String state;

    @ManyToOne
    @JoinColumn(name = "app_user_id", nullable = false, foreignKey = @ForeignKey(name = "fk_adress__app_user"))
    private AppUser appUser;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Adress adress = (Adress) o;
        return id != null && Objects.equals(id, adress.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
