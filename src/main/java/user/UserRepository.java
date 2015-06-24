package user;

import org.springframework.data.repository.CrudRepository;

/**
 * Created by Ethan Zhang on 15/6/23.
 */
public interface UserRepository extends CrudRepository<User, Long> {
}
