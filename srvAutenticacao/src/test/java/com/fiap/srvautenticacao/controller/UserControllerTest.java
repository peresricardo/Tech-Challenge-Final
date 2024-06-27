package com.fiap.srvautenticacao.controller;

import com.callibrity.logging.test.LogTracker;
import com.callibrity.logging.test.LogTrackerStub;
import com.fiap.srvautenticacao.entity.User;
import com.fiap.srvautenticacao.entity.enums.UserRole;
import com.fiap.srvautenticacao.repository.UserRepository;
import com.fiap.srvautenticacao.security.TokenService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserControllerTest {
    @RegisterExtension
    LogTrackerStub logTracker = LogTrackerStub.create().recordForLevel(LogTracker.LogLevel.INFO)
            .recordForType(UserController.class);

    AutoCloseable openMocks;

    @Mock
    private UserRepository userRepository;
    @Mock
    private TokenService tokenService;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    UserController userController;

    @BeforeEach
    public void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
        userController = new UserController();
    }

    @AfterEach
    public void tearDown() throws Exception {
        openMocks.close();
    }

    @Nested
    class Usuarios {
        @Test
        public void buscarUsuarios() {
            ReflectionTestUtils.setField(userController, "repository", userRepository);

            List<User> mockUsers = List.of(new User("user1", "password1", UserRole.USER), new User("user2", "password2", UserRole.ADMIN));
            Mockito.when(userRepository.findAll()).thenReturn(mockUsers);

            ResponseEntity response = userController.getAllUsers();

            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(mockUsers, response.getBody());
        }
    }
}
