package cm.nsi.saarngan;

import cm.nsi.saarngan.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * created by : Ravanely
 * create at : 05/04/2022, 11:23, mar.
 * saar-ngan
 **/
@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Integer> {
    @Query("SELECT u FROM User u where u.email= :email")
    User getUserByEmail(@Param("email") String email);

    Long countById(Integer id);

    @Query("UPDATE User u set u.enabled = ?2 WHERE u.id = ?1")
    @Modifying
    void updateEnabledStatus(Integer id, boolean enabled);

    @Query("select u from User u where concat(u.id, ' ', u.email, ' ', u.firstName,' ', u.lastName, ' ') like %?1%")
    Page<User> findAll(String keyword, Pageable pageable);
}
