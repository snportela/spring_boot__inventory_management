package vo.project.inventory.services;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import vo.project.inventory.domain.User;
import vo.project.inventory.dtos.user.RegisterDto;
import vo.project.inventory.dtos.user.UserDto;

import java.util.Map;
import java.util.UUID;

public interface UserService {

    void register(RegisterDto registerDto);

    Map<String, Object> findAll(Specification<User> spec, Pageable pageable);

    UserDto findOne(UUID userId);

    UserDto update(UUID userId, UserDto userDto);

    void delete(UUID userId);

    void sendPasswordResetEmail(String email, String token);

    void redeemPassword(String email);

    void resetPassword(String token, String password);
}
