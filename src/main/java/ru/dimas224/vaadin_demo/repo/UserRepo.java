package ru.dimas224.vaadin_demo.repo;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.dimas224.vaadin_demo.domain.UserEntity;

public interface UserRepo extends JpaRepository<UserEntity, Long> {
    @Query("from UserEntity u " +
            "where concat(u.lastName, ' ', u.firstName, ' ', u.patronymic) " +
            "like concat('%', :name, '%') ")
    List<UserEntity> findByName(@Param("name") String name);
}
