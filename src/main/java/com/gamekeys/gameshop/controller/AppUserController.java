package com.gamekeys.gameshop.controller;

import com.gamekeys.gameshop.domain.user.AppUserDetails;
import com.gamekeys.gameshop.domain.user.AppUserDto;
import com.gamekeys.gameshop.exception.ExceptionHandling;
import com.gamekeys.gameshop.exception.domain.EmailExistException;
import com.gamekeys.gameshop.exception.domain.UserNotFoundException;
import com.gamekeys.gameshop.token.JWTTokenProvider;
import com.gamekeys.gameshop.service.AppUserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

import static com.gamekeys.gameshop.constant.FileConstant.TEMP_PROFILE_IMAGE_BASE_URL;
import static com.gamekeys.gameshop.constant.SecurityConstant.JWT_TOKEN_HEADER;
import static org.springframework.http.MediaType.IMAGE_JPEG_VALUE;

@Slf4j
@RestController
//@RequiredArgsConstructor
@AllArgsConstructor
@RequestMapping("/api/user")
//@RequestMapping({"/api","/api/user"})
@CrossOrigin(origins = "http://localhost:4200/")
public class AppUserController extends ExceptionHandling {
    // HTTP REST API: GET, POST, PUT, DELETE

    private final AppUserService appUserService;
    private JWTTokenProvider jwtTokenProvider;
    private AuthenticationManager authenticationManager;

    // hello world

    @PostMapping("/register") //former create
    public ResponseEntity<AppUserDto> registerUser(@RequestBody @Valid AppUserDto appUserDto) throws UserNotFoundException, EmailExistException, MessagingException {
        AppUserDto newUser = appUserService.registerUser(appUserDto);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @GetMapping("/login/{email}/{password}")
    public ResponseEntity<AppUserDto> loginUser(@PathVariable("email") String email, @PathVariable("password") String password) {
        authenticate(email, password);
        // Get the userDetails in order to generate the JWT header
        AppUserDetails appUserDetails = appUserService.getUserDetails(email);
        HttpHeaders jwtHeader = getJwtHeader(appUserDetails);
//        AppUser appUser = appUserDetails.getAppUser();
//        Set<AppRole> userRole = appUser.getRoles();
        AppUserDto loginUser = appUserService.findAppUserByEmail(email);
        return new ResponseEntity<>(loginUser, jwtHeader, HttpStatus.OK);
    }

//    @GetMapping("/login/")
//    public ResponseEntity<AppUserDto> loginUser(@RequestBody AppUserDto appUserDto) {
//        authenticate(appUserDto.getEmail(), appUserDto.getPassword());
//        // Get the userDetails in order to generate the JWT header
//        AppUserDetails appUserDetails = appUserService.getUserDetails(appUserDto.getEmail());
//        HttpHeaders jwtHeader = getJwtHeader(appUserDetails);
////        AppUser appUser = appUserDetails.getAppUser();
////        Set<AppRole> userRole = appUser.getRoles();
//
//        AppUserDto loginUser = appUserService.findAppUserByEmail(appUserDto.getEmail());
//        return new ResponseEntity<>(loginUser, jwtHeader, HttpStatus.OK);
//    }

    @GetMapping(path = "/image/profile/{username}", produces = IMAGE_JPEG_VALUE)
    public byte[] getTempProfileImage(@PathVariable("username") String username) throws IOException {
        URL url = new URL(TEMP_PROFILE_IMAGE_BASE_URL + username);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try (InputStream inputStream = url.openStream()) {
            int bytesRead;
            byte[] chunk = new byte[1024];
            while ((bytesRead = inputStream.read(chunk)) > 0) {
                byteArrayOutputStream.write(chunk, 0, bytesRead);
            }
        }
        return byteArrayOutputStream.toByteArray();
    }

    @GetMapping("/all")
    public ResponseEntity<List<AppUserDto>> getAllUsers() {
        List<AppUserDto> users = appUserService.findAllUsers();
        log.info("Retrieving Users '{}' ", users);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<AppUserDto> getUserById(@PathVariable("id") Long id) {
        AppUserDto user = appUserService.findUserById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<AppUserDto> updateUser(@RequestBody AppUserDto appUserDto) {
        AppUserDto updateUser = appUserService.updateUser(appUserDto);
        return new ResponseEntity<>(updateUser, HttpStatus.OK);
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long id) {
        appUserService.deleteAppUserById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private void authenticate(String username, String password) {
        // We told the authentication manager how to fetch the user (by implementing the UserDetails interface
        // and told it to look in the database by the username)
        // This will trigger the loadUserByUsername method in userService
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }

    private HttpHeaders getJwtHeader(AppUserDetails userDetails) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(JWT_TOKEN_HEADER, jwtTokenProvider.generateJwtToken(userDetails));
        return headers;
    }


}
