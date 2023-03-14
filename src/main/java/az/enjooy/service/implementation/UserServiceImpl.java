package az.enjooy.service.implementation;

import az.enjooy.dto.ResponseDTO;
import az.enjooy.dto.UserInfoDTO;
import az.enjooy.dto.UserPageDTO;
import az.enjooy.dto.authentication.LoginDTO;
import az.enjooy.exception.*;
import az.enjooy.model.*;
import az.enjooy.model.entity.ConfirmationToken;
import az.enjooy.model.entity.Role;
import az.enjooy.model.entity.User;
import az.enjooy.repository.RoleRepository;
import az.enjooy.repository.UserDao;
import az.enjooy.repository.UserRepository;
import az.enjooy.dto.RoleDTO;
import az.enjooy.dto.authentication.UserDTO;
import az.enjooy.mapper.UserMapper;
import az.enjooy.service.abstraction.EmailService;
import az.enjooy.service.abstraction.PasswordService;
import az.enjooy.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl {

    Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder encoder;
    private final UserDao userDao;
    private final ConfirmationTokenServiceImpl ctService;
    private final EmailService emailService;
    private final PasswordService passwordService;

    @Value("${base.url}")
    private String baseUrl;

    public ResponseDTO login(LoginDTO loginDTO) {
        String email = loginDTO.getEmail();
        String password = loginDTO.getPassword();
        User user = findByEmail(email);
        if (user != null && !user.isActive())
            throw new RuntimeException("Account is not activated");
        String username = null;
        if (user == null)
            throw new NoSuchAccountException();
        User userA = userRepository.findByEmail(loginDTO.getEmail()).get();
        if (!passwordService.isValidPassword(loginDTO.getPassword(), userA.getPassword()))
            throw new InvalidPasswordException();
            username = user.getUsername();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Basic b3JhbmdlbGluZTpvcmFuZ2VsaW5lMTIz");
        String url = "http://localhost:8080/oauth/token?username=" + username + "&password=" + password + "&grant_type=password";
        return ResponseDTO.builder()
                .success(true)
                .data(new RestTemplate().postForObject(url, new HttpEntity<>(null, headers), Object.class))
                .build();
    }

    public UserInfoDTO getUserInfo(String username) {
        User authenticatedUser = findByUsername(username);
        return new UserInfoDTO(authenticatedUser);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    public Boolean checkUserExistence(String email) {
        return userRepository
                .findByEmail(email)
                .isPresent();
    }


    public User save(User user) {
        return userRepository.save(user);
    }

    public void addUserRole(UserDTO userDTO) {
        userDTO.getRoles().add(RoleDTO.builder()
                .id(2L)
                .name("USER")
                .build());
    }

    public ResponseDTO save(UserDTO userDTO) {
        logger.info("saving user: " + userDTO);
        addUserRole(userDTO);
        if (userDTO.getId() == null && checkUserExistence(userDTO.getEmail()))
            throw new EmailExistsException("Email is already taken");

        Set<Role> newRoles = new LinkedHashSet();
        Set<Role> roles = new LinkedHashSet();
        User user;
        if (userDTO.getId() != null) {
            user = findById(userDTO.getId());
        } else {
            user = new User();
        }
        checkUserExistence(userDTO.getUsername(), userDTO.getId());
        user.setUsername(userDTO.getUsername());
        user.setPhotoUrl(userDTO.getPhoto());
        user.setFirstName(UserUtil.divideFullName(userDTO.getFullName()).getFirstName());
        user.setLastName(UserUtil.divideFullName(userDTO.getFullName()).getLastName());
        user.setEmail(userDTO.getEmail());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setGender(userDTO.getGender());
        if (userDTO.getPassword() != null && !userDTO.getPassword().equals("")) {
            user.setPassword(encoder.encode(userDTO.getPassword()));
        }
        for (Role role : new ArrayList<>(user.getRoles())) {
            role.removeUser(user);
            roles.add(role);
        }
        roleRepository.saveAll(roles);
        for (RoleDTO roleDTO : userDTO.getRoles()) {
            Role role = roleRepository.findById(roleDTO.getId()).get();
            newRoles.add(role);
        }
        newRoles.forEach(newRole -> newRole.addUser(user));
        user.setRoles(newRoles);
        if (userDTO.getId() == null) {
            user.setCreatedAt(LocalDateTime.now());
            ConfirmationToken confirmationToken = new ConfirmationToken(
                    UUID.randomUUID() + "email",
                    LocalDateTime.now(),
                    LocalDateTime.now().plusDays(1),
                    user
            );
            ctService.saveConfirmationToken(confirmationToken);
            String link = baseUrl + "/users/confirm?token=" + confirmationToken.getToken();
            emailService.send(user.getEmail(), buildEmail(user.getFullName(), link), "email");
        }
        userRepository.save(user);
        return ResponseDTO.builder()
                .success(true)
                .data(user)
                .build();
    }


    @Transactional
    public ResponseDTO confirmToken(String token) {
        ConfirmationToken confirmationToken = ctService.findByToken(token);
        if (confirmationToken.getConfirmedAt() != null) throw new IllegalStateException("email already confirmed");
        LocalDateTime expiredAt = confirmationToken.getExpiresAt();
        if (expiredAt.isBefore(LocalDateTime.now())) throw new IllegalStateException("token expired");
        ctService.setConfirmedAt(token);
        enableAppUser(confirmationToken.getUser().getEmail());
        return ResponseDTO.builder()
                .success(true)
                .data("Email has been confirmed successfully")
                .build();
    }

    public void enableAppUser(String email) {
        userRepository.enableAppUser(email);
    }

    public String buildEmail(String name, String link) {
        if (link.contains("confirm-password"))
            return "<p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Hi " + name + ",</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> Please click on the below link to reset your password: </p><blockquote style=\"Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\"><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> <a href=\"" + link + "\">Reset Now</a> </p></blockquote>\n Link will expire in 15 minutes. <p>See you soon</p>";
        else
            return "<p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Hi " + name + ",</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> Thank you for registering. Please click on the below link to activate your account: </p><blockquote style=\"Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\"><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> <a href=\"" + link + "\">Activate now</a> </p></blockquote>\n Link will expire in 15 minutes. <p>See you soon</p>";
    }


    public ResponseDTO activateOrDeactivate(Long id, boolean isActive) {
        User user = findById(id);
        user.setActive(isActive);
        return new ResponseDTO(userRepository.save(user));
    }

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found with id = " + id));
    }


    public ResponseDTO findByIdAndMapToDTO(Long id) {
        User user = findById(id);
        return new ResponseDTO(mapUserToUserDTO(user));
    }

    public UserDTO mapUserToUserDTO(User user) {
        UserDTO userDTO = UserMapper.MAPPER.fromEntity(user);
        userDTO.getRoles().clear();
        user.getRoles()
                .stream()
                .filter(x -> x.getId() != null)
                .forEach(x -> userDTO.getRoles()
                        .add(RoleDTO.builder()
                                .id(x.getId())
                                .name(x.getName())
                                .build()));
        return userDTO;
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public ResponseDTO deleteUser(Long id) {
        userDao.delete(id);
        return new ResponseDTO();
    }

    public void checkUserExistence(String username, Long id) {
        User user = findByUsername(username);
        User existingUser = null;
        if (id != null) {
            existingUser = findById(id);
        }
        if ((id == null && user != null) ||
                (existingUser != null && user != null && user.getId() != existingUser.getId())) {
            throw new UsernameAlreadyExistsException("User with username " + username + " already exists");
        }
    }

    public boolean userExists(String username) {
        User user = null;
        if (username != null) {
            user = userRepository.findByUsername(username);
        }
        return user != null;
    }

    public ResponseDTO findAll(int page, int size) {
        Page<User> userPage = userRepository.findAllByOrderByActiveDescFirstNameAscLastNameAsc(PageRequest.of(page, size));
        return new ResponseDTO(userPageToUserDtoPage(userPage));
    }

    public Page<UserDTO> userPageToUserDtoPage(Page<User> users) {
        Page<UserDTO> userDTOS = users.map(this::mapUserToUserDTO);
        return userDTOS;
    }

    public ResponseDTO findAllActiveUsers() {
        List<User> users = userRepository.findAllByActiveIsTrueOrderByFirstNameAscLastNameAsc();
        return new ResponseDTO(users
                .stream()
                .map(user -> new LightUser(user))
                .collect(Collectors.toList()));
    }

    public ResponseDTO findAllUsers() {
        return new ResponseDTO(userRepository.findAllByActiveIsTrueOrderByFirstNameAscLastNameAsc());
    }
}
