package com.github.niefy.test;

/**
 * @Author: wushiqiang
 * @Date: Created in 17:46 2020/1/29
 * @Description:
 * @Modified By:
 */
public interface UserServiceTest {
	long countFansCountByUserId(Long userId);

	long countMsgCountByUserId(Long userId);
	long countCollectCountByUserId(Long userId);

	String countFollowCountByUserId(Long userId);

	long countRedBagCountByUserId(Long userId);
	long countCouponCountByUserId(Long userId);
}
