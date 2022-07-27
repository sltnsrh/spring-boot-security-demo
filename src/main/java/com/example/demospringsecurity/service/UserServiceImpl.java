package com.example.demospringsecurity.service;

import com.example.demospringsecurity.model.Role;
import com.example.demospringsecurity.model.User;
import com.example.demospringsecurity.model.dto.RegistrationRequestDto;
import com.example.demospringsecurity.model.dto.UpdateRequestDto;
import com.example.demospringsecurity.repository.RoleRepository;
import com.example.demospringsecurity.repository.UserRepository;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public User register(RegistrationRequestDto requestDto) {
        String userName = requestDto.getUserName();
        String password = requestDto.getPassword();
        User user = new User();
        user.setUserName(userName);
        user.setPassword(passwordEncoder.encode(password));
        Role role = roleRepository.findByRoleName(Role.RoleName.valueOf(requestDto.getRole()));
        user.setRoles(Set.of(role));
        return userRepository.save(user);
    }

    @Override
    public User get(Long id) {
        return userRepository.getById(id);
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public User update(UpdateRequestDto dto, Long id) {
        User user = get(id);
        user.setUserName(dto.getUserName());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.getRoles().clear();
        user.getRoles().add(roleRepository.findByRoleName(Role.RoleName.valueOf(dto.getRole())));
        return userRepository.save(user);
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public Role saveRole(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public void addRoleToUser(String userName, Role.RoleName roleName) {
        User user = userRepository.findByUserName(userName);
        Role role = roleRepository.findByRoleName(roleName);
        user.getRoles().add(role);
    }

    @Override
    public User getUserByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }
}
