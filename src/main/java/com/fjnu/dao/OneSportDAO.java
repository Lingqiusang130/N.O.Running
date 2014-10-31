package com.fjnu.dao;

import java.util.List;

import com.fjnu.domain.Model;
import com.fjnu.domain.OneSport;

public interface OneSportDAO {
	
	public boolean saveOneSport(OneSport oneSport);

	public List<OneSport> findOneSportByDate(String date, String email);

	public List<OneSport> findRestOneSport(Model model);
	
	public OneSport findLastOneSport(String email);
	
	public List<OneSport> findAllOneSport(String email);
	
	public String findDataStatistics(String email);
}
