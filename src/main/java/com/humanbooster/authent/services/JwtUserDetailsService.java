package com.humanbooster.authent.services;

import com.humanbooster.authent.models.Role;
import com.humanbooster.authent.models.User;
import com.humanbooster.authent.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        User user = userRepository.findByUsername(username);
        User user = userRepository.findByEmailAndAdminRole(email);
        if(user == null) {
            throw new UsernameNotFoundException("User with email " + email + " doesn't exist or isn't an admin");
        } else {
            return new org.springframework.security.core.userdetails.User(user.getEmail(),
                    user.getPassword(),
                    this.getAuthorities(user.getRoleList()));
        }

    }


    private Collection<? extends GrantedAuthority> getAuthorities(List<Role> roles){
        List<GrantedAuthority> authorityList = new ArrayList<GrantedAuthority>();

        for(Role role : roles) {
            authorityList.add(new SimpleGrantedAuthority(role.getRoleName()));
        }

        return authorityList;
    }
}
