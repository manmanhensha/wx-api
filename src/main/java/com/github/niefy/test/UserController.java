package com.github.niefy.test;

import com.github.niefy.test.原始线程池异步返回.MyFutureTask;
import com.github.niefy.test.原始线程池异步返回.UserBehaviorDataDTO;
import com.github.niefy.test.原始线程池异步返回.UserServiceTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author: wushiqiang
 * @Date: Created in 17:38 2020/1/29
 * @Description:
 * @Modified By:
 */
@Controller
@RequestMapping("user")
public class UserController {

	@Autowired
	private UserServiceTest userService;


	@Autowired
	private MyFutureTask myFutureTask;


	@GetMapping("/index")
	@ResponseBody
	public String index() {
		return "启动用户模块成功~~~~~~~~";
	}

	//http://localhost:8080/api/user/get/data?userId=4

	@GetMapping("/get/data")
	@ResponseBody
	public UserBehaviorDataDTO getUserData(Long userId) {
		System.out.println("UserController的线程:" + Thread.currentThread());
		long begin = System.currentTimeMillis();
		UserBehaviorDataDTO userAggregatedResult = myFutureTask.getUserAggregatedResult(userId);
		long end = System.currentTimeMillis();
		System.out.println("===============总耗时:" + (end - begin) /1000.0000+ "秒");
		return userAggregatedResult;
	}

}
