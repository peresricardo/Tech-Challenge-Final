package com.fiap.srvautenticacao;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

public class MainAppTest {
    @Test
    public void main(){
        try (MockedStatic<SpringApplication> mocked = Mockito.mockStatic(SpringApplication.class)) {
            mocked.when(() -> SpringApplication.run(SrvAutenticacaoApplication.class, new String[]{}))
                    .thenReturn(Mockito.mock(ConfigurableApplicationContext.class));

            SrvAutenticacaoApplication.main(new String[]{});

            mocked.verify(() -> SpringApplication.run(SrvAutenticacaoApplication.class, new String[]{}));
        }
    }
}