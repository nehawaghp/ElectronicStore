package com.example.demo_for_batch7.controller;

import com.example.demo_for_batch7.dtos.ApiResponseMessage;
import com.example.demo_for_batch7.dtos.ImageResponse;
import com.example.demo_for_batch7.dtos.PageableResponse;
import com.example.demo_for_batch7.dtos.UserDto;
import com.example.demo_for_batch7.service.FileService;
import com.example.demo_for_batch7.service.UserService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;


public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private FileService fileService;

    @Value("${user.profile.image.path")
    private String imageUploadPath;

    private Logger logger =  LoggerFactory.getLogger(UserController.class);


    private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(UserController.class);

    @PostMapping("/")
    public ResponseEntity<UserDto> createUser(@Validated @RequestBody UserDto userDto) {
        LOGGER.info("Initiating request to create user");
        UserDto user = userService.createUser(userDto);
        LOGGER.info("Completed request to create user");
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }


    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@PathVariable("userId") String userId, @RequestBody UserDto userDto) {
        LOGGER.info("Initiating request to update userID");
        UserDto updatedUserDto = userService.updateUser(userDto, userId);
        LOGGER.info("Completed request of update user");
        return new ResponseEntity<>(updatedUserDto, HttpStatus.OK);
    }


    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponseMessage> deleteUser(@PathVariable("userId") String userId) {

        LOGGER.info("Initiating  request to delete userId");
        userService.deleteUser(userId);
        ApiResponseMessage message = ApiResponseMessage
                .builder()
                .message("User is deleted successfully!!")
                .success(true)
                .status(HttpStatus.OK)
                .build();
        LOGGER.info("Completed request of delete userId");
        return new ResponseEntity<>(message, HttpStatus.OK);
    }


    @GetMapping("/")
    public ResponseEntity<PageableResponse<UserDto>> getAllUsers(

            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "name", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir) {
        LOGGER.info("Initiating  request to  getAllUse");
        return new ResponseEntity<>(userService.getAllUser(pageNumber, pageSize, sortBy, sortDir), HttpStatus.OK);
    }


    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUsers(@PathVariable
                                                    String userId) {
        LOGGER.info("Initiating request to getUsers");
        return new ResponseEntity<>(userService.getUserById(userId), HttpStatus.OK);
    }


    @GetMapping("/email/{email}")
    public ResponseEntity<UserDto> getUserByEmail(@PathVariable String email) {
        LOGGER.info("Initiating request to getUserByEmail");
        return new ResponseEntity<>(userService.getUserById(email), HttpStatus.OK);
    }


    @GetMapping("/search/{keyword}")
    public ResponseEntity<UserDto> searchUser(@PathVariable String keywords) {
        LOGGER.info("Initiating request to searchUser");
        return new ResponseEntity(userService.searchUser(keywords), HttpStatus.OK);
    }

    // upload user image
    @PostMapping("/image/{userId}")
    public ResponseEntity<ImageResponse> uploadUserImage(@RequestParam("userImage") MultipartFile image, @PathVariable String userId) throws IOException {
        String imageName = fileService.uploadFile(image, imageUploadPath);
        UserDto userDto = userService.getUserById(userId);
        userDto.setImageName(imageName);
        userService.updateUser(userDto, userId);
        ImageResponse imageResponse = ImageResponse.builder().imageName(imageName).success(true).status(HttpStatus.CREATED).build();
        return new ResponseEntity<>(imageResponse, HttpStatus.CREATED);
    }
 // server user image
    @GetMapping("/image/{userId}")
    public void serverUserImage(@PathVariable String userId, HttpServletResponse response) throws IOException {
        UserDto userDto = userService.getUserById(userId);
        logger.info("User image name : {}", userDto.getImageName());
        InputStream resource = fileService.getResource(imageUploadPath, userDto.getImageName());
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource, response.getOutputStream());
    }
}



























































































































































































































































































































































































































































































































































































































































































