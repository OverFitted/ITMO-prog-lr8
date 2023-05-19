package exmp.repository;

import exmp.models.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    List<Product> findAll();
    Optional<Product> findById(long id);
    Product save(Product product);

    List<Product> findByCountry(exmp.enums.Country country);

    void delete(Product product);
    void deleteById(long id);
    public void loadData(exmp.App app);
}
