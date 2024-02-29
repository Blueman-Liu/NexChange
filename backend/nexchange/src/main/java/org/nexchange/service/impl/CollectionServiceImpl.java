package org.nexchange.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.nexchange.entity.Collection;
import org.nexchange.mapper.CollectionMapper;
import org.nexchange.service.CollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CollectionServiceImpl extends ServiceImpl<CollectionMapper, Collection> implements CollectionService {
    @Autowired
    private CollectionMapper collectionMapper;
    @Override
    public void add(long productID, long userID) {
        Collection collection = new Collection();
        collection.setUserID(userID);
        collection.setProductID(productID);
        collectionMapper.insert(collection);
    }

    @Override
    public void delete(long productID, long userID) {
        LambdaQueryWrapper<Collection> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper
                .eq(Collection::getUserID, userID)
                .eq(Collection::getProductID, productID);
        collectionMapper.delete(lambdaQueryWrapper);
    }

    @Override
    public Collection get(long productID, long userID) {
        LambdaQueryWrapper<Collection> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Collection::getProductID, productID)
                .eq(Collection::getUserID, userID);
        return getOne(lambdaQueryWrapper);
    }
}
