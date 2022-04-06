package cm.nsi.saarngan.services;

import cm.nsi.saarngan.entity.Role;
import cm.nsi.saarngan.entity.User;
import cm.nsi.saarngan.exceptions.UserNotFoundException;
import cm.nsi.saarngan.repository.RoleRepository;
import cm.nsi.saarngan.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * created by : Ravanely
 * create at : 06/04/2022, 10:07, mer.
 * saar-ngan
 **/
@Service
@Transactional
public class UserService {
    public static final int USERS_PER_PAGE = 4;
    private final UserRepository userRepo;
    private final RoleRepository roleRepo;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepo, RoleRepository roleRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> findAll() {
        return (List<User>) userRepo.findAll(Sort.by("firstName").ascending());
    }

    public Page<User> listByPage(int pageNum, String sortField, String sortDir, String keyword) {
        Sort sort = Sort.by(sortField);

        sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();

        Pageable pageable = PageRequest.of(pageNum - 1, USERS_PER_PAGE, sort);

        if (keyword != null)
            return userRepo.findAll(keyword, pageable);

        return userRepo.findAll(pageable);
    }

    public List<Role> listRoles() {
        return (List<Role>) roleRepo.findAll();
    }

    public User save(User user) {
        boolean isUpdatingUser = (user.getId() != null);

        if (isUpdatingUser) {
            User existingUser = userRepo.findById(user.getId()).get();

            if (user.getPassword().isEmpty())
                user.setPassword(existingUser.getPassword());
            else
                encodePassword(user);
        } else
            encodePassword(user);

        return userRepo.save(user);
    }

    private void encodePassword(User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
    }

    public boolean isEmailUnique(Integer id, String email) {
        User userByEmail = userRepo.getUserByEmail(email);

        if (userByEmail == null) return true;

        boolean isCreatingNew = (id == null);

        if (isCreatingNew) {
            return false;
        } else {
            return userByEmail.getId().equals(id);
        }
    }

    public User get(Integer id) throws UserNotFoundException {
        try {
            return userRepo.findById(id).get();
        } catch (NoSuchElementException ex) {
            throw new UserNotFoundException("Could not find any user with ID " + id);
        }
    }

    public void delete(Integer id) throws UserNotFoundException {
        Long countById = userRepo.countById(id);

        if (countById == null || countById == 0)
            throw new UserNotFoundException("Could not find any user with ID :" + id);

        userRepo.deleteById(id);
    }

    public void updateUserEnableStatus(Integer id, boolean enabled) {
        userRepo.updateEnabledStatus(id, enabled);
    }

    public User getByEmail(String email) {
        return userRepo.getUserByEmail(email);
    }

    public User updateAccount(User userInform) {
        User userInDB = userRepo.findById(userInform.getId()).get();

        if (!userInform.getPassword().isEmpty()) {
            userInDB.setPassword(userInform.getPassword());
            encodePassword(userInDB);
        }

        if (userInform.getPhotos() != null) {
            userInDB.setPhotos(userInform.getPhotos());
        }
        userInDB.setFirstName(userInform.getFirstName());
        userInDB.setLastName(userInform.getLastName());

        return userRepo.save(userInDB);

    }
}
