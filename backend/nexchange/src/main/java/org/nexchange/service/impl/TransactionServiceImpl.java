package org.nexchange.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.nexchange.entity.Transaction;
import org.nexchange.mapper.TransactionMapper;
import org.nexchange.service.TransactionService;
import org.springframework.stereotype.Service;

@Service
public class TransactionServiceImpl extends ServiceImpl<TransactionMapper, Transaction> implements TransactionService {
}
