package de.neuefische.securitydemo;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserDocument createUser(UserDocument user) {
        return userRepository.save(user);
    }

    public Optional<UserDocument> findByEmail(String username) {
        return userRepository.findByEmail(username);
    }

}
