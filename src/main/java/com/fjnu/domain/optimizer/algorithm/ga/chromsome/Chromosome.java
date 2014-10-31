package com.fjnu.domain.optimizer.algorithm.ga.chromsome;

import java.util.ArrayList;
import java.util.List;

import com.fjnu.domain.optimizer.algorithm.ga.chromsome.IChromOperation;

/**
 * 染色体类
 */

public class Chromosome {

	// 染色体编码
	private List<Double> encodes = new ArrayList<Double>();
	// 当前适应度
	private double fitness = Double.MIN_VALUE;
	// 遗传操作类
	private IChromOperation chromOpt = null;

	// 当前执行结果

	public IChromOperation getChromOpt() {
		return chromOpt;
	}

	public void setChromOpt(IChromOperation chromOpt) {
		this.chromOpt = chromOpt;
	}

	public List<Double> getEncodes() {
		return encodes;
	}

	public void setEncodes(List<Double> encodes2) {
		this.encodes = encodes2;
	}

	public void setFitness(double fitness) {
		this.fitness = fitness;
	}

	// 获取适应值
	public double getFitness() {
		if (Double.compare(fitness, Double.MIN_VALUE) == 0) {
			fitness = chromOpt.calcuFitness(encodes);
		}
		return fitness;
	}

	// 构造空染色体
	public Chromosome() {
	}

	// 构造随机染色体
	public Chromosome(IChromOperation chromOpt) {
		this.chromOpt = chromOpt;
		// 获取随机染色体的随机编码
		encodes = chromOpt.getRndEncodes();
	}

	/**
	 * 变异操作，修改了原来染色体编码
	 * 
	 * @param mutateNum
	 *            :变异次数,目前仅考虑单点变异
	 */
	public void mutate(int mutateNum) {
		setEncodes(chromOpt.mutate(encodes, mutateNum));
	}

	// 复制行为
	public Chromosome copy() {
		Chromosome newChrom = new Chromosome();
		List<Double> newEncodes = new ArrayList<Double>();
		int len = encodes.size();
		for (int i = 0; i < len; i++) {
			double tempStr = encodes.get(i);
			newEncodes.add(tempStr);
		}
		newChrom.setEncodes(newEncodes);
		newChrom.fitness = fitness;
		newChrom.setChromOpt(chromOpt);
		return newChrom;
	}

}