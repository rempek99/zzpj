package p.lodz.pl.zzpj.sharethebill.utils;

import p.lodz.pl.zzpj.sharethebill.dtos.UserDto;
import p.lodz.pl.zzpj.sharethebill.entities.User;

import java.util.List;
import java.util.stream.Collectors;

public class UserConverter {
    private UserConverter() {
    }

    public static List<UserDto> toDtoList(List<User> userList) {
        return userList
                .stream()
                .map(UserConverter::toDto)
                .collect(Collectors.toList());
    }

    public static UserDto toDto(User user) {
        return UserDto
                .builder()
                .id(user.getId())
                .login(user.getLogin())
                .email(user.getEmail())
                .role(user.getRole().toString())
                .build();
    }

    public static User toEntity(UserDto user) {
        return new User(user.getId(), user.getLogin(), user.getRole());
    }
}
