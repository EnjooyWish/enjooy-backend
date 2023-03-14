package az.enjooy.controller;

import az.enjooy.dto.UserFavoritePostDTO;
import az.enjooy.dto.authentication.LoginDTO;
import az.enjooy.dto.authentication.PasswordResetDTO;
import az.enjooy.dto.ResponseDTO;
import az.enjooy.dto.authentication.UserPasswordDTO;
import az.enjooy.dto.item.ItemGetDTO;
import az.enjooy.service.abstraction.PasswordService;
import az.enjooy.service.abstraction.UserFavoriteService;
import az.enjooy.service.abstraction.UserRecommendService;
import az.enjooy.service.implementation.UserServiceImpl;
import az.enjooy.dto.authentication.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
@CrossOrigin("*")
public class AuthController {

    private final UserServiceImpl userService;
    private final PasswordService passwordService;
    private final UserFavoriteService userFavoriteService;
    private final UserRecommendService userRecommendService;

    @PostMapping("login")
    public ResponseEntity<ResponseDTO> login(@RequestBody LoginDTO loginDTO) {
        return ResponseEntity.ok(userService.login(loginDTO));
    }

    @GetMapping(path = "/confirm")
    public ResponseEntity<ResponseDTO> confirm(@RequestParam("token") String token) {
        return ResponseEntity.ok(userService.confirmToken(token));
    }

    @GetMapping
    @Validated
    public ResponseEntity<ResponseDTO> findAll(@RequestParam @Min(0) int page, @RequestParam int size) {
        return ResponseEntity.ok(userService.findAll(page, size));

    }

    @GetMapping("all")
    public ResponseEntity<ResponseDTO> findAllActiveLightUsers() {
        return ResponseEntity.ok(userService.findAllActiveUsers());

    }

    @GetMapping("extended-list")
    public ResponseEntity<ResponseDTO> findAllActiveUsers() {
        return ResponseEntity.ok(userService.findAllUsers());

    }

    @PostMapping
    public ResponseEntity<ResponseDTO> save(@Valid @RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(userService.save(userDTO));

    }

    @PostMapping("/password-changing")
    public ResponseEntity<ResponseDTO> changePassword(@RequestBody UserPasswordDTO passwordDTO) {
        return ResponseEntity.ok(passwordService.changePasswordByUser(passwordDTO));

    }

    @PostMapping("/password-resetting")
    public ResponseEntity<ResponseDTO> resetPassword(@RequestBody PasswordResetDTO passwordResetDTO) {
        return ResponseEntity.ok(passwordService.resetPassword(passwordResetDTO.getEmail(), passwordResetDTO.getPassword()));

    }

    @PutMapping("/{id}/activation")
    public ResponseEntity<ResponseDTO> activateOrDeactivateUser(@PathVariable Long id, @RequestParam boolean isActive) {
        return ResponseEntity.ok(userService.activateOrDeactivate(id, isActive));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO> deleteUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.deleteUser(id));

    }

    @GetMapping("/single/{id}")
    public ResponseEntity<ResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.findByIdAndMapToDTO(id));

    }

    @GetMapping("/{username}")
    public ResponseEntity<ResponseDTO> getUserPage(@PathVariable String username) {
        return new ResponseEntity<>(ResponseDTO.builder()
                .success(userService.userExists(username))
                .build(), HttpStatus.OK);
    }

    @GetMapping("/my-information")
    public ResponseEntity<ResponseDTO> getUserInfo(String username) {
        return new ResponseEntity<>(ResponseDTO.builder()
                .success(true)
                .data(userService.getUserInfo(username))
                .build(), HttpStatus.OK);
    }

    @GetMapping("/favorites")
    public ResponseEntity<ResponseDTO> getFavorites(String username, int page, int size) {
        return new ResponseEntity<>(ResponseDTO.builder()
                .success(true)
                .data(userFavoriteService.getAll(username, page, size))
                .build(), HttpStatus.OK);
    }

    @PostMapping("/favorites")
    public ResponseEntity<ResponseDTO> postFavorite(@RequestBody UserFavoritePostDTO postDTO) {
        return new ResponseEntity<>(ResponseDTO.builder()
                .success(true)
                .data(userFavoriteService.post(postDTO))
                .build(), HttpStatus.OK);
    }

    @GetMapping("/recommends")
    public ResponseEntity<List<ItemGetDTO>> getRecommends(String username) {
        return ResponseEntity.ok(userRecommendService.getRecommendItemsByUsername(username));
    }
}
