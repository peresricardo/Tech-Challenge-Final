package com.fiap.srvautenticacao.service;

import com.fiap.srvautenticacao.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class AuthorizationServiceTest {
    private AuthorizationService service;
    @Mock
    private UserRepository userRepository;

    AutoCloseable openMocks;

    @BeforeEach
    void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
        service = new AuthorizationService();
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Nested
    class Authenticate {
        @Test
        public void carregaUsuarioValido() throws Exception {
            ReflectionTestUtils.setField(service, "repository", userRepository);

            String username = "validUser";
            UserDetails mockUserDetails = mock(UserDetails.class);

            when(userRepository.findByLogin(username)).thenReturn(mockUserDetails);

            UserDetails userDetails = service.loadUserByUsername(username);

            assertNotNull(userDetails);
            assertEquals(mockUserDetails, userDetails);
            verify(userRepository, times(1)).findByLogin(username);
        }
    }
}
