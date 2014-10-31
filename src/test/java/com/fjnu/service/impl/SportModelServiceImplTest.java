package com.fjnu.service.impl;

import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fjnu.domain.Model;
import com.fjnu.domain.Parameter;
import com.fjnu.domain.PidPara;
import com.fjnu.domain.Scheme;
import com.fjnu.domain.User;
import com.fjnu.service.SportModelService;
import com.alibaba.fastjson.*;

public class SportModelServiceImplTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testAddModel() {
		ApplicationContext ctx = new ClassPathXmlApplicationContext(
				"applicationContext.xml");
		SportModelService service = (SportModelServiceImpl) ctx
				.getBean("sportModelServiceImpl");
		Model model = new Model();
		User user = new User();
		user.setEmail("137590333@qq.com");
		model.setUser(user);
		Parameter parameter = new Parameter();
		Scheme scheme = new Scheme();
		parameter.setA1(1);
		parameter.setA2(2);
		parameter.setA3(3);
		parameter.setA4(4);
		parameter.setA5(5);
		model.setParameter(parameter);
		scheme.setBestSpeed(2.0);
		scheme.setMinute(20);
		List<Scheme> schemes = new ArrayList<Scheme>();
		schemes.add(scheme);
		model.setSchemes(schemes);
		model.setStartTime(newMyDate("2014-10-04 20:29:09"));
		model.setEndTime(newMyDate("2014-10-04 20:29:09"));
		PidPara pidPara = new PidPara();
		pidPara.setKd(5.998422292036105);
		pidPara.setKi(3.4506247628946762);
		pidPara.setKp(6.94787200840841);
		model.setPidPara(pidPara);
		service.addModel(model);

	}

	@Test
	public void testGetCurrentModel() {
		ApplicationContext ctx = new ClassPathXmlApplicationContext(
				"applicationContext.xml");
		SportModelService service = (SportModelServiceImpl) ctx
				.getBean("sportModelServiceImpl");
		String email = "137590337@qq.com";
		Model model = JSON.parseObject(service.getCurrentModel(email),
				Model.class);
		if (model != null) {
			System.out.println(model.getId() + "");
			System.out.println(model.getParameter().getId());
			System.out.println(model.getSchemes().get(0).getId());
		} else {
			System.out.println("Model is null");
		}
	}

	@Test
	public void testGetModelByID() {
		ApplicationContext ctx = new ClassPathXmlApplicationContext(
				"applicationContext.xml");
		SportModelService service = (SportModelServiceImpl) ctx
				.getBean("sportModelServiceImpl");
		String email = "137590332@qq.com";
		Model model = JSON.parseObject(service.getModelByID(1, email),
				Model.class);

		if (model != null) {
			System.out.println(model.getId() + "");
			System.out.println(model.getParameter().getId());
			System.out.println(model.getSchemes().get(0).getId());
			System.out.println(model.getUser().getEmail());
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

}
