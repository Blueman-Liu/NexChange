package org.nexchange.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.nexchange.entity.Collection;
import org.nexchange.entity.Product;
import org.springframework.stereotype.Service;

@Service
public interface CollectionService extends IService<Collection> {
    void add(long productID, long userID);
    void delete(long productID, long userID);

    Collection get(long productID, long userID);
}
