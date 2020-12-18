package tr.edu.yildiz.yazilimkalite.librarymanagement.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tr.edu.yildiz.yazilimkalite.librarymanagement.model.UserRole;
import tr.edu.yildiz.yazilimkalite.librarymanagement.repository.UserRoleRepository;

@Service
public class UserRoleService {
    @Autowired
    private UserRoleRepository userRoleRepository;

    public List<UserRole> getAllUserRoles() {
        List<UserRole> roles = new ArrayList<>();
        userRoleRepository.findAll().forEach(roles::add);
        return roles;
    }

    public UserRole saveUserRole(UserRole userRole) {
        return userRoleRepository.save(userRole);
    }

}
