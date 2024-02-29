package org.nexchange.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.nexchange.entity.Category;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService extends IService<Category> {
    List<Category> getCategories();
}
