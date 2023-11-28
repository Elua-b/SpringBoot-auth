package live.learnlynx.api.v1.serviceImpls;

import live.learnlynx.api.v1.dtos.UpdateUserDTO;
import live.learnlynx.api.v1.enums.ERole;
import live.learnlynx.api.v1.exceptions.BadRequestException;
import live.learnlynx.api.v1.exceptions.ResourceNotFoundException;
import live.learnlynx.api.v1.fileHandling.File;
import live.learnlynx.api.v1.models.PasswordReset;
import live.learnlynx.api.v1.models.User;
import live.learnlynx.api.v1.models.Verification;
import live.learnlynx.api.v1.repositories.IPasswordResetRepository;
import live.learnlynx.api.v1.repositories.IUserRepository;
import live.learnlynx.api.v1.repositories.IVerificationRepository;
import live.learnlynx.api.v1.services.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {

    private final IUserRepository userRepository;
    private final IVerificationRepository verificationRepository;
    private final IPasswordResetRepository passwordResetRepository;

    @Override
    public List<User> getAll() {
        return this.userRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));
    }

    @Override
    public Page<User> getAll(Pageable pageable) {
        return this.userRepository.findAll(pageable);
    }

    @Override
    public User getById(UUID id) {
        return this.userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("User", "id", id.toString()));
    }

    @Override
    public User create(User user) {
        Optional<User> userOptional = this.userRepository.findByEmail(user.getEmail());
        if (userOptional.isPresent())
            throw new BadRequestException(String.format("User with email '%s' already exists", user.getEmail()));
        Verification verification = new Verification();
        PasswordReset passwordReset = new PasswordReset();
        this.verificationRepository.save(verification);
        this.passwordResetRepository.save(passwordReset);
        user.setVerification(verification);
        user.setPasswordReset(passwordReset);
        return this.userRepository.save(user);
    }

    @Override
    public User update(UpdateUserDTO dto) {
        User entity = this.getLoggedInUser();
        Optional<User> userOptional = this.userRepository.findByEmail(dto.getEmail());
        if (userOptional.isPresent() && (userOptional.get().getId() != entity.getId()))
            throw new BadRequestException(String.format("User with email '%s' already exists", entity.getEmail()));

        entity.setEmail(dto.getEmail());
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setMobile(dto.getMobile());
        entity.setGender(dto.getGender());


        return this.userRepository.save(entity);
    }

    @Override
    public boolean delete() {
        User user = this.getLoggedInUser();
        this.userRepository.deleteById(user.getId());
        return true;
    }

    @Override
    public boolean deleteById(UUID id) {
        this.userRepository.deleteById(id);
        return true;
    }

    @Override
    public List<User> getAllByRole(ERole role) {
        return this.userRepository.findByRoles(role);
    }

    @Override
    public Page<User> getAllByRole(Pageable pageable, ERole role) {
        return this.userRepository.findByRoles(pageable, role);
    }

    @Override
    public List<User> searchUser(String searchKey) {
        return this.userRepository.searchUser(searchKey);
    }

    @Override
    public Page<User> searchUser(Pageable pageable, String searchKey) {
        return this.userRepository.searchUser(pageable, searchKey);
    }

    @Override
    public User getLoggedInUser() {
        String email;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            email = ((UserDetails) principal).getUsername();
        } else {
            email = principal.toString();
        }

        return userRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("User", "id", email));
    }

    @Override
    public User getByEmail(String email) {
        return this.userRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("User", "id", email));
    }


    @Override
    public User changeProfileImage(UUID id, File file) {
        User entity = this.userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Document", "id", id.toString()));

        entity.setProfileImage(file);
        return this.userRepository.save(entity);
    }
}
