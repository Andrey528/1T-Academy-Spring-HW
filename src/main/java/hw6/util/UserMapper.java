package hw6.util;

import hw6.dto.ProductDto;
import hw6.dto.UserDto;
import hw6.model.User;

import java.util.Collections;
import java.util.List;

public class UserMapper {

    public static List<UserDto> toDTO(List<User> users, boolean includeProducts) {
        if (users == null || users.isEmpty())
            return Collections.emptyList();

        return users.stream().map(user -> toDTO(user, includeProducts)).toList();
    }

    public static UserDto toDTO(User user, boolean includeProducts) {
        if (user == null)
            return null;

        List<ProductDto> productDtos = null;

        if (includeProducts && user.getProducts() != null) {
            productDtos = ProductMapper.toDTO(user.getProducts());
        }

        return new UserDto(user.getId(), user.getUsername(), productDtos);
    }
}
