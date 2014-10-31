package com.fjnu.service;

import javax.jws.WebParam;
import javax.jws.WebService;

import org.springframework.stereotype.Service;

import com.fjnu.domain.Model;

@WebService
public interface SportModelService {
	// 添加运动模型
	public boolean addModel(@WebParam(name = "model") Model model);

	// 获取最新运动模型
	public String getCurrentModel(@WebParam(name = "email") String email);

	// 按ID获取运动模型
	public String getModelByID(@WebParam(name = "id") int id,
			@WebParam(name = "email") String email);
}
