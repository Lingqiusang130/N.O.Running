package com.fjnu.domain.optimizer.algorithm.ga.chromsome;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.fjnu.domain.optimizer.data.DataOparation;

import com.fjnu.domain.optimizer.algorithm.ga.GAParameter;
import com.fjnu.domain.optimizer.algorithm.ga.Kutta;

public class ModelingChromOpt implements IChromOperation {

	// 产生随机数
	private Random random = null;
	// 算法参数类
	private GAParameter parameter = null;
	// 数据操作类
	private DataOparation dataOpt = null;

	public GAParameter getParameter() {
		return parameter;
	}

	public void setParameter(GAParameter parameter) {
		this.parameter = parameter;
	}

	public Random getRandom() {
		return random;
	}

	public void setRandom(Random random) {
		this.random = random;
	}

	public DataOparation getDataOpt() {
		return dataOpt;
	}

	public void setDataOpt(DataOparation dataOpt) {
		this.dataOpt = dataOpt;
	}

	public ModelingChromOpt() {
		random = new Random();
		parameter = new GAParameter();
		dataOpt = new DataOparation();
	}

	// +
	public ModelingChromOpt(DataOparation dataOparation) {
		random = new Random();
		parameter = new GAParameter();
		dataOpt = dataOparation;
	}

	public ModelingChromOpt(GAParameter parameter, Random random,
			DataOparation dataOpt) {
		this.parameter = parameter;
		this.random = random;
		this.dataOpt = dataOpt;
	}

	/**
	 * @return：返回一个随机的染色体编码
	 */
	public List<Double> getRndEncodes() {
		List<Double> rndEncodes = new ArrayList<Double>();
		// 染色体长度
		int chromLen = parameter.getChromLen();
		// 产生随机染色体编码
		for (int i = 0; i < chromLen; i++) {
			double rnd = random.nextDouble() * 5;
			rndEncodes.add(rnd);
		}
		return rndEncodes;
	}

	/**
	 * 
	 * @param encodes
	 *            :染色体编码
	 * @return：返回适应值=（响应时间的降低幅度*序列中有改进效果的规则数目）/序列中非0规则总数
	 */

	public double calcuFitness(List<Double> encodes) {
		double fitness = 0.1;
		// 根据规则序列执行结果计算适应度()
		double measure_x1[] = dataOpt.getMeasure_x1();
		double measure_u[] = dataOpt.getMeasure_u();
		int N = measure_x1.length;
		double estimated_x1[] = new double[N];
		double estimated_x2[] = new double[N];
		estimated_x1[0] = 0;
		estimated_x2[0] = 0;
		double h = 1;
		double f1, f2, f3, f4, g1, g2, g3, g4;
		Kutta k = new Kutta();
		int len = measure_x1.length;
		for (int j = 1; j < len; j++) {
			f1 = k.x1(estimated_x1[j - 1], estimated_x2[j - 1], encodes.get(0),
					encodes.get(1), measure_u[j]);
			g1 = k.x2(estimated_x1[j - 1], estimated_x2[j - 1], encodes.get(2),
					encodes.get(3), encodes.get(4));
			f2 = k.x1(estimated_x1[j - 1] + h / 2 * f1, estimated_x2[j - 1] + h
					/ 2 * g1, encodes.get(0), encodes.get(1), measure_u[j]);
			g2 = k.x2(estimated_x1[j - 1] + h / 2 * f1, estimated_x2[j - 1] + h
					/ 2 * g1, encodes.get(2), encodes.get(3), encodes.get(4));
			f3 = k.x1(estimated_x1[j - 1] + h / 2 * f2, estimated_x2[j - 1] + h
					/ 2 * g2, encodes.get(0), encodes.get(1), measure_u[j]);
			g3 = k.x2(estimated_x1[j - 1] + h / 2 * f2, estimated_x2[j - 1] + h
					/ 2 * g2, encodes.get(2), encodes.get(3), encodes.get(4));
			f4 = k.x1(estimated_x1[j - 1] + h * f3, estimated_x2[j - 1] + h
					* g3, encodes.get(0), encodes.get(1), measure_u[j]);
			g4 = k.x2(estimated_x1[j - 1] + h * f3, estimated_x2[j - 1] + h
					* g3, encodes.get(2), encodes.get(3), encodes.get(4));
			estimated_x1[j] = estimated_x1[j - 1] + h / 6
					* (f1 + 2 * f2 + 2 * f3 + f4);
			estimated_x2[j] = estimated_x2[j - 1] + h / 6
					* (g1 + 2 * g2 + 2 * g3 + g4);
		}
		for (int j = 1; j < len; j++)
			fitness = fitness + Math.abs(estimated_x1[j] - measure_x1[j]);

		fitness = 1000 / fitness;
		return fitness;
	}

	/**
	 * @param chrom
	 *            :染色体
	 * @param mutateNum
	 *            ：变异次数,目前只考虑单点变异
	 * @return：新的染色体编码
	 */
	public List<Double> mutate(List<Double> encodes, int mutateNum) {
		List<Double> newEncodes = new ArrayList<Double>();

		int size = encodes.size();
		// 复制原来的染色体编码
		for (int i = 0; i < size; i++) {
			double tmpNum = encodes.get(i);
			newEncodes.add(tmpNum);
		}

		// 随机产生变异位置
		int mutatePos = random.nextInt(size);
		double newDouble = random.nextDouble() * 5;
		newEncodes.set(mutatePos, newDouble);

		return newEncodes;
	}

}
