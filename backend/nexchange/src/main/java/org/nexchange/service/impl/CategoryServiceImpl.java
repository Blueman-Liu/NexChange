package org.nexchange.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.nexchange.entity.Category;
import org.nexchange.mapper.CategoryMapper;
import org.nexchange.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;
    @Override
    public List<Category> getCategories() {
        return categoryMapper.selectList(new LambdaQueryWrapper<>());
    }
}
