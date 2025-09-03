package vo.project.inventory.services.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import vo.project.inventory.domain.User;
import vo.project.inventory.dtos.user.RegisterDto;
import vo.project.inventory.dtos.user.UserDto;
import vo.project.inventory.exceptions.AlreadyExistsException;
import vo.project.inventory.exceptions.NotFoundException;
import vo.project.inventory.mappers.UserMapper;
import vo.project.inventory.repositories.UserRepository;
import vo.project.inventory.security.services.MailService;
import vo.project.inventory.security.services.ResetTokenService;
import vo.project.inventory.services.UserService;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final MailService mailService;
    private final ResetTokenService resetTokenService;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, MailService mailService, ResetTokenService resetTokenService) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.mailService = mailService;
        this.resetTokenService = resetTokenService;
    }

    @Value("${host.url}")
    private String hostUrl;

    @Override
    public void register(RegisterDto registerDto) {
        Optional<User> foundUser =  userRepository.findByEmail(registerDto.email());
        if(foundUser.isPresent()) throw new AlreadyExistsException("A user with this e-mail already exists");

        String encryptedPassword = new BCryptPasswordEncoder().encode(registerDto.password());

        User newUser = new User();
        newUser.setName(registerDto.name());
        newUser.setEmail(registerDto.email());
        newUser.setPassword(encryptedPassword);
        newUser.setRole(registerDto.role());

        userRepository.save(newUser);
    }

    @Override
    public Map<String, Object> findAll(Specification<User> spec, Pageable pageable) {
        Page<User> userList = userRepository.findAll(spec, pageable);

        Map<String, Object> response = new HashMap<>();

        response.put("totalItems", userList.getTotalElements());
        response.put("totalPages", userList.getTotalPages());
        response.put("users", userList.getContent().stream().map(userMapper::userToDto).collect(Collectors.toList()));
        response.put("currentPage", userList.getNumber());

        return response;
    }

    @Override
    public UserDto findOne(UUID userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("Could not find User with ID: " + userId));
        return userMapper.userToDto(user);
    }

    @Override
    public UserDto update(UUID userId, UserDto userDto) {
        User foundUser = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("Could not find User with ID: " + userId));

        foundUser.setName(userDto.name());
        foundUser.setEmail(userDto.email());

        userRepository.save(foundUser);

        return userMapper.userToDto(foundUser);
    }

    @Override
    public void delete(UUID userId) {
        userRepository.findById(userId).orElseThrow(() -> new NotFoundException("Could not find User with ID: " + userId));
        userRepository.deleteById(userId);
    }

    @Override
    public void sendPasswordResetEmail(String email, String token) {
        String subject = "Password Reset Request";
        String resetUrl = hostUrl + "reset?token=" + token;
        String body = "Click the link to reset your password: " + resetUrl;

        mailService.sendEmail(email, subject, body);
    }

    @Override
    public void redeemPassword(String email) {
        User foundUser = userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("Could not find User with e-mail: " + email));
        var token = resetTokenService.generateResetToken(foundUser);
        foundUser.setResetToken(token);

        userRepository.save(foundUser);

        sendPasswordResetEmail(email, token);

    }

    @Override
    public void resetPassword(String token, String password) {
        resetTokenService.validateResetToken(token);

        User foundUser = userRepository.findByResetToken(token).orElseThrow(() -> new NotFoundException("Could not find user"));

        String encryptedPassword = new BCryptPasswordEncoder().encode(password);

        foundUser.setPassword(encryptedPassword);
        foundUser.setResetToken(null);

        userRepository.save(foundUser);
    }
}
