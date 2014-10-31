package com.fjnu.service.impl;


import java.util.List;

import javax.jws.WebService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fjnu.dao.OneSportDAO;
import com.fjnu.dao.UserDAO;
import com.fjnu.domain.Model;
import com.fjnu.domain.OneSport;
import com.fjnu.service.OneSportService;
import com.alibaba.fastjson.*;
@Service
@WebService(serviceName="oneSportServiceImpl", endpointInterface="com.fjnu.service.OneSportService")
public class OneSportServiceImpl implements OneSportService{
	@Autowired
	private OneSportDAO oneSportDAO;

	public OneSportDAO getOneSportDAO() {
		return oneSportDAO;
	}

	public void setOneSportDAO(OneSportDAO oneSportDAO) {
		this.oneSportDAO = oneSportDAO;
	}
	//添加OneSport
	@Override
	public boolean addOneSport(String  strOneSport) {
		OneSport oneSport = JSON.parseObject(strOneSport, OneSport.class);
		return oneSportDAO.saveOneSport(oneSport);
		
	}
	//按天获取OneSport
	@Override
	public String getOneSportByDay(String date,String email) {
		String strOneSport = JSON.toJSONString(oneSportDAO.findOneSportByDate(date,email));
		return strOneSport;
	}
	//得到建模截点后的所有OneSport
	@Override
	public List<OneSport> getRestOneSport(Model model) {
		return oneSportDAO.findRestOneSport(model);	
	}

	@Override
	public String getCurrentSport(String email) {	
		String strOneSport = JSON.toJSONString(oneSportDAO.findLastOneSport(email));
		return strOneSport;
	}

	@Override
	public String getAllOneSport(String email) {
		String strOneSports = JSON.toJSONString(oneSportDAO.findAllOneSport(email));
		return strOneSports;
	}

	@Override
	public String getDataStatistics(String email) {
		return oneSportDAO.findDataStatistics(email);
	} 
	
}
