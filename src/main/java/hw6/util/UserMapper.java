package hw6.util;

import hw6.dto.ProductDto;
import hw6.dto.UserDto;
import hw6.model.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@AllArgsConstructor
@Component
public class UserMapper {

    private final ProductMapper productMapper;

    public List<UserDto> toDTO(List<User> users, boolean includeProducts) {
        if (users == null || users.isEmpty())
            return Collections.emptyList();

        return users.stream().map(user -> toDTO(user, includeProducts)).toList();
    }

    public UserDto toDTO(User user, boolean includeProducts) {
        if (user == null)
            return null;

        List<ProductDto> productDtos = null;

        if (includeProducts && user.getProducts() != null) {
            productDtos = productMapper.toDTO(user.getProducts());
        }

        return new UserDto(user.getId(), user.getUsername(), productDtos);
    }
}
