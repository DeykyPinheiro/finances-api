package com.finances.domain.exception;

public class UserNotFoundException extends EntityNotFoundException {
    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException(Long userId) {
        this(String.format("Não existe um cadastro de usuario com código %d", userId));
    }
}
