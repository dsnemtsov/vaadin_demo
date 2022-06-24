package ru.dimas224.vaadin_demo.repo;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.dimas224.vaadin_demo.domain.User;


public interface UserRepo extends JpaRepository<User, Long> {
    @Query("from users u " +
            "where concat(u.lastName, ' ', u.firstName, ' ', u.patronymic) " +
            "like concat('%', :name, '%') ")
    List<User> findByName(@Param("name") String name);
}
