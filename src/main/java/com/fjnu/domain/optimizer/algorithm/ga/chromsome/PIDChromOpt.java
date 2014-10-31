package com.fjnu.domain.optimizer.algorithm.ga.chromsome;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.fjnu.domain.optimizer.algorithm.ga.GAParameter;
import com.fjnu.domain.optimizer.data.DataOparation;

public class PIDChromOpt implements IChromOperation {

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

	public PIDChromOpt() {
		random = new Random();
		parameter = new GAParameter();
		dataOpt = new DataOparation();
	}

	// +
	public PIDChromOpt(DataOparation dataOparation) {
		random = new Random();
		parameter = new GAParameter();
		dataOpt = dataOparation;
	}

	public PIDChromOpt(GAParameter parameter, Random random,
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
			double rnd = random.nextDouble() * 10;
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
		double tempFitness = 0;
		// 根据规则序列执行结果计算适应度()
		double measure_u[] = dataOpt.getMeasure_u();
		List<Double> best_u = dataOpt.getBest_u();
		int len = best_u.size();

		double e[] = new double[len + 1];
		double sum_e = 0;

		for (int i = 1; i < len + 1; i++) {
			e[i] = measure_u[i - 1] - best_u.get(i - 1);
		}
		e[0] = 0;

		double f[] = new double[len + 1];
		for (int t = 1; t < len + 1; t++) {
			for (int j = 1; j <= t; j++)
				sum_e += e[j];
			f[t] = encodes.get(0)
					* (e[t] + 1 / encodes.get(1) * sum_e + encodes.get(2)
							* (e[t] - e[t - 1]));
			if ((e[t] > 0 && f[t] > 0) || (e[t] < 0 && f[t] < 0)) {
				tempFitness++;
			}
			sum_e = 0;
		}

		fitness = tempFitness;

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
		double newDouble = random.nextDouble() * 10;
		newEncodes.set(mutatePos, newDouble);

		return newEncodes;
	}

}
