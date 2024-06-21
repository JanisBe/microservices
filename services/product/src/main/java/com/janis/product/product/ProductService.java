package com.janis.product.product;

import com.janis.product.exception.ProductNotFoundException;
import com.janis.product.exception.ProductPurchaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public Integer createProduct(ProductRequest request) {
        var product = productMapper.toProduct(request);
        return productRepository.save(product).getId();
    }

    public List<ProductPurchaseResponse> purchaseProducts(List<ProductPurchaseRequest> request) {

        List<Integer> productIds = request.stream().map(ProductPurchaseRequest::productId).toList();
        var storedProducts = productRepository.findAllByIdInOrderById(productIds);
        if(storedProducts.size() != productIds.size()) {
            throw new ProductPurchaseException("One or more products not found");
        }
        var storedRequest = request.stream().sorted(Comparator.comparing(ProductPurchaseRequest::productId)).toList();
        var purchasedProducts = new ArrayList<ProductPurchaseResponse>();

        for(int i = 0; i < storedProducts.size(); i++) {
            var product = storedProducts.get(i);
            if(product.getAvailableQuantity() < storedRequest.get(i).quantity()) {
                throw new ProductPurchaseException("Not enough quantity");
            }
            product.setAvailableQuantity(product.getAvailableQuantity() - storedRequest.get(i).quantity());
            productRepository.save(product);
            purchasedProducts.add(productMapper.toProductPurchaseResponse(product, storedRequest.get(i).quantity()));
        }
        return purchasedProducts;
    }

    public ProductResponse getProduct(Integer id) {
        return productRepository.findById(id).map(productMapper::toProductResponse).orElseThrow(() -> new ProductNotFoundException("Product not found"));

    }

    public List<ProductResponse> getProducts() {
        return productRepository.findAll().stream().map(productMapper::toProductResponse).toList();
    }
}
