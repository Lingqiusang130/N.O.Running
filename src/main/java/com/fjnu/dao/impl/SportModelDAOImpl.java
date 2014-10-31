package com.fjnu.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.fjnu.dao.SportModelDAO;
import com.fjnu.domain.Model;

@Repository
public class SportModelDAOImpl implements SportModelDAO {

	@Autowired
	private SessionFactory sessionFactory;

	// 保存运动模型
	@Override
	public boolean saveSportModel(Model model) {
		Session session = sessionFactory.openSession();
		try {
			session.save(model);
			return true;
		} catch (Exception e) {
			return false;
		}finally{
			session.flush();
			session.close();
		}
	}

	// 获取最近一次建模的模型
	@Override
	public Model findLastModel(String email) {
		Session session = sessionFactory.openSession();

		String hql = " from Model t where t.user.email=:email order by id desc ";
		Query q = session.createQuery(hql).setString("email", email);
		q.setFirstResult(0);
		q.setMaxResults(1);
		List<Model> model = (List<Model>) q.list();
		if (model.size() > 0) {
			return model.get(0);
		} else {
			return null;
		}
	}

	// 通过ID获取运动模型
	@Override
	public Model findModelByID(int id, String email) {
		Session session = sessionFactory.openSession();
		Query q = session
				.createQuery(
						" from Model t where t.id=:id and t.user.email=:email ")
				.setInteger("id", id).setString("email", email);
		List<Model> model = (List<Model>) q.list();

		if (model.size() > 0) {
			return model.get(0);
		} else {
			return null;
		}
	}

}
