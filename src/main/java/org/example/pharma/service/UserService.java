// Interface
package org.example.pharma.service;

import org.example.pharma.model.User;
import java.util.List;

public interface UserService {
    List<User> getAllUsers();
    User createUser(User user);
    User updateUser(long id, User user);
    void deleteUser(long id);
    User getUserById(long id);
}