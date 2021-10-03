package ru.otus.lab2.app.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import ru.otus.lab2.app.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
