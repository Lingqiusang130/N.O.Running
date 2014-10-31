package com.fjnu.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fjnu.domain.MinuteSportData;
import com.fjnu.domain.Model;
import com.fjnu.domain.OneSport;
import com.fjnu.domain.User;
import com.fjnu.service.OneSportService;
import com.alibaba.fastjson.*;

public class OneSportServiceImplTest {

	@Test
	public void testAddOneSport() {
		ApplicationContext ctx = new ClassPathXmlApplicationContext(
				"applicationContext.xml");

		OneSportService service = (OneSportServiceImpl) ctx
				.getBean("oneSportServiceImpl");

		User user = new User();
		user.setEmail("137590334@qq.com");
		MinuteSportData minuteSportData = new MinuteSportData();
		// 心率： 0, 20, 25, 33, 32, 38, 44, 47, 48, 39, 44
		// 速度： 0, 3, 4, 5, 4, 4, 5, 4, 3, 4, 4
		minuteSportData.setCollectTime(new Date());
		minuteSportData.setHeartRate(0);
		minuteSportData.setSpeed(0);
		minuteSportData.setNumber(1);

		OneSport oneSport = new OneSport();
		oneSport.setDate("2014-10-06");
		oneSport.setCount(1);
		oneSport.setEndTime(new Date());
		oneSport.setStartTime(new Date());
		List<MinuteSportData> l = new ArrayList<MinuteSportData>();
		l.add(minuteSportData);

		oneSport.setMinuteSportData(l);
		oneSport.setUser(user);
		String strOneSport = JSON.toJSONString(oneSport);
		service.addOneSport(strOneSport);
	}

	@Test
	public void testGetOneSportByDay() {
		// OneSportService service = (OneSportService)
		// ApplicationUtil.getBean("oneSportServiceImpl");
		ApplicationContext ctx = new ClassPathXmlApplicationContext(
				"applicationContext.xml");
		OneSportService service = (OneSportServiceImpl) ctx
				.getBean("oneSportServiceImpl");

		String strOneSport = service.getOneSportByDay("2014-10-06",
				"137590333@qq.com");
		if (strOneSport != null) {
			// 反序列化List
			List<OneSport> os = JSON.parseArray(strOneSport, OneSport.class);
			if (os != null) {
				for (int i = 0; i < os.size(); i++) {
					System.out.println("OneSport数据如下");
					System.out.println("ID：" + os.get(i).getId());
					System.out.println("次数：" + os.get(i).getCount());
					System.out.println("开始时间：" + os.get(i).getEndTime());
					System.out.println("结束时间：" + os.get(i).getEndTime());
					System.out.println("日期查询" + os.get(i).getDate());
					for (int j = 0; j < os.get(i).getMinuteSportData().size(); j++) {
						System.out.println("MinuteSportData数据如下");
						System.out
								.println("ID："
										+ os.get(i).getMinuteSportData().get(j)
												.getId());
						System.out.println("第几次采集："
								+ os.get(i).getMinuteSportData().get(j)
										.getNumber());
						System.out.println("采集时间："
								+ os.get(i).getMinuteSportData().get(j)
										.getCollectTime());
						System.out.println("心率："
								+ os.get(i).getMinuteSportData().get(j)
										.getHeartRate());
						System.out.println("速度："
								+ os.get(i).getMinuteSportData().get(j)
										.getSpeed());
						System.out.println(os.get(i).getUser().getEmail());
					}
				}
			}
		}
	}

	@Test
	public void testGetRestOneSportWithModelNULL() {
		ApplicationContext ctx = new ClassPathXmlApplicationContext(
				"applicationContext.xml");
		OneSportService service = (OneSportServiceImpl) ctx
				.getBean("oneSportServiceImpl");
		Model model = null;
		model = new Model();
		User user = new User();
		user.setEmail("137590333@qq.com");// 如果查找到的model为空,设置model的user.email=查找用户email
		model.setUser(user);
		List<OneSport> os = service.getRestOneSport(model);
		for (int i = 0; i < os.size(); i++) {
			System.out.println(os.get(i).getId());
		}
	}

