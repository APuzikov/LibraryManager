package ru.mera.lib.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mera.lib.entity.UserRoles;

import java.util.List;

public interface UserRolesRepository extends JpaRepository<UserRoles, Integer>{
    List<UserRoles> findByUserId(Integer userId);
}
