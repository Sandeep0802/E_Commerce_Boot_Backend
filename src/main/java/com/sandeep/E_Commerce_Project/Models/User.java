package com.sandeep.E_Commerce_Project.Models;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.Set;

@Data
@Document(collection = "users")
public class User {

    @Id
    private String id;

    private String username;     // login username
    private String email;        // unique email
    private String password;     // bcrypt password

    private Set<Role> roles;     // ROLE_USER, ROLE_ADMIN

    private Address address;   // optional user address

    private boolean enabled = true;
    private Instant createdAt = Instant.now();
}
