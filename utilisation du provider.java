import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ApplicationRunner implements CommandLineRunner {

    private final RepositoryProvider repositoryProvider;

    @Autowired
    public ApplicationRunner(RepositoryProvider repositoryProvider) {
        this.repositoryProvider = repositoryProvider;
    }

    @Override
    public void run(String... args) {
        // Get the UserRepository dynamically
        JpaRepository<User, Long> userRepository = repositoryProvider.getRepository(User.class);
        User user = new User();
        user.setName("John Doe");
        userRepository.save(user);

        // Get the ProductRepository dynamically
        ProductRepository productRepository = (ProductRepository) repositoryProvider.getRepository(Product.class);
        Product product = new Product();
        product.setName("Laptop");
        product.setCategory("Electronics");
        productRepository.save(product);

        // Use the custom method
        List<Product> electronics = productRepository.findByCategory("Electronics");
        for (Product p : electronics) {
            System.out.println(p.getName());
        }
    }
}