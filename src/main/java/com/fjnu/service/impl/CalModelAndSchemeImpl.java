package com.fjnu.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.jws.WebService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fjnu.dao.OneSportDAO;
import com.fjnu.dao.SportModelDAO;
import com.fjnu.dao.UserDAO;
import com.fjnu.domain.Model;
import com.fjnu.domain.OneSport;
import com.fjnu.domain.Parameter;
import com.fjnu.domain.PidPara;
import com.fjnu.domain.Scheme;
import com.fjnu.domain.User;
import com.fjnu.domain.optimizer.algorithm.ga.GAAlg;
import com.fjnu.domain.optimizer.algorithm.ga.chromsome.Chromosome;
import com.fjnu.domain.optimizer.algorithm.ga.chromsome.ModelingChromOpt;
import com.fjnu.domain.optimizer.algorithm.ga.chromsome.PIDChromOpt;
import com.fjnu.domain.optimizer.algorithm.ga.chromsome.ServiceChromOpt;
import com.fjnu.domain.optimizer.data.DataOparation;
import com.fjnu.service.CalModelAndScheme;
import com.fjnu.service.OneSportService;
import com.fjnu.service.SportModelService;
import com.fjnu.service.UserService;
import com.alibaba.fastjson.*;
import org.springframework.stereotype.Service;
import org.springframework.web.context.support.WebApplicationContextUtils;
@Service
@WebService(serviceName="calModelAndSchemeImpl", endpointInterface="com.fjnu.service.CalModelAndScheme")
public class CalModelAndSchemeImpl implements CalModelAndScheme{
	@Autowired
	private OneSportDAO oneSportDAO;
	@Autowired
	private SportModelDAO sportModelDAO;	
	@Autowired
	private UserDAO userDAO;
	
	private Model model = null;
	private ModelingChromOpt modelingChromOpt = null;
	private GAAlg gaAlgGetModeling = null;
	private ServiceChromOpt serviceChromOpt = null;
	private GAAlg gaAlgGetService = null;
	private DataOparation dataOperation = null;
	private PIDChromOpt pidChromOpt = null;
	private GAAlg gaAlgGetPID = null;
	@Override
	public void run() {
		//获取所有用户
		List<User> users =userDAO.findAllUser();

		//对所有用户进行遍历，分别对各自进行建模
		for (int i = 0; i < users.size(); i++) {
			//获取该用户的最新运动模型
			model = sportModelDAO.findLastModel(users.get(i).getEmail());
			//如果该用户未构建任何运动模型
			if (model == null) {
				model = new Model();
			}
			model.setUser(users.get(i));
			// 获取未建模的数据
			List<OneSport> oneSports = oneSportDAO.findRestOneSport(model);
			// 将数据临时存到DataOparation中
			if (saveToDataOparation(oneSports)) {
				// 建模服务
				modelingChromOpt = new ModelingChromOpt(dataOperation);
				gaAlgGetModeling = new GAAlg(modelingChromOpt);
				gaAlgGetModeling.getParameter().setChromLen(10);
				gaAlgGetModeling.getParameter().setPopuSize(100);
				// 开始建模
				Chromosome modeling = gaAlgGetModeling.run();
				Model successModel = new Model();
				//指定该模型属于该用户
				successModel.setUser(users.get(i));
				// 装配开始时间结束时间
				successModel.setStartTime(oneSports.get(0).getStartTime());
				successModel.setEndTime(oneSports.get(oneSports.size() - 1)
						.getEndTime());
				Parameter parameter = new Parameter();
				parameter.setA1(modeling.getEncodes().get(0));
				parameter.setA2(modeling.getEncodes().get(1));
				parameter.setA3(modeling.getEncodes().get(2));
				parameter.setA4(modeling.getEncodes().get(3));
				parameter.setA5(modeling.getEncodes().get(4));
				// 装配参数
				successModel.setParameter(parameter);

				modelingChromOpt.getDataOpt().setParameter(
						modeling.getEncodes());

				serviceChromOpt = new ServiceChromOpt(dataOperation);
				serviceChromOpt.setDataOpt(modelingChromOpt.getDataOpt());
				gaAlgGetService = new GAAlg(serviceChromOpt);
				gaAlgGetService.getParameter().setChromLen(10); // 10
																// 代表10分钟内每分钟最佳速度
				gaAlgGetService.getParameter().setPopuSize(100);
				// 开始生成运动方案
				Chromosome service = gaAlgGetService.run();

				List<Scheme> schemes = new ArrayList<Scheme>();
				for (int j = 0; j < service.getEncodes().size(); j++) {
					Scheme s = new Scheme();
					s.setBestSpeed(service.getEncodes().get(j));
					s.setMinute(j);
					schemes.add(s);
				}
				// 装配运动方案
				successModel.setSchemes(schemes);
				// 添加运动模型到数据库
				serviceChromOpt.getDataOpt().setBest_u(service.getEncodes());
				
				pidChromOpt = new PIDChromOpt(dataOperation);
				pidChromOpt.setDataOpt(serviceChromOpt.getDataOpt());
				gaAlgGetPID = new GAAlg(pidChromOpt);
				gaAlgGetPID.getParameter().setChromLen(10); //10 代表10分钟内每分钟最佳速度
				gaAlgGetPID.getParameter().setPopuSize(100);
				Chromosome PID = gaAlgGetPID.run();
				PidPara pidPara = new PidPara();
				pidPara.setKd(PID.getEncodes().get(0));
				pidPara.setKi(PID.getEncodes().get(1));
				pidPara.setKp(PID.getEncodes().get(2));
				//装配PID参数
				successModel.setPidPara(pidPara);
				
				sportModelDAO.saveSportModel(successModel);
			}
		}
	}

	private Boolean saveToDataOparation(List<OneSport> oneSports) {
		// 计算建模数据个数
		int size = 0;
		for (int i = 0; i < oneSports.size(); i++) {
			for (int j = 0; j < oneSports.get(i).getMinuteSportData().size(); j++) {
				size++;
			}
		}
		System.out.println(size);

		if (size > 0) {
			double measure_x1[] = new double[size];
			double measure_u[] = new double[size];
			int x = 0;
			int u = 0;
			for (int i = 0; i < oneSports.size(); i++) {
				for (int j = 0; j < oneSports.get(i).getMinuteSportData()
						.size(); j++) {
					measure_x1[x] = oneSports.get(i).getMinuteSportData()
							.get(j).getHeartRate();
					measure_u[u] = oneSports.get(i).getMinuteSportData().get(j)
							.getSpeed();

					x++;
					u++;
				}
			}
			// 将数据临时存到DataOparation中
			dataOperation = new DataOparation();
			dataOperation.setMeasure_x1(measure_x1);
			dataOperation.setMeasure_u(measure_u);
			return true;
		} else {
			return false;
		}
	}
}
