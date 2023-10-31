package live.learnlynx.api.v1.services;

import live.learnlynx.api.v1.dtos.UpdateUserDTO;
import live.learnlynx.api.v1.enums.ERole;
import live.learnlynx.api.v1.fileHandling.File;
import live.learnlynx.api.v1.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.util.List;
import java.util.UUID;


public interface IUserService {

    public List<User> getAll();

    public Page<User> getAll(Pageable pageable);

    public User getById(UUID id);

    public User create(User user);

    public User update(UpdateUserDTO dto);

    public boolean delete();
    public boolean deleteById(UUID id);

    public List<User> getAllByRole(ERole role);

    public Page<User> getAllByRole(Pageable pageable, ERole role);

    public List<User> searchUser(String searchKey);

    public Page<User> searchUser(Pageable pageable, String searchKey);

    public User getLoggedInUser();

    public User getByEmail(String email);

    public User changeProfileImage(UUID id, File file);

}