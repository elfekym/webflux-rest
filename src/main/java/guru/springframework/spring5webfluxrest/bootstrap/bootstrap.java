package guru.springframework.spring5webfluxrest.bootstrap;


import guru.springframework.spring5webfluxrest.domain.Category;
import guru.springframework.spring5webfluxrest.domain.Vendor;
import guru.springframework.spring5webfluxrest.repositories.CategoryRepository;
import guru.springframework.spring5webfluxrest.repositories.VendorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class bootstrap implements CommandLineRunner {

    private final CategoryRepository categoryRepository;
    private final VendorRepository vendorCategory;

    public bootstrap(CategoryRepository categoryRepository, VendorRepository vendorCategory) {
        this.categoryRepository = categoryRepository;
        this.vendorCategory = vendorCategory;
    }

    @Override
    public void run(String... args) throws Exception {


        if(categoryRepository.count().block() == 0){
            System.out.println("Loading Data on BOOTSTRAP ");
            categoryRepository.save(Category
                    .builder()
                    .description("Fruits")
                    .build())
                    .block();

            categoryRepository.save(Category
                    .builder()
                    .description("Nuts")
                    .build())
                    .block();

            categoryRepository.save(Category
                    .builder()
                    .description("Breads")
                    .build())
                    .block();

            categoryRepository.save(Category
                    .builder()
                    .description("Eggs")
                    .build())
                    .block();
            System.out.println("Loaded Categories: "+ categoryRepository.count().block());
        }
        if(vendorCategory.count().block() == 0){
            vendorCategory.save( Vendor
                .builder()
                .firstName("Joe")
                .lastName("Buck")
                .build())
                    .block();

            vendorCategory.save( Vendor
                    .builder()
                    .firstName("Milla")
                    .lastName("Max")
                    .build())
                    .block();

            vendorCategory.save( Vendor
                    .builder()
                    .firstName("Maya")
                    .lastName("Mo")
                    .build())
                    .block();

            vendorCategory.save( Vendor
                    .builder()
                    .firstName("John")
                    .lastName("Doe")
                    .build())
                    .block();

            vendorCategory.save( Vendor
                    .builder()
                    .firstName("Gigi")
                    .lastName("Hud")
                    .build())
                    .block();

            System.out.println("Loaded Vendors: "+ vendorCategory.count().block());
        }


    }
}
