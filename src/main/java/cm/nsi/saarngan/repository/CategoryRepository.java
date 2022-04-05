package cm.nsi.saarngan.repository;

import cm.nsi.saarngan.entity.Category;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * created by : Ravanely
 * create at : 05/04/2022, 11:48, mar.
 * saar-ngan
 **/
@Repository
public interface CategoryRepository extends PagingAndSortingRepository<Category, Integer> {

    @Query("select c from Category c where c.parent is null ")
    List<Category> findRootCategories();

    @Query("select c from Category c where c.alias = :alias")
    Category findByAlias(String alias);

    @Query("select c from Category c where c.name = :name")
    Category findByName(String name);

    @Query("update Category c set c.enabled = ?2 where c.id = ?1")
    @Modifying
    void updateEnableStatus(Integer id, boolean enabled);
}
