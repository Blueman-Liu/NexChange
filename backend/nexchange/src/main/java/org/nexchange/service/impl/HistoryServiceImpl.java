package org.nexchange.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.nexchange.entity.History;
import org.nexchange.mapper.HistoryMapper;
import org.nexchange.service.HistoryService;
import org.springframework.stereotype.Service;

@Service
public class HistoryServiceImpl extends ServiceImpl<HistoryMapper, History> implements HistoryService {
}
