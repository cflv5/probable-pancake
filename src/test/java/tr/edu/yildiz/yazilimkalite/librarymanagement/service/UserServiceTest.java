package tr.edu.yildiz.yazilimkalite.librarymanagement.service;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import tr.edu.yildiz.yazilimkalite.librarymanagement.dto.UserRegistrationDto;
import tr.edu.yildiz.yazilimkalite.librarymanagement.exception.EntityAlreadyExistsException;
import tr.edu.yildiz.yazilimkalite.librarymanagement.exception.UserPasswordEmptyException;
import tr.edu.yildiz.yazilimkalite.librarymanagement.model.User;
import tr.edu.yildiz.yazilimkalite.librarymanagement.model.UserStatus;

@SpringBootTest
@ActiveProfiles("test")
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void registerNewUser_GivenUserRegistrationDtoObject() {
        final String email = "asd@asd";
        final String name = "Name";
        final String surname = "Surname";
        final String password = "password";
        final UserStatus status = UserStatus.ACTIVE;
        final List<Long> roles = Arrays.asList(1L, 2L);

        UserRegistrationDto userDto = new UserRegistrationDto()
                .email(email)
                .name(name)
                .surname(surname)
                .password(password)
                .status(status)
                .roles(roles);
        
        User user = userService.saveUser(userDto);

        assertNotNull(user);
        assertArrayEquals(new Object[] {
            email,
            name,
            surname,
            status,
            roles
        }, new Object[]{
            user.getEmail(),
            user.getName(),
            user.getSurname(),
            user.getStatus(),
            user.getRoles().stream().map(role -> role.getId()).collect(Collectors.toList())
        });
        assertTrue(passwordEncoder.matches(password, user.getPassword()));
    }

    @Test
    public void registerNewUser_ExistingEmailAddress_ShouldThrowException(){
        final String email = "root@root";

        UserRegistrationDto userDto = new UserRegistrationDto()
                .email(email)
                .name("root")
                .surname("root")
                .password("password")
                .status(UserStatus.ACTIVE)
                .roles(Arrays.asList(1L, 2L));

        //saves with the same userDto but user with the 'email' exists
        //it, hence, should throw EmailAlreadyExistsException
        assertThrows(EntityAlreadyExistsException.class, () -> userService.saveUser(userDto));
    }

    @Test
    public void registerNewUser_NoPasswordPresented_ShouldThrowException(){
        UserRegistrationDto userDto = new UserRegistrationDto()
                .email("asdasd@asd")
                .name("Name")
                .surname("Surname")
                .password(null)
                .status(UserStatus.ACTIVE)
                .roles(Arrays.asList(1L, 2L));

        //saves with the userDto whose password field is null
        //should throw UserPasswordEmptyException
        assertThrows(UserPasswordEmptyException.class, () -> userService.saveUser(userDto));
        
        //saving with empty password
        //should throw UserPasswordEmptyException
        userDto.email(userDto.getEmail()).password("");
        assertThrows(UserPasswordEmptyException.class, () -> userService.saveUser(userDto));
    }

    @Test
    public void fetchUser_GivenEmail(){
        UserRegistrationDto userDto = new UserRegistrationDto()
                .email("asdasd@asd")
                .name("Name")
                .surname("Surname")
                .password("password")
                .status(UserStatus.ACTIVE)
                .roles(Arrays.asList(1L, 2L));

        //saving user to fetch later
        User user = userService.saveUser(userDto);
        assertNotNull(user);
        
        User fetchedUser = userService.getUserByEmail(user.getEmail());
        assertNotNull(fetchedUser);
    }
}
