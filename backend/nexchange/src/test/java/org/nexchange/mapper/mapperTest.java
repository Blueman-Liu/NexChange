package org.nexchange.mapper;

import com.baomidou.mybatisplus.core.toolkit.Assert;
import org.junit.jupiter.api.Test;
import org.nexchange.entity.Product;
import org.nexchange.entity.Transaction;
import org.nexchange.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.swing.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicReferenceArray;

@SpringBootTest
public class mapperTest {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private TransactionMapper transactionMapper;

    @Test
    public void test(){
        System.out.println(("----- users ------"));
        List<User> userList = userMapper.selectList(null);
        userList.forEach(System.out::println);

        System.out.println(("----- products ------"));
        List<Product> productList = productMapper.selectList(null);
        productList.forEach(System.out::println);

        System.out.println(("----- transactions ------"));
        List<Transaction> transList = transactionMapper.selectList(null);
        transList.forEach(System.out::println);
    }
}
