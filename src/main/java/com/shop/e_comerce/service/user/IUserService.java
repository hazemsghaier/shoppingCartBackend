package com.shop.e_comerce.service.user;

import com.shop.e_comerce.DTO.UserDto;
import com.shop.e_comerce.model.User;
import com.shop.e_comerce.request.CreateUserRequest;
import com.shop.e_comerce.request.UserUpdateRequest;

public interface IUserService {

    User getUserById(Long userId);
    User createUser(CreateUserRequest request);
    User updateUser(UserUpdateRequest request, Long userId);
    void deleteUser(Long userId);

    UserDto convertUserToDto(User user);
}