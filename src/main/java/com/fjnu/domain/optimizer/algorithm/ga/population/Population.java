package com.fjnu.domain.optimizer.algorithm.ga.population;

import java.util.ArrayList;
import java.util.List;

import com.fjnu.domain.optimizer.algorithm.ga.chromsome.Chromosome;
import com.fjnu.domain.optimizer.algorithm.ga.chromsome.IChromOperation;

/**
 * 种群类
 */
public class Population {

	// 染色体操作类
	private IChromOperation chromOpt = null;
	// 种群操作类
	private IPopuOperation popuOpt = null;

	// 染色体的数组列表
	private List<Chromosome> chroms = new ArrayList<Chromosome>();
	// 最优染色体
	private Chromosome bestChrom = null;

	public IChromOperation getChromOpt() {
		return chromOpt;
	}

	public void setChromOpt(IChromOperation chromOpt) {
		this.chromOpt = chromOpt;
	}

	public IPopuOperation getPopuOpt() {
		return popuOpt;
	}

	public void setPopuOpt(IPopuOperation popuOpt) {
		this.popuOpt = popuOpt;
	}

	public List<Chromosome> getChroms() {
		return chroms;
	}

	public void setChroms(List<Chromosome> chroms) {
		this.chroms = chroms;
	}

	public void setBestChrom(Chromosome bestChrom) {
		this.bestChrom = bestChrom;
	}

	// 产生空种群
	public Population() {
	}

	// 产生随机种群
	public Population(IPopuOperation popuOpt, IChromOperation chromOpt) {
		this.chromOpt = chromOpt;
		this.popuOpt = popuOpt;
		chroms = this.popuOpt.getRndChroms();
	}

	/**
	 * @return:返回最优染色体
	 */
	public Chromosome getBestChrom() {

		// 比较各个适应值
		int size = chroms.size();

		double bestFitness = 0;
		int bestIdx = -1;
		if (bestChrom != null) {
			bestFitness = bestChrom.getFitness();
		}
		for (int i = 0; i < size; i++) {
			double tempFitness = chroms.get(i).getFitness();
			if (Double.compare(tempFitness, bestFitness) > 0) {
				bestFitness = tempFitness;
				bestIdx = i;
			}
		}
		if (bestIdx != -1) {
			bestChrom = chroms.get(bestIdx).copy();
		}
		return bestChrom;
	}

	/**
	 * 
	 * @param crossoverNum
	 *            ：交叉的数目
	 */
	public void crossover(int crossoverNum) {
		chroms = popuOpt.crossover(chroms, crossoverNum);
	}

	/**
	 * 种群的变异操作，修改了其中的染色体
	 * 
	 * @param mutateNum
	 *            ：变异次数，目前仅实现单点变异
	 */
	public void mutate(int mutateNum) {
		chroms = popuOpt.mutate(chroms, mutateNum);
	}

	/**
	 * 
	 * @return:返回被选择染色体的下标
	 */
	public Population choose() {

		Population chosenPopu = copy();
		// 装配适应度
		int len = chroms.size();
		double[] fitnesses = new double[len];
		for (int i = 0; i < len; i++) {
			fitnesses[i] = chroms.get(i).getFitness();
		}

		// 获取被选择染色体的下标
		int idxs[] = popuOpt.choose(fitnesses);
		// 产生选择后种群的染色体
		List<Chromosome> chosenChroms = chosenPopu.getChroms();

		for (int i = 0; i < len; i++) {
			chosenChroms.set(i, chroms.get(idxs[i]).copy());
		}
		return chosenPopu;
	}

	public Population copy() {
		Population newPopu = new Population();
		newPopu.setChromOpt(chromOpt);
		newPopu.setPopuOpt(popuOpt);

		newPopu.setBestChrom(bestChrom);
		// 复制种群中的染色体
		for (int i = 0; i < chroms.size(); i++) {
			Chromosome tmpChrom = chroms.get(i);
			newPopu.getChroms().add(tmpChrom.copy());
		}
		return newPopu;
	}

}