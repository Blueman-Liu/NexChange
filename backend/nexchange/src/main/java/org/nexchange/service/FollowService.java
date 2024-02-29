package org.nexchange.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.nexchange.entity.Follow;
import org.springframework.stereotype.Service;

@Service
public interface FollowService extends IService<Follow> {
    public void follow(Long targetID, Long userID);
    public void unfollow(Long targetID, Long userID);
}
