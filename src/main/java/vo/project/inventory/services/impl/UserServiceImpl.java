package vo.project.inventory.services.impl;

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

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

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
}