	@Test
	public void testGetRestOneSportWithModelNotNULL() {
		ApplicationContext ctx = new ClassPathXmlApplicationContext(
				"applicationContext.xml");
		OneSportService service = (OneSportServiceImpl) ctx
				.getBean("oneSportServiceImpl");
		Model model = new Model();
		User user = new User();
		user.setEmail("137590332@qq.com");
		model.setUser(user);
		model.setId(1);
		model.setStartTime(newMyDate("2014-10-04 20:29:09"));
		model.setEndTime(newMyDate("2014-10-04 20:29:09"));
		List<OneSport> os = service.getRestOneSport(model);
		for (int i = 0; i < os.size(); i++) {
			System.out.println(os.get(i).getId());
		}
	}

	public Date newMyDate(String myDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date date = null;
		try {
			date = sdf.parse(myDate);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;
	}

	@Test
	public void testGetCurrentOneSport() {
		ApplicationContext ctx = new ClassPathXmlApplicationContext(
				"applicationContext.xml");
		OneSportService service = (OneSportServiceImpl) ctx
				.getBean("oneSportServiceImpl");
		String strOneSport = service.getCurrentSport("137590334@qq.com");
		if (strOneSport != null) {
			// 反序列化List
			OneSport os = JSON.parseObject(strOneSport, OneSport.class);
			if (os != null) {

				System.out.println("OneSport数据如下");
				System.out.println("ID：" + os.getId());
				System.out.println("次数：" + os.getCount());
				System.out.println("开始时间：" + os.getEndTime());
				System.out.println("结束时间：" + os.getEndTime());
				System.out.println("日期查询" + os.getDate());
				for (int j = 0; j < os.getMinuteSportData().size(); j++) {
					System.out.println("MinuteSportData数据如下");
					System.out.println("ID："
							+ os.getMinuteSportData().get(j).getId());
					System.out.println("第几次采集："
							+ os.getMinuteSportData().get(j).getNumber());
					System.out.println("采集时间："
							+ os.getMinuteSportData().get(j).getCollectTime());
					System.out.println("心率："
							+ os.getMinuteSportData().get(j).getHeartRate());
					System.out.println("速度："
							+ os.getMinuteSportData().get(j).getSpeed());
					System.out.println(os.getUser().getEmail());
				}
			}

		}
	}
	
	@Test
	public void testGetAllOneSport() {
		ApplicationContext ctx = new ClassPathXmlApplicationContext(
				"applicationContext.xml");
		OneSportService service = (OneSportServiceImpl) ctx
				.getBean("oneSportServiceImpl");
		String strOneSport = service.getAllOneSport("137590334@qq.com");
		if (strOneSport != null) {
			// 反序列化List
			List<OneSport> os = JSON.parseArray(strOneSport, OneSport.class);
			if (os != null) {
				for (int i = 0; i < os.size(); i++) {
					System.out.println("OneSport数据如下");
					System.out.println("ID：" + os.get(i).getId());
					System.out.println("次数：" + os.get(i).getCount());
					System.out.println("开始时间：" + os.get(i).getEndTime());
					System.out.println("结束时间：" + os.get(i).getEndTime());
					System.out.println("日期查询" + os.get(i).getDate());
					for (int j = 0; j < os.get(i).getMinuteSportData().size(); j++) {
						System.out.println("MinuteSportData数据如下");
						System.out
								.println("ID："
										+ os.get(i).getMinuteSportData().get(j)
												.getId());
						System.out.println("第几次采集："
								+ os.get(i).getMinuteSportData().get(j)
										.getNumber());
						System.out.println("采集时间："
								+ os.get(i).getMinuteSportData().get(j)
										.getCollectTime());
						System.out.println("心率："
								+ os.get(i).getMinuteSportData().get(j)
										.getHeartRate());
						System.out.println("速度："
								+ os.get(i).getMinuteSportData().get(j)
										.getSpeed());
						System.out.println(os.get(i).getUser().getEmail());
					}
				}
			}
		}
	}
	@Test
	public void testGetDataStatistics(){
		ApplicationContext ctx = new ClassPathXmlApplicationContext(
				"applicationContext.xml");
		OneSportService service = (OneSportServiceImpl) ctx
				.getBean("oneSportServiceImpl");
		System.out.println(service.getDataStatistics("137590334@qq.com"));
	}
}
