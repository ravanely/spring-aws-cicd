package cm.nsi.saarngan.category;

import cm.nsi.saarngan.entity.Category;
import cm.nsi.saarngan.repository.CategoryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * created by : Ravanely
 * create at : 05/04/2022, 12:34, mar.
 * saar-ngan
 **/
@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class CategoryRepositoryTests {
    @Autowired
    private CategoryRepository repo;

    @Test
    public void testCreateRootCategory() {
        Category category = new Category("Computers");
        category.setAlias("computers");
        Category savedCategory = repo.save(category);

        assertThat(savedCategory.getId()).isGreaterThan(0);
    }

/*    @Test
    public void testCreateSubCategory() {
        Category parent = new Category(8);
        Category subCategory = new Category("IPhone", parent);
        Category savedCategory = repo.save(subCategory);

        assertThat(savedCategory.getId()).isGreaterThan(0);
    }*/

/*    @Test
    public void testGetCategory() {
        Category category = repo.findById(2).get();
        System.out.println(category.getName());

        Set<Category> children = category.getChildren();

        children.forEach(child -> System.out.println(child.getName()));

        assertThat(children.size()).isGreaterThan(0);
    }*/

/*    @Test
    public void testPrintHierachicalCategories() {
        Iterable<Category> categories = repo.findAll();

        categories.forEach(category -> {
            if (category.getParent() == null) {
                System.out.println(category.getName());

                Set<Category> children = category.getChildren();

                children.forEach(subCategory -> {
                    System.out.println("--" + subCategory.getName());
                    printChildren(subCategory, 1);
                });
            }
        });
    }*/

/*    private void printChildren(Category parent, int subLevel) {
        int newSubLevel = subLevel + 1;
        Set<Category> children = parent.getChildren();

        for (Category subCategory : children) {
            for (int i = 0; i < newSubLevel; i++) {
                System.out.print("--");
            }

            System.out.println(subCategory.getName());

            printChildren(subCategory, newSubLevel);
        }
    }*/

    @Test
    @DisplayName("List roots categories")
    public void testListRootCategories() {
        List<Category> rootCategories = repo.findRootCategories();
        rootCategories.forEach(category -> System.out.println(category.getName()));
    }

/*    @Test
    @DisplayName("Find category by Name")
    public void testFindByName() {
        Category category = repo.findByName("Computers");
        assertThat(category).isNotNull();
        assertThat("Computers").isEqualTo(category.getName());
    }

    @Test
    @DisplayName("Find category by unique alias")
    public void testFindByAlias() {
        Category category = repo.findByAlias("computers");
        assertThat(category).isNotNull();
        assertThat("computers").isEqualTo(category.getAlias());
    }*/
}
