package com.fiap.srvautenticacao.controller;

import com.callibrity.logging.test.LogTracker;
import com.callibrity.logging.test.LogTrackerStub;
import com.fiap.srvautenticacao.entity.User;
import com.fiap.srvautenticacao.entity.enums.UserRole;
import com.fiap.srvautenticacao.repository.UserRepository;
import com.fiap.srvautenticacao.request.UserAuthRequest;
import com.fiap.srvautenticacao.request.UserRequest;
import com.fiap.srvautenticacao.response.UserResponse;
import com.fiap.srvautenticacao.security.TokenService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AuthenticationControllerTest {
    @RegisterExtension
    LogTrackerStub logTracker = LogTrackerStub.create().recordForLevel(LogTracker.LogLevel.INFO)
            .recordForType(AuthenticationController.class);

    AutoCloseable openMocks;

    @Mock
    private UserRepository userRepository;
    @Mock
    private TokenService tokenService;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    AuthenticationController controller;

    @BeforeEach
    public void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
        controller = new AuthenticationController();
    }

    @AfterEach
    public void tearDown() throws Exception {
        openMocks.close();
    }

    @Nested
    class Login {
        @Test
        public void testLogin_Valido() {
            ReflectionTestUtils.setField(controller, "authenticationManager", authenticationManager);
            ReflectionTestUtils.setField(controller, "repository", userRepository);
            ReflectionTestUtils.setField(controller, "tokenService", tokenService);

            UserAuthRequest request = new UserAuthRequest("validUser", "validPassword");
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken("validUser", "validPassword");
            Authentication auth = mock(Authentication.class);
            User user = new User("validUser", "validPassword", UserRole.USER);

            when(authenticationManager.authenticate(authToken)).thenReturn(auth);
            when(auth.getPrincipal()).thenReturn(user);
            when(tokenService.generateToken(user)).thenReturn("validToken");

            // Act
            ResponseEntity response = controller.login(request);

            // Assert
            assertEquals(200, response.getStatusCodeValue());
            assertEquals("validToken", ((UserResponse) response.getBody()).token());
        }

        @Test
        public void testLogin_Invalido() {
            ReflectionTestUtils.setField(controller, "authenticationManager", authenticationManager);
            ReflectionTestUtils.setField(controller, "repository", userRepository);
            ReflectionTestUtils.setField(controller, "tokenService", tokenService);

            UserAuthRequest request = new UserAuthRequest("invalidUser", "invalidPassword");
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken("invalidUser", "invalidPassword");

            when(authenticationManager.authenticate(authToken)).thenThrow(new RuntimeException("Invalid credentials"));

            // Act
            ResponseEntity response;
            try {
                response = controller.login(request);
                fail("Expected an exception to be thrown");
            } catch (Exception e) {
                // Assert
                assertTrue(e instanceof RuntimeException);
                assertEquals("Invalid credentials", e.getMessage());
            }
        }
    }

    @Nested
    class RegistraUsuario {
        @Test
        public void registraUsuario_Valido() {
            ReflectionTestUtils.setField(controller, "repository", userRepository);
            ReflectionTestUtils.setField(controller, "tokenService", tokenService);
            ReflectionTestUtils.setField(controller, "authenticationManager", authenticationManager);

            UserRequest request = new UserRequest("newuser", "password123", UserRole.USER);
            when(userRepository.findByLogin("newuser")).thenReturn(null);

            ResponseEntity response = controller.register(request);

            assertEquals(HttpStatus.OK, response.getStatusCode());
            verify(userRepository, times(1)).save(any(User.class));
        }
    }

    @Nested
    class ValidaToken {
        @Test
        public void tokenValido() {
            ReflectionTestUtils.setField(controller, "tokenService", tokenService);

            String validToken = "validToken";
            when(tokenService.validateToken(validToken)).thenReturn("user");

            String response = controller.validateToken(validToken);

            assertEquals("Token is valid", response);
        }

        @Test
        public void tokenInvalido() {
            ReflectionTestUtils.setField(controller, "tokenService", tokenService);

            String invalidToken = "Token is not valid";
            when(tokenService.validateToken(invalidToken)).thenReturn("");

            String response = controller.validateToken(invalidToken);

            assertEquals("Token is not valid", response);
        }
    }
}
