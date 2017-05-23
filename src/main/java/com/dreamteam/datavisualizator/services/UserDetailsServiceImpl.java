package com.dreamteam.datavisualizator.services;

import com.dreamteam.datavisualizator.dao.UserDAO;
import com.dreamteam.datavisualizator.models.User;
import com.dreamteam.datavisualizator.models.UserTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserDAO userDAO;

    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userDAO.getUserByEmail(email);
        Set<GrantedAuthority> roles = new HashSet();

        //setting up user role
        switch (user.getUserType()) {
            case ADMIN:
                roles.add(new SimpleGrantedAuthority(UserTypes.ADMIN.toString()));
                break;
            case REGULAR_USER:
                roles.add(new SimpleGrantedAuthority(UserTypes.REGULAR_USER.toString()));
                break;
        }


        UserDetails userDetails = new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), roles);
        return userDetails;
    }
}
