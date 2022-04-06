package cm.nsi.saarngan.bootstrap;

import cm.nsi.saarngan.entity.Role;
import cm.nsi.saarngan.entity.User;
import cm.nsi.saarngan.repository.RoleRepository;
import cm.nsi.saarngan.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

/**
 * @author : macbookpro
 * @mailto : nagueravanely@gmail.com
 * @created : 06/04/2022, 20:29 mercredi
 **/
public class SaarLoader implements CommandLineRunner {

    @Autowired
    private UserRepository userRepo;
    @Autowired
    private RoleRepository roleRepo;

    @Override
    public void run(String... args) throws Exception {
        if (userRepo.count() == 0L) {

            Role adminRole = roleRepo.save(new Role("Admin", "Manage Everything"));
            User adminUser = new User("nam@codejava.net", "nam2020", "Nam", "Ha Minh");
            adminUser.addRole(adminRole);
            userRepo.save(adminUser);

            Role salesPersonRole = roleRepo.save(new Role("SalesPerson", "Manage product price, customers, shipping, orders and sales report"));
            User salesPersonUser = new User("toto@toto.cm", "toto2020", "Toto", "toto");
            salesPersonUser.addRole(salesPersonRole);
            userRepo.save(salesPersonUser);

            Role EditorRole = roleRepo.save(new Role("Editor", "Manage categories, brands, products, articles and menus"));
            User EditorUser = new User("tata@tata.cm", "tata2020", "Tata", "tata");
            salesPersonUser.addRole(EditorRole);
            userRepo.save(EditorUser);

            Role shipperRole = roleRepo.save(new Role("Shipper", "View products, view orders and update order status"));
            User shipperUser = new User("tata@tata.cm", "tata2020", "Tata", "tata");
            salesPersonUser.addRole(shipperRole);
            userRepo.save(shipperUser);

            Role assistantRole = roleRepo.save(new Role("Assistant", "Manage questions and reviews"));
            User assistantUser = new User("zozo@zozo.cm", "zozo2020", "Zozo", "zozo");
            salesPersonUser.addRole(assistantRole);
            userRepo.save(assistantUser);
        }
    }
}