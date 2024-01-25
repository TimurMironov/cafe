package com.tmir.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@jakarta.persistence.Table(name = "_users")
public class User implements UserDetails, Comparable<User>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "accountNonExpired")
    private boolean accountNonExpired;

    @Column(name = "accountNonLocked")
    private boolean accountNonLocked;

    @Column(name = "credentialsNonExpired")
    private boolean credentialsNonExpired;

    @Column(name = "enabled")
    private boolean enabled;

    @Column(name = "password")
    private String password;

    @Column(name = "username")
    private String username;

    @Version
    private Integer version;

    @Column(name = "_authorities")
    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @Enumerated(value = EnumType.STRING)
    private List<Role> authorities;

    @OneToMany(mappedBy = "user")
    private List<Booking> bookings;

    @Override
    public int compareTo(User user) {
        return this.id.compareTo(user.getId());
    }
}
