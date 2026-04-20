package org.example.swamcappugilemmo.DomainModel;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;



//@MappedSuperclass   //impedisce query su User
@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)   //in alternativa
public abstract class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idUser;
    @Column(length = 16, unique = true)
    private String tax_code;
    private String name;
    private String surname;
    @Column(unique = true)
    private String username;
    private String password;
    private String email;
    private String phone_number;
    private LocalDate birth_date;

}

