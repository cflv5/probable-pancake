package tr.edu.yildiz.yazilimkalite.librarymanagement.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import tr.edu.yildiz.yazilimkalite.librarymanagement.dto.UserRegistrationDto;
import tr.edu.yildiz.yazilimkalite.librarymanagement.dto.mapping.StatisticResultMapping;
import tr.edu.yildiz.yazilimkalite.librarymanagement.exception.EntityAlreadyExistsException;
import tr.edu.yildiz.yazilimkalite.librarymanagement.exception.NotExistingEntityException;
import tr.edu.yildiz.yazilimkalite.librarymanagement.exception.UserPasswordEmptyException;
import tr.edu.yildiz.yazilimkalite.librarymanagement.model.User;
import tr.edu.yildiz.yazilimkalite.librarymanagement.model.UserRole;
import tr.edu.yildiz.yazilimkalite.librarymanagement.repository.UserRepository;
import tr.edu.yildiz.yazilimkalite.librarymanagement.repository.UserRoleRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    public Page<User> getPaginatedUsersWithQuery(String query, Pageable page) {
        if(page == null) {
            page = PageRequest.of(0, 10);
        }

        return userRepository.findAllBySearchQuery(query, page);
    }

    public User saveUser(UserRegistrationDto userToSave) {
        User user = null;

        Optional<User> emailExists = userRepository.findByEmail(userToSave.getEmail());

        if (emailExists.isPresent()) {
            throw new EntityAlreadyExistsException(userToSave.getEmail() + " is already used.");
        }

        if (userToSave.getPassword() == null || userToSave.getPassword().trim().isEmpty()) {
            throw new UserPasswordEmptyException("Password cannot be empty.");
        }

        user = User.of(userToSave);

        List<UserRole> roles = userRoleRepository.findAllByIdIn(userToSave.getRoles());
        if (roles.size() != userToSave.getRoles().size()) {
            throw new NotExistingEntityException("At least one of the entered roles does not exist.");
        }
        user.setRoles(roles);

        userRepository.save(user);

        return user;
    }

    public User getUserByEmail(String email) {
        User user = null;

        Optional<User> fetchedUser = userRepository.findByEmail(email);

        if (fetchedUser.isPresent()) {
            user = fetchedUser.get();
        }

        return user;
    }

    public User editUser(User user, UserRegistrationDto editedUser) {
        if (user == null) {
            throw new NullPointerException("'user' parameter cannot be null!");
        }

        if (!user.getEmail().equals(editedUser.getEmail()) && getUserByEmail(editedUser.getEmail()) != null) {
            throw new EntityAlreadyExistsException(editedUser.getEmail() + " is already used!");
        }

        BeanUtils.copyProperties(editedUser, user, "id", "password", "roles");
        userRepository.save(user);

        return user;
    }

    public Page<User> searchUser(String query, Pageable page) {
        return userRepository.findAllBySearchQuery(query, page);
    }

	public List<StatisticResultMapping> getUserCountByStatus() {
		return userRepository.countGroupByStatus();
	}
}
