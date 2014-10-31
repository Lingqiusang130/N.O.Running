package com.fjnu.dao;

import com.fjnu.domain.Model;

public interface SportModelDAO {
	
	public boolean saveSportModel(Model model);

	public Model findLastModel(String email);

	public Model findModelByID(int id, String email);
}
