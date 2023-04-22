package exmp.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import exmp.models.Product;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public class InMemoryProductRepository implements exmp.repository.ProductRepository {
    private final List<Product> products = new ArrayList<>();
    private final AtomicLong idGenerator = new AtomicLong(1);
    private static final Logger logger = LogManager.getLogger(InMemoryProductRepository.class);

    @Override
    public List<Product> findAll() {
        return new ArrayList<>(products);
    }

    @Override
    public Optional<Product> findById(long id) {
        return products.stream()
                .filter(product -> product.getId() == id)
                .findFirst();
    }

    @Override
    public Product save(Product product) {
        if (product.getId() == null) {
            product.setId(idGenerator.getAndIncrement());
            products.add(product);
        } else {
            findById(product.getId()).ifPresent(existingProduct -> {
                products.remove(existingProduct);
                products.add(product);
            });
        }
        return product;
    }

    @Override
    public void delete(Product product) {
        products.remove(product);
    }

    @Override
    public void deleteById(long id) {
        findById(id).ifPresent(products::remove);
    }

    /**
     * Загружает данные из файла и инициализирует коллекцию {@code products}.
     * Если загрузка данных прошла успешно, выводит сообщение об успешной загрузке.
     * Если возникла ошибка при загрузке данных, выводит сообщение об ошибке.
     */
    @Override
    public void loadData(String fileName) {
        ObjectMapper xmlMapper = new XmlMapper();
        File file = new File(fileName);
        try {
            List<Product> file_products = xmlMapper.readValue(file, xmlMapper.getTypeFactory().constructCollectionType(List.class, Product.class));

            products.addAll(file_products);
            logger.info("Данные успешно загружены из файла: {}", fileName);
        } catch (IOException e) {
            logger.error("Ошибка при загрузке данных из файла: {}", fileName);
        }
    }
}
