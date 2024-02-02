package org.nexchange.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.nexchange.entity.Transaction;

@Mapper
public interface TransactionMapper extends BaseMapper<Transaction> {
}
