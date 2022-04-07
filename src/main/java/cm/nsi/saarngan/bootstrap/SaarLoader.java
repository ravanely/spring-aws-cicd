package cm.nsi.saarngan.bootstrap;

import cm.nsi.saarngan.entity.Role;
import cm.nsi.saarngan.entity.User;
import cm.nsi.saarngan.repository.RoleRepository;
import cm.nsi.saarngan.repository.UserRepository;
import cm.nsi.saarngan.services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @author : macbookpro
 * @mailto : nagueravanely@gmail.com
 * @created : 06/04/2022, 20:29 mercredi
 **/
@Component
public class SaarLoader implements CommandLineRunner {

    private final UserRepository userRepo;
    private final RoleRepository roleRepo;
    private final UserService userService;

    public SaarLoader(UserRepository userRepo, RoleRepository roleRepo, UserService userService) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.userService = userService;
    }

    @Override
    public void run(String... args) throws Exception {
        if (userRepo.count() == 0L) {

            Role adminRole = roleRepo.save(new Role("Admin", "Manage Everything"));
            User adminUser = new User("nam@codejava.net", "nam2020", "Nam", "Ha Minh");
            adminUser.addRole(adminRole);
            adminUser.setEnabled(true);
            userService.save(adminUser);

            Role salesPersonRole = roleRepo.save(new Role("SalesPerson", "Manage product price, customers, shipping, orders and sales report"));
            User salesPersonUser = new User("toto@toto.cm", "toto2020", "Toto", "toto");
            salesPersonUser.addRole(salesPersonRole);
            salesPersonUser.setEnabled(true);
            userService.save(salesPersonUser);

            Role editorRole = roleRepo.save(new Role("Editor", "Manage categories, brands, products, articles and menus"));
            User editorUser = new User("tata@tata.cm", "tata2020", "Tata", "tata");
            editorUser.addRole(editorRole);
            editorUser.setEnabled(true);
            userService.save(editorUser);

            Role shipperRole = roleRepo.save(new Role("Shipper", "View products, view orders and update order status"));
            User shipperUser = new User("lola@lola.cm", "lola2020", "Lola", "lola");
            shipperUser.addRole(shipperRole);
            shipperUser.setEnabled(true);
            userService.save(shipperUser);

            Role assistantRole = roleRepo.save(new Role("Assistant", "Manage questions and reviews"));
            User assistantUser = new User("zozo@zozo.cm", "zozo2020", "Zozo", "zozo");
            assistantUser.addRole(assistantRole);
            assistantUser.setEnabled(true);
            userService.save(assistantUser);
        }
    }
}