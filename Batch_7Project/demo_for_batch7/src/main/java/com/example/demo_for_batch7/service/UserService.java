package com.example.demo_for_batch7.service;

import com.example.demo_for_batch7.dtos.PageableResponse;
import com.example.demo_for_batch7.dtos.UserDto;

import java.util.List;

public interface UserService {
  UserDto createUser(UserDto userDto);

  UserDto updateUser(UserDto userDto, String userId);

  void deleteUser(String userId);

  PageableResponse<UserDto>getAllUser(int pageNumber, int pageSize, String sortBy, String sortDir);

  UserDto getUserById(String userId);

  UserDto getUserByEmail(String email);

  List<UserDto> searchUser(String keyword);


}