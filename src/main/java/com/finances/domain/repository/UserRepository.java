package com.finances.domain.repository;

import com.finances.domain.dto.user.UserDto;
import com.finances.domain.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.print.Pageable;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

}
