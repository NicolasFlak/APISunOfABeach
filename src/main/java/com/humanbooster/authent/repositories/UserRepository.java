package com.humanbooster.authent.repositories;

import com.humanbooster.authent.models.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    @Override
    public List<User> findAll();

    public User findByEmail(String email);

//    public User findByUsername(String username);

    @Query("FROM User u JOIN u.roleList as roleList WHERE u.email = :username AND roleList.roleName = 'ADMIN'")
    public User findByEmailAndAdminRole(String username);

    @Query(value = "FROM User WHERE idUser != :id AND email = :email")
    public List<User> findByEmailWithoutId(String email, Long id);

}
