package com.fjnu.service.impl;

import javax.jws.WebService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.test.util.JsonPathExpectationsHelper;

import com.fjnu.dao.SportModelDAO;
import com.fjnu.domain.Model;
import com.fjnu.service.SportModelService;
import com.alibaba.fastjson.*;
@Service
@WebService(serviceName="sportModelServiceImpl", endpointInterface="com.fjnu.service.SportModelService")
public class SportModelServiceImpl implements SportModelService {

	@Autowired
	private SportModelDAO sportModelDAO;
	
	public SportModelDAO getSportModelDAO() {
		return sportModelDAO;
	}
	public void setSportModelDAO(SportModelDAO sportModelDAO) {
		this.sportModelDAO = sportModelDAO;
	}
	
	//添加运动模型
	@Override
	public boolean addModel(Model model) {
		return sportModelDAO.saveSportModel(model);	
	}
	//获取最新的运动模型
	@Override
	public String getCurrentModel(String email) {
		String strModel=JSON.toJSONString(sportModelDAO.findLastModel(email));
		return strModel;
	}
	//通过ID获取运动模型
	@Override
	public String getModelByID(int id,String email) {
		String strModel=JSON.toJSONString(sportModelDAO.findModelByID(id,email));
		return strModel;
	}
	
}
