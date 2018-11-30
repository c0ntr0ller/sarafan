package ru.progmatik.sarafan.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.progmatik.sarafan.domain.User;

public interface UserRepository extends JpaRepository<User, String> {
}
