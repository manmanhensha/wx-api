package com.github.niefy.test.原始线程池异步返回;

import lombok.Builder;
import lombok.Data;

/**
 * @Author: wushiqiang
 * @Date: Created in 17:41 2020/1/29
 * @Description:
 * @Modified By:
 */
@Data
@Builder
public class UserBehaviorDataDTO {

	private long fansCount;
	private long msgCount;
	private long collectCount;
	private String followCount;
	private long redBagCount;
	private long couponCount;

}
