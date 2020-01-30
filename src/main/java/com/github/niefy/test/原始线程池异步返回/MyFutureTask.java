package com.github.niefy.test.原始线程池异步返回;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.*;

/**
 * @Author: wushiqiang
 * @Date: Created in 17:34 2020/1/29
 * @Description:
 * @Modified By:
 */
@Slf4j
@Component
public class MyFutureTask {

	@Resource
	UserServiceTest userService;

	/**
	 * 核心线程 8 最大线程 20 保活时间30s 存储队列 10 有守护线程 拒绝策略:将超负荷任务回退到调用者
	 */
	private static ExecutorService executor = new ThreadPoolExecutor(8, 20,
			30L, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(10),
			new ThreadFactoryBuilder().setNameFormat("User_Async_FutureTask-%d").setDaemon(true).build(),
			new ThreadPoolExecutor.CallerRunsPolicy());


	@SuppressWarnings("all")
	public UserBehaviorDataDTO getUserAggregatedResult(final Long userId) {

		System.out.println("MyFutureTask的线程:" + Thread.currentThread());


		long fansCount = 0, msgCount = 0, collectCount = 0,
				 redBagCount = 0, couponCount = 0;
		String followCount = "";

//        fansCount = userService.countFansCountByUserId(userId);
//        msgCount = userService.countMsgCountByUserId(userId);
//        collectCount = userService.countCollectCountByUserId(userId);
//        followCount = userService.countFollowCountByUserId(userId);
//        redBagCount = userService.countRedBagCountByUserId(userId);
//        couponCount = userService.countCouponCountByUserId(userId);

		try {

			Future<Long> fansCountFT = executor.submit(() -> userService.countFansCountByUserId(userId));
			Future<Long> msgCountFT = executor.submit(() -> userService.countMsgCountByUserId(userId));
			Future<Long> collectCountFT = executor.submit(() -> userService.countCollectCountByUserId(userId));
			Future<String> followCountFT = executor.submit(() -> {
				return userService.countFollowCountByUserId(userId);
			});
			Future<Long> redBagCountFT = executor.submit(() -> userService.countRedBagCountByUserId(userId));
			Future<Long> couponCountFT = executor.submit(() -> userService.countCouponCountByUserId(userId));

			//get阻塞
			fansCount = fansCountFT.get();
			msgCount = msgCountFT.get();
			collectCount = collectCountFT.get();
			followCount = followCountFT.get();
			redBagCount = redBagCountFT.get();
			couponCount = couponCountFT.get();

		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
			log.error(">>>>>>聚合查询用户聚合信息异常:" + e + "<<<<<<<<<");
		}
		UserBehaviorDataDTO userBehaviorData =
				UserBehaviorDataDTO.builder().fansCount(fansCount).msgCount(msgCount)
						.collectCount(collectCount).followCount(followCount)
						.redBagCount(redBagCount).couponCount(couponCount).build();

		return userBehaviorData;
	}
}
