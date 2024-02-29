package org.nexchange.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.nexchange.entity.Collection;
import org.nexchange.entity.Product;
import org.nexchange.entity.User;
import org.nexchange.mapper.ProductMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService extends IService<Product> {
    public void addProduct(Product product);
    public void setProduct(Product product);
    public List<Product> getProductsBySellerID(User user);
    void collect(long productID, long userID);
    void uncollect(long productID, long userID);
}
