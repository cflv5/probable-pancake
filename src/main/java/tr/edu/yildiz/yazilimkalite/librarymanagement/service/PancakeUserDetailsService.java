package tr.edu.yildiz.yazilimkalite.librarymanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import tr.edu.yildiz.yazilimkalite.librarymanagement.model.PancakeUserDetails;
import tr.edu.yildiz.yazilimkalite.librarymanagement.model.User;

@Service
public class PancakeUserDetailsService implements UserDetailsService {
    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userService.getUserByEmail(email);

        if(user == null) {
            throw new UsernameNotFoundException("No user with " + email + " found.");
        } 
        
        return new PancakeUserDetails(user);
    }
    
}
