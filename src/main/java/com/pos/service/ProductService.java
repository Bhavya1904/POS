package com.pos.service;


import com.pos.model.User;
import com.pos.payload.dto.ProductDTO;

import java.util.List;

public interface ProductService {

    ProductDTO createProduct(ProductDTO productDTO, User user) throws Exception;
    ProductDTO updateProduct(Long id, ProductDTO productDTO, User user) throws Exception;
    void deleteProduct(Long id, User user) throws Exception;
    List<ProductDTO> getProductsByStoreId(Long storeId);
    List<ProductDTO> searchByKeyword(Long storeId, String keyword);

}
