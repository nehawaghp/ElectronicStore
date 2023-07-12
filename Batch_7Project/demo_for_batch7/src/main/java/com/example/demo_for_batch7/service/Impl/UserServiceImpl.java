package com.example.demo_for_batch7.service.Impl;

import com.example.demo_for_batch7.constant.AppConstant;
import com.example.demo_for_batch7.dtos.PageableResponse;
import com.example.demo_for_batch7.dtos.UserDto;
import com.example.demo_for_batch7.entity.User;
import com.example.demo_for_batch7.exception.ResourceNotFoundException;
import com.example.demo_for_batch7.helper.Helper;
import com.example.demo_for_batch7.repository.UserRepo;
import com.example.demo_for_batch7.service.UserService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
        User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException(AppConstant.USER_NOT_FOUND));
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
        User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException(AppConstant.USER_NOT_FOUND));
        userRepo.delete(user);
        LOGGER.info("Completed request of delete userId" );
    }

    @Override
    public PageableResponse<UserDto> getAllUser(int pageNumber, int pageSize, String sortBy, String sortDir ){

        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) :(Sort.by(sortBy).ascending());
        // pageNumber default start fom 0
        Pageable pageable = PageRequest.of(pageNumber-1, pageSize, sort );
        Page<User> page = userRepo.findAll(pageable);

         PageableResponse<UserDto> response = Helper.getPageableResponse(page, UserDto.class);
        List<User> users = page.getContent();
        LOGGER.info("Initiating  request to  getAllUser" );
        List<UserDto> dtoList = users.stream().map(user -> entityToDto(user)).collect(Collectors.toList());
        LOGGER.info("Completed request to getAllUser");
        return response;
    }

    @Override
    public UserDto getUserById(String userId){

        LOGGER.info("Initiating request to getUserByID ");
        User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException(AppConstant.USER_NOT_FOUND));
        LOGGER.info("Completed request to getUserByID");
        return entityToDto(user);
    }

    @Override
    public UserDto getUserByEmail(String email){

        LOGGER.info("Initiating request to getUserByEmail");
        User user = userRepo.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException(AppConstant.Email_NOT_FOUND));
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
//        UserDto userDto = UserDto.builder()
//                .userId(savedUser.getUserId())
//                .name(savedUser.getName())
//                .email(savedUser.getEmail())
//                .password(savedUser.getPassword())
//                .about(savedUser.getAbout())
//                .gender(savedUser.getGender())
//                .imageName(savedUser.getImageName()).build();
        return mapper.map(savedUser,UserDto.class);
    }

    private User dtoToEntity(UserDto userDto) {
//        User user = User.builder()
//                .name(userDto.getName())
//                .userId(userDto.getUserId())
//                .email(userDto.getEmail())
//                .password(userDto.getPassword())
//               .about(userDto.getGender())
//                .gender(userDto.getGender()).build();
        return mapper.map(userDto,User.class);
    }
}