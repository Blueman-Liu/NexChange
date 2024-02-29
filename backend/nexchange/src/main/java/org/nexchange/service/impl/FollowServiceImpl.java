package org.nexchange.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.nexchange.entity.Follow;
import org.nexchange.mapper.FollowMapper;
import org.nexchange.service.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FollowServiceImpl extends ServiceImpl<FollowMapper, Follow> implements FollowService {
    @Autowired
    private FollowMapper followMapper;

    @Override
    public void follow(Long targetID, Long userID) {
        Follow follow = new Follow();
        follow.setFollowerID(userID);
        follow.setFollowingID(targetID);
        followMapper.insert(follow);
    }

    @Override
    public void unfollow(Long targetID, Long userID) {
        LambdaQueryWrapper<Follow> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper
                .eq(Follow::getFollowerID, userID)
                .eq(Follow::getFollowingID, targetID);
        followMapper.delete(lambdaQueryWrapper);
    }


}
