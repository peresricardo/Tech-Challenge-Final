package com.fiap.srvautenticacao.request;

import com.fiap.srvautenticacao.entity.enums.UserRole;

public record UserRequest(String login, String password, UserRole role) {
}
