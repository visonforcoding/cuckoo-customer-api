package com.vison.cuckoocustomerapi.repository;

import java.util.Optional;

import com.vison.cuckoocustomerapi.entity.User;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author vison.cao <visonforcoding@gmail.com>
 */
public interface UserRepository extends CrudRepository<User, Long> {

    User findByUsername(String username);

    Optional<User> findByToken(String token);
}
