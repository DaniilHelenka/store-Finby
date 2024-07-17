package com.gmail.daniilhelenka98.store.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import com.gmail.daniilhelenka98.store.model.Product;
import com.gmail.daniilhelenka98.store.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
public class ProductService {

    private static final String SHORT_DESCRIPTION_PATTERN = "Прекрасные %s %s, цвет: %s. %s";

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ImageProcessingService imageProcessingService;

    @Transactional
    public Product createOrUpdate(Product product) {
        log.info("Создание продукта с именем {}.", product.getName());
        product.setShortDescription(buildShortDescription(product));
        return productRepository.save(product);
    }

    @Transactional
    public Product updateImage(Long id, MultipartFile image) {
        try {
            log.info("Обновление изображения для продукта с id {}.", id);
            Product product = getById(id);
            byte[] processedImage = imageProcessingService.removeBackground(image.getBytes());
            Path imagePath = Paths.get("processed-images/" + image.getOriginalFilename());
            Files.write(imagePath, processedImage);
            product.setImageUrl(imagePath.toString());
            return createOrUpdate(product);
        } catch (Exception e) {
            String message = String.format("Ошибка при обновлении изображения для продукта с id %s : %s", id, e.getMessage());
            throw new RuntimeException(message, e);
        }
    }

    @Transactional(readOnly = true)
    public List<Product> getAll() {
        log.info("Получение всех продуктов.");
        return productRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Product getById(Long id) {
        log.info("Получение продукта с id {}.", id);
        return productRepository.findById(id).orElseThrow(() -> new RuntimeException(String.format("Продукт с id %s не найден.", id)));
    }

    @Transactional
    public void delete(Long id) {
        log.info("Удаление продукта с id {}.", id);
        productRepository.deleteById(id);
    }

    private String buildShortDescription(Product product) {
        return String.format(SHORT_DESCRIPTION_PATTERN,
                product.getCategory(), product.getBrand(), product.getColor(),
                String.join(", ", product.getSpecialFeatures()));
    }

}