package com.example.demo_for_batch7.service.Impl;

import com.example.demo_for_batch7.dtos.UserDto;
import com.example.demo_for_batch7.entity.User;
import com.example.demo_for_batch7.repository.UserRepo;
import com.example.demo_for_batch7.service.UserService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper mapper;

    private  static  final  Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
    @Override
    public UserDto createUser(UserDto userDto){

        LOGGER.info("Initiating request to create user");
        String userId = UUID.randomUUID().toString();
        LOGGER.info("Completed request  of create user");

        userDto.setUserId(userId);

        // dto -> entity
        User user = dtoToEntity(userDto);
        LOGGER.info("Saving the User: "+user);
        User savedUser = userRepo.save(user);
        LOGGER.info("saved User "+savedUser);
        //entity -> dto
        UserDto newDto = entityToDto(savedUser);
        LOGGER.info("Conversion Successful");
        return newDto;
    }

    @Override
    public UserDto updateUser(UserDto userDto, String userId){

        LOGGER.info("Initiating request to update userID");
        User user = userRepo.findById(userId).orElseThrow(() -> new RuntimeException("User Not Found With ID"));
        user.setName(userDto.getName());
        user.setAbout(userDto.getAbout());
        user.setGender(userDto.getGender());
        user.setPassword(userDto.getPassword());
        user.setImageName(userDto.getImageName());
        User updatedUser = userRepo.save(user);
        UserDto updatedDto = entityToDto(updatedUser);
        LOGGER.info("Completed request of update user ");
        return updatedDto;
    }

    @Override
    public void deleteUser(String userId){

        LOGGER.info("Initiating  request to delete user Id");
        User user = userRepo.findById(userId).orElseThrow(() -> new RuntimeException("User Not Found With given Id"));
        userRepo.delete(user);
        LOGGER.info("Completed request of delete userId" );
    }

    @Override
    public List<UserDto> getAllUser(){

        LOGGER.info("Initiating  request to  getAllUser" );
        List<User> users = userRepo.findAll();
        List<UserDto> dtoList = users.stream().map(user -> entityToDto(user)).collect(Collectors.toList());
        LOGGER.info("Completed request to getAllUser");
        return dtoList;
    }

    @Override
    public UserDto getUserById(String userId){

        LOGGER.info("Initiating request to getUserByID ");
        User user = userRepo.findById(userId).orElseThrow(() -> new RuntimeException("User Not Found with Given Id"));
        LOGGER.info("Completed request to getUserByID");
        return entityToDto(user);
    }

    @Override
    public UserDto getUserByEmail(String email){

        LOGGER.info("Initiating request to getUserByEmail");
        User user = userRepo.findByEmail(email).orElseThrow(() -> new RuntimeException("User Not Found With Given Email Id And Password"));
        LOGGER.info("Completed request to getUserByEmail");
        return entityToDto(user);
    }

    @Override
    public List<UserDto> searchUser(String keyword){
        LOGGER.info("Initiating request to searchUser");
        List<User> users = userRepo.findByNameContaining(keyword);
        List<UserDto> dtoList = users.stream().map(user -> entityToDto(user)).collect(Collectors.toList());
        LOGGER.info("Completed request to searchUser");
        return dtoList;
    }
    private UserDto entityToDto(User savedUser) {
        UserDto userDto = UserDto.builder()
                .userId(savedUser.getUserId())
                .name(savedUser.getName())
                .email(savedUser.getEmail())
                .password(savedUser.getPassword())
                .about(savedUser.getAbout())
                .gender(savedUser.getGender())
                .imageName(savedUser.getImageName()).build();
        return mapper.map(savedUser,UserDto.class);
    }

    private User dtoToEntity(UserDto userDto) {
        User user = User.builder()
                .name(userDto.getName())
                .userId(userDto.getUserId())
                .email(userDto.getEmail())
                .password(userDto.getPassword())
               .about(userDto.getGender())
                .gender(userDto.getGender()).build();
        return mapper.map(userDto,User.class);
    }
}