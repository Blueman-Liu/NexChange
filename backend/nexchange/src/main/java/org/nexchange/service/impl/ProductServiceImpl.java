package org.nexchange.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.nexchange.entity.Collection;
import org.nexchange.entity.Product;
import org.nexchange.entity.User;
import org.nexchange.mapper.ProductMapper;
import org.nexchange.service.CategoryService;
import org.nexchange.service.CollectionService;
import org.nexchange.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {
    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private CollectionService collectionService;
    @Autowired
    private CategoryService categoryService;

    @Override
    public void addProduct(Product product) {
        productMapper.insert(product);
    }

    @Override
    public void setProduct(Product product) {
        productMapper.updateById(product);
    }

    @Override
    public List<Product> getProductsBySellerID(User user) {
        LambdaQueryWrapper<Product> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Product::getSellerID, user.getUserID());
        List<Product> list = productMapper.selectList(lambdaQueryWrapper);
        return list;
    }

    @Override
    public void collect(long productID, long userID) {
        collectionService.add(productID, userID);
    }

    @Override
    public void uncollect(long productID, long userID) {
        collectionService.delete(productID, userID);
    }

    @Override
    public Page<Product> getProdPage(int pageNum, int pageSize, long categoryID, String searchText, long sellerID) {
        LambdaQueryWrapper<Product> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (!searchText.isEmpty()) {
            lambdaQueryWrapper
                    .like(Product::getProductName, searchText);
        }
        if (categoryID!=0) {
            lambdaQueryWrapper
                    .eq(Product::getCategoryID, categoryID);
        }
        if (sellerID!=0) {
            lambdaQueryWrapper
                    .eq(Product::getSellerID, sellerID);
        }
        lambdaQueryWrapper.orderByDesc(Product::getPublicationDate);

        return productMapper.selectPage(new Page<>(pageNum, pageSize), lambdaQueryWrapper);
    }

}
