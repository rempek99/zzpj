package p.lodz.pl.zzpj.sharethebill.utils;

import p.lodz.pl.zzpj.sharethebill.dtos.UserDto;
import p.lodz.pl.zzpj.sharethebill.entities.User;

import java.util.List;
import java.util.stream.Collectors;

public class UserDtoConverter {
    private UserDtoConverter() {
    }

    public static List<UserDto> toDtoList(List<User> userList) {
        return userList
                .stream()
                .map(UserDtoConverter::toDto)
                .collect(Collectors.toList());
    }

    private static UserDto toDto(User user) {
        return UserDto
                .builder()
                .id(user.getId())
                .login(user.getLogin())
                .role(user.getRole())
                .build();
    }
}
