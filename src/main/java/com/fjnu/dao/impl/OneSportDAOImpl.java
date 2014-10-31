package com.fjnu.dao.impl;

import java.text.DecimalFormat;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.fjnu.dao.OneSportDAO;
import com.fjnu.domain.MinuteSportData;
import com.fjnu.domain.Model;
import com.fjnu.domain.OneSport;

@Repository
public class OneSportDAOImpl implements OneSportDAO {
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public boolean saveOneSport(OneSport oneSport) {
		Session session = sessionFactory.openSession();
		try {
			session.save(oneSport);		
			return true;
		} catch (Exception e) {
			return false;
		}finally{
			session.flush();
			session.close();
		}
	}

	@Override
	public List<OneSport> findOneSportByDate(String date, String email) {
		Session session = sessionFactory.openSession();
		// 通过外键字段查询
		Query q = session
				.createQuery(
						" from OneSport t where t.date=:date and t.user.email=:email")
				.setString("date", date).setString("email", email);
		// q.setMaxResults(10);
		@SuppressWarnings("unchecked")
		List<OneSport> oneSports = (List<OneSport>) q.list();// 返回一个对象集合
		// OneSport oneSport=(OneSport) q.uniqueResult();//只返回一个对象，如果返回的对象有多个会异常

		if (oneSports.size() > 0) {
			return oneSports;
		} else {
			return null;
		}	
	}

	@Override
	public List<OneSport> findRestOneSport(Model model) {
		// 读Model表如果为空从第一条开始获取，如果不为空读最后一条读出endTime到数据库匹配读出剩余的数据
		Session session = sessionFactory.openSession();
		List<OneSport> oneSports = null;
		if (model.getStartTime() == null) {
			Query q = session.createQuery(
					" from OneSport as t where t.user.email=:email ")
					.setString("email", model.getUser().getEmail());
			oneSports = (List<OneSport>) q.list();
		} else {
			// 获取上一个建模的数据截点
			Query q = session
					.createQuery(
							" from OneSport t where t.endTime=:endTime and t.user.email=:email ")
					.setTimestamp("endTime", model.getEndTime())
					.setString("email", model.getUser().getEmail());
			List<OneSport> lastOneSports = (List<OneSport>) q.list();
			// 获取剩余数据
			Query q1 = session
					.createQuery(
							" from OneSport t where t.id>:id and t.user.email=:email ")
					.setInteger("id", lastOneSports.get(0).getId())
					.setString("email", model.getUser().getEmail());
			oneSports = (List<OneSport>) q1.list();
		}
		return oneSports;

	}

	@Override
	public OneSport findLastOneSport(String email) {
		Session session = sessionFactory.openSession();

		String hql = " from OneSport t where t.user.email=:email order by id desc ";
		Query q = session.createQuery(hql).setString("email", email);
		q.setFirstResult(0);
		q.setMaxResults(1);
		List<OneSport> oneSports = (List<OneSport>) q.list();
		if (oneSports.size() > 0) {
			return oneSports.get(0);
		} else {
			return null;
		}
	}

	@Override
	public List<OneSport> findAllOneSport(String email) {
		Session session = sessionFactory.openSession();
		Query q = session.createQuery(
				" from OneSport t where t.user.email=:email ").setString(
				"email", email);
		List<OneSport> oneSports = (List<OneSport>) q.list();
		
		return oneSports;
	}

	@Override
	public String findDataStatistics(String email) {
		double aveHeartRate = 0, sumHeartRate = 0, aveSpeed = 0, sumSpeed = 0, totalDistance = 0, totalTime = 0;
		int count = 0;
		String data = null;
		List<OneSport> oneSports=findAllOneSport(email);	
		for (OneSport os : oneSports) {
			totalTime += os.getMinuteSportData().size();
			count += os.getMinuteSportData().size();
			for (MinuteSportData msd : os.getMinuteSportData()) {
				sumHeartRate += msd.getHeartRate();
				sumSpeed += msd.getSpeed();
				totalDistance += msd.getSpeed() * 60;
			}
		}
		totalDistance = totalDistance/1000;
		aveHeartRate = sumHeartRate / count;
		aveSpeed = sumSpeed / count;
		DecimalFormat fnum = new DecimalFormat("##0.00");
		data =((int) Math.floor(aveHeartRate)+80)+","+fnum.format(aveSpeed)+","+fnum.format(totalDistance)+","+totalTime;
		return data;
	}

}
