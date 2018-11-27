package ru.progmatik.sarafan.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.progmatik.sarafan.domain.Message;

public interface MessageRepository extends JpaRepository<Message, Long>{
}
