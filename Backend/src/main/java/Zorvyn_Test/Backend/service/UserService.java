package Zorvyn_Test.Backend.service;

import Zorvyn_Test.Backend.model.User;
import Zorvyn_Test.Backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User toggleUserStatus(UUID id, boolean status) {
        User existing = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + id));

        existing.setActive(status);
        return userRepository.save(existing);
    }
}
