package com.pos.service.impl;

import com.pos.mapper.ProductMapper;
import com.pos.model.Product;
import com.pos.model.Store;
import com.pos.model.User;
import com.pos.payload.dto.ProductDTO;
import com.pos.repository.ProductRepository;
import com.pos.repository.StoreRepository;
import com.pos.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final StoreRepository storeRepository;

    @Override
    public ProductDTO createProduct(ProductDTO productDTO, User user) throws Exception {
        Store store = storeRepository.findById(productDTO.getStoreId()
            ).orElseThrow(
                () -> new Exception("Store not found"));

        Product product = ProductMapper.toEntity(productDTO, store);
        Product savedProduct = productRepository.save(product);
        return ProductMapper.toDTO(savedProduct);
    }

    @Override
    public ProductDTO updateProduct(Long id, ProductDTO productDTO, User user) throws Exception {
        Product product = productRepository.findById(id).orElseThrow(
                () -> new Exception("Product not found")
        );

        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setSku(productDTO.getSku());
        product.setImage(productDTO.getImage());
        product.setMrp(productDTO.getMrp());
        product.setSellingPrice(productDTO.getSellingPrice());
        product.setBrand(productDTO.getBrand());
        product.setUpdatedAt(LocalDateTime.now());

        Product savedProduct = productRepository.save(product);

        return ProductMapper.toDTO(savedProduct);
    }

    @Override
    public void deleteProduct(Long id, User user) throws Exception {
        Product product = productRepository.findById(id).orElseThrow(
                () -> new Exception("Product not found")
        );
        productRepository.delete(product);

    }

    @Override
    public List<ProductDTO> getProductsByStoreId(Long storeId) {
        List<Product> products = productRepository.findByStoreId(storeId);
        return products
                .stream()
                .map(ProductMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDTO> searchByKeyword(Long storeId, String keyword) {
        List<Product> products = productRepository.searchByKeyword(storeId, keyword);
        return products
                .stream()
                .map(ProductMapper::toDTO)
                .collect(Collectors.toList());    }
}
