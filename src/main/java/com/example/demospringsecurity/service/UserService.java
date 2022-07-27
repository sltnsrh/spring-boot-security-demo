package com.example.demospringsecurity.service;

import com.example.demospringsecurity.model.Role;
import com.example.demospringsecurity.model.User;
import com.example.demospringsecurity.model.dto.RegistrationRequestDto;
import com.example.demospringsecurity.model.dto.UpdateRequestDto;
import java.util.List;

public interface UserService {
    Role saveRole(Role role);
    void addRoleToUser(String userName, Role.RoleName roleName);
    User getUserByUserName(String userName);
    User save(User user);
    User register(RegistrationRequestDto requestDto);
    User get(Long id);
    List<User> getAll();
    User update(UpdateRequestDto dto, Long id);
    void delete(Long id);
}
