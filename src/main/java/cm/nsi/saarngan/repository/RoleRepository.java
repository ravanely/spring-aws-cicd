package cm.nsi.saarngan.repository;


import cm.nsi.saarngan.entity.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * created by : Ravanely
 * create at : 05/04/2022, 11:47, mar.
 * saar-ngan
 **/
@Repository
public interface RoleRepository extends CrudRepository<Role, Integer> {
}
