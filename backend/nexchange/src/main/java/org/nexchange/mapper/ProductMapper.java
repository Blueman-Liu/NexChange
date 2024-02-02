package org.nexchange.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.nexchange.entity.Product;

@Mapper
public interface ProductMapper extends BaseMapper<Product> {
}
