package live.learnlynx.api.v1.controllers;

import live.learnlynx.api.v1.dtos.SignUpDTO;
import live.learnlynx.api.v1.dtos.UpdateUserDTO;
import live.learnlynx.api.v1.enums.ERole;
import live.learnlynx.api.v1.exceptions.BadRequestException;
import live.learnlynx.api.v1.fileHandling.File;
import live.learnlynx.api.v1.fileHandling.FileStorageService;
import live.learnlynx.api.v1.models.Role;
import live.learnlynx.api.v1.models.User;
import live.learnlynx.api.v1.payload.ApiResponse;
import live.learnlynx.api.v1.repositories.IRoleRepository;
import live.learnlynx.api.v1.security.JwtTokenProvider;
import live.learnlynx.api.v1.services.IFileService;
import live.learnlynx.api.v1.services.IUserService;
import live.learnlynx.api.v1.utils.Constants;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/users")
public class UserController {

    private final IUserService userService;
    private static final ModelMapper modelMapper = new ModelMapper();
    private final IRoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final FileStorageService fileStorageService;
    private final IFileService fileService;

    @Value("${uploads.directory.user_profiles}")
    private String directory;

    @GetMapping(path = "/current-user")
    public ResponseEntity<ApiResponse> currentlyLoggedInUser() {
        return ResponseEntity.ok(new ApiResponse(true, userService.getLoggedInUser()));
    }

    @GetMapping
    public List<User> getAllUsers() {
        return this.userService.getAll();
    }

    @GetMapping(path = "/paginated")

    public Page<User> getAllUsers(@RequestParam(value = "page", defaultValue = Constants.DEFAULT_PAGE_NUMBER) int page,
                                  @RequestParam(value = "size", defaultValue = Constants.DEFAULT_PAGE_SIZE) int limit
    ) {
        Pageable pageable = (Pageable) PageRequest.of(page, limit, Sort.Direction.ASC, "id");
        return userService.getAll(pageable);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<User> getById(@PathVariable(value = "id") UUID id) {
        return ResponseEntity.ok(this.userService.getById(id));
    }

    @PostMapping(path = "/register")
    public ResponseEntity<ApiResponse> register(@RequestBody @Valid SignUpDTO dto) {

        User user = new User();


        String encodedPassword = bCryptPasswordEncoder.encode(dto.getPassword());
        Role role = roleRepository.findByName(dto.getRole()).orElseThrow(
                () -> new BadRequestException("User Role not set"));

        user.setEmail(dto.getEmail());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setGender(dto.getGender());
        user.setMobile(dto.getMobile());
        user.setPassword(encodedPassword);
        user.setRoles(Collections.singleton(role));

        User entity = this.userService.create(user);

        return ResponseEntity.ok(new ApiResponse(true, entity));
    }

    @PatchMapping("/update")
    public ResponseEntity<ApiResponse> updateUser(@RequestBody UpdateUserDTO dto) {
        return ResponseEntity.ok().body(new ApiResponse(true, "Updated user successfully", this.userService.update(dto)));
    }

    @PutMapping(path = "/upload-profile")
    public ResponseEntity<ApiResponse> uploadProfileImage(
            @RequestParam("file") MultipartFile document
    ) {
        File file = this.fileService.create(document, directory);
        UUID userId = this.userService.getLoggedInUser().getId();
        User updated = this.userService.changeProfileImage(userId, file);

        return ResponseEntity.ok(new ApiResponse(true, "File saved successfully", updated));

    }

    @GetMapping("/load-file/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> loadProfileImage(@PathVariable String filename) {

        Resource file = this.fileStorageService.load(directory, filename);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }

    @DeleteMapping("/delete")
    private ResponseEntity<ApiResponse> deleteUser() {
        return ResponseEntity.ok()
                .body(new ApiResponse(true, "User deleted successfully", this.userService.delete()));
    }

    @DeleteMapping("/delete/{id}")
    private ResponseEntity<ApiResponse> deleteUserbyId(@PathVariable(name = "id") UUID userId) {
        return ResponseEntity.ok()
                .body(new ApiResponse(true, "User deleted successfully", this.userService.deleteById(userId)));
    }

    @GetMapping("/role/{role}")
    private ResponseEntity<ApiResponse> getUsersByRole(@PathVariable(name = "role") ERole role) {
        return ResponseEntity.ok().body(new ApiResponse(true, "Users fetched successfully", this.userService.getAllByRole(role)));
    }

    @GetMapping("/paginated/role/{role}")
    private ResponseEntity<ApiResponse> getUsersPaginatedByRole(
            @RequestParam(value = "page", defaultValue = Constants.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(value = "size", defaultValue = Constants.DEFAULT_PAGE_SIZE) int limit,
            @PathVariable(name = "role") ERole role) {
        Pageable pageable = (Pageable) PageRequest.of(page, limit, Sort.Direction.ASC, "id");
        return ResponseEntity.ok().body(new ApiResponse(true, "Users fetched successfully", this.userService.getAllByRole(pageable, role)));
    }

    @GetMapping("/search/{query}")
    private ResponseEntity<ApiResponse> searchUsers(@PathVariable(name = "query") String query) {
        return ResponseEntity.ok().body(new ApiResponse(true, "Users fetched successfully", this.userService.searchUser(query)));
    }

    @GetMapping("/search/paginated/{query}")
    private ResponseEntity<ApiResponse> searchUsersPaginated(
            @RequestParam(value = "page", defaultValue = Constants.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(value = "size", defaultValue = Constants.DEFAULT_PAGE_SIZE) int limit,
            @PathVariable(name = "query") String query) {
        Pageable pageable = (Pageable) PageRequest.of(page, limit, Sort.Direction.ASC, "id");
        return ResponseEntity.ok().body(new ApiResponse(true, "Users fetched successfully", this.userService.searchUser(pageable, query)));
    }

    private User convertDTO(SignUpDTO dto) {
        return modelMapper.map(dto, User.class);
    }
}