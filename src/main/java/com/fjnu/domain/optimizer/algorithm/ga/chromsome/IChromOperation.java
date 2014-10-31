package com.fjnu.domain.optimizer.algorithm.ga.chromsome;

import java.util.List;

public interface IChromOperation {

	/**
	 * @return：返回一个随机的染色体编码
	 */
	public List<Double> getRndEncodes();

	/**
	 * @param encodes
	 *            :染色体编码
	 * @return：适应值
	 */
	public double calcuFitness(List<Double> encodes);

	/**
	 * @param encodes
	 *            :染色体
	 * @param mutateNum
	 *            ：变异次数
	 * @return：新的染色体编码
	 */
	public List<Double> mutate(List<Double> encodes, int mutateNum);
}
