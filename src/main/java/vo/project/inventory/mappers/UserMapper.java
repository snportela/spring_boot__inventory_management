package vo.project.inventory.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import vo.project.inventory.domain.User;
import vo.project.inventory.dtos.user.RegisterDto;
import vo.project.inventory.dtos.user.UserDto;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    UserDto userToDto(User user);

    User dtoToUser(UserDto userDto);

}
