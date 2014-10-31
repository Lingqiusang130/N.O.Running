package com.fjnu.service;

import java.util.List;

import javax.jws.WebParam;
import javax.jws.WebService;

import com.fjnu.domain.Model;
import com.fjnu.domain.OneSport;

@WebService
public interface OneSportService {
	// 添加OneSport
	public boolean addOneSport(@WebParam(name = "onesport") String strOneSport);

	// 按天获取OneSport
	public String getOneSportByDay(@WebParam(name = "date") String date,
			@WebParam(name = "email") String email);

	// 得到建模截点后的所有OneSport
	public List<OneSport> getRestOneSport(Model model);
	//得到最新的运动数据
	public String getCurrentSport(@WebParam(name="email")String email);
	//得到所有运动数据
	public String getAllOneSport(@WebParam(name="email")String email);
	
	public String getDataStatistics(@WebParam(name="email")String email);
	
}
