package com.fjnu.domain.optimizer.algorithm.ga.population;

import java.util.List;

import com.fjnu.domain.optimizer.algorithm.ga.chromsome.Chromosome;

public interface IPopuOperation {

	/**
	 * 
	 * @return：返回一个随机的种群中染色体
	 */
	public List<Chromosome> getRndChroms();

	/**
	 * @param fitnesses
	 *            :种群中个体适应度值的列表
	 * @return：采用轮盘赌法进行选择，返回被选中的个体下标
	 */
	public int[] choose(double[] fitnesses);

	/**
	 * 种群的变异操作，修改了其中的染色体
	 * 
	 * @param mutateNum
	 *            ：变异次数，目前仅实现单点变异
	 */
	public List<Chromosome> mutate(List<Chromosome> chroms, int mutateNum);

	/**
	 * 
	 * @param crossoverNum
	 *            ：交叉的数目
	 */
	public List<Chromosome> crossover(List<Chromosome> chroms, int crossoverNum);
}
