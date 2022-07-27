package com.example.demospringsecurity.controller;

import com.example.demospringsecurity.model.User;
import com.example.demospringsecurity.model.dto.UpdateRequestDto;
import com.example.demospringsecurity.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok().body(userService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getById(@PathVariable long id) {
        return ResponseEntity.ok().body(userService.get(id));
    }

    @PreAuthorize("hasAuthority('USER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.ok("User with id: " + id + " was successfully deleted");
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> update(@RequestBody UpdateRequestDto requestDto,
                                       @PathVariable Long id) {
        User user = userService.update(requestDto, id);
        return ResponseEntity.ok().body(user);
    }
}

