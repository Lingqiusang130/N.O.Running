package com.fjnu.domain.optimizer.algorithm.ga.population;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.fjnu.domain.optimizer.algorithm.ga.chromsome.Chromosome;
import com.fjnu.domain.optimizer.algorithm.ga.chromsome.IChromOperation;

import com.fjnu.domain.optimizer.algorithm.ga.GAParameter;

public class DefaultPopuOpt implements IPopuOperation {

	private Random random = null;
	private GAParameter parameter = null;
	private IChromOperation chromOpt = null;

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

	public DefaultPopuOpt(IChromOperation chromOpt) {
		random = new Random();
		parameter = new GAParameter();
		this.chromOpt = chromOpt;
	}

	public DefaultPopuOpt(GAParameter parameter, Random random,
			IChromOperation chromOpt) {
		this.parameter = parameter;
		this.random = random;
		this.chromOpt = chromOpt;
	}

	/**
	 * @return：返回一个随机的种群中染色体
	 */
	public List<Chromosome> getRndChroms() {
		List<Chromosome> chroms = new ArrayList<Chromosome>();
		int popSize = parameter.getPopuSize();
		for (int i = 0; i < popSize; i++) {
			Chromosome chrom = new Chromosome(chromOpt);
			chroms.add(chrom);
		}
		return chroms;
	}

	/**
	 * 轮盘赌选择
	 * 
	 * @param weights
	 *            :权重数组，所有列表元素之各应为1
	 * @return：被选择的下标值
	 */
	public int[] rouletteWheelChoose(double[] weights) {
		int len = weights.length;
		int[] chooseResults = new int[len];

		// 按累积概率值进行轮盘赌选择
		int cur = 0;
		while (cur < len) {
			double rnum = random.nextDouble();
			double accum = 0.0;
			for (int i = 0; i < len; i++) {
				if (Double.compare(rnum, accum) == 0
						|| rnum < accum + weights[i]) {
					chooseResults[cur] = i;
					break;
				}
				accum += weights[i];
			}
			cur++;
		}
		return chooseResults;
	}

	/**
	 * @param fitnesses
	 *            :种群中个体适应度值的列表
	 * @return：采用轮盘赌法进行选择，返回被选中的个体下标
	 */
	public int[] choose(double[] fitnesses) {
		int popSize = fitnesses.length;
		// 计算适应度的总和
		double fitnessSum = 0;
		for (int j = 0; j < popSize; j++) {
			fitnessSum += fitnesses[j];
		}
		// 计算每个个体适应度占总适应和的比例
		double[] weights = new double[popSize];
		for (int k = 0; k < popSize; k++) {
			weights[k] = fitnesses[k] / fitnessSum;
		}

		return rouletteWheelChoose(weights);
	}

	/**
	 * 种群的变异操作，修改了其中的染色体
	 * 
	 * @param mutateNum
	 *            ：变异次数，目前仅实现单点变异
	 */
	public List<Chromosome> mutate(List<Chromosome> chroms, int mutateNum) {

		List<Chromosome> newChroms = new ArrayList<Chromosome>();

		int size = chroms.size();

		for (int j = 0; j < size; j++) {
			newChroms.add(chroms.get(j));
		}
		float mutateProb = parameter.getMutateProb();
		for (int i = 0; i < size; i++) {
			float tempMutateProb = random.nextFloat();
			if (tempMutateProb <= mutateProb) {
				newChroms.get(i).mutate(mutateNum);
				newChroms.get(i).setFitness(Double.MIN_VALUE);
			}
		}
		return newChroms;
	}

	/**
	 * 
	 * @param crossoverNum
	 *            ：交叉的数目
	 */
	public List<Chromosome> crossover(List<Chromosome> chroms, int crossoverNum) {

		List<Chromosome> newChroms = new ArrayList<Chromosome>();
		// 种群大小
		int popSize = chroms.size();
		// 复制交叉前种群个体
		for (int j = 0; j < popSize; j++) {
			newChroms.add(chroms.get(j));
		}
		// 获取交叉概率
		float crossProb = parameter.getCrossProb();
		// 随机对种群的两个个体进交叉操作
		for (int i = 0; i < popSize && popSize >= 2; i++) {
			// 随机判断第i个体是否参与交叉
			float draw = random.nextFloat();
			// 第i个体参与交叉
			if (draw < crossProb) {
				// 随机产生另一个交叉个体
				int otherIdx = random.nextInt(popSize);
				// 避免自身交叉
				while (otherIdx == i) {
					otherIdx = random.nextInt(popSize);
				}
				// 产生交叉的位置
				int chromLen = parameter.getChromLen();

				int crossPos = random.nextInt(chromLen);
				// 源染色体的编码
				List<Double> srcEncodes = newChroms.get(i).getEncodes();
				// 目标染色体的编码
				List<Double> desEncodes = newChroms.get(otherIdx).getEncodes();
				// 临时编码
				List<Double> tempEncodes = new ArrayList<Double>();
				// 复制源染色体的后半部分编码到临时编码
				for (int j = crossPos; j < chromLen; j++) {
					double tempSB = srcEncodes.get(j);
					tempEncodes.add(tempSB);
				}
				// 修改源染色体编码的后半部分
				for (int m = crossPos; m < chromLen; m++) {
					double tempSB = desEncodes.get(m);
					srcEncodes.set(m, tempSB);
				}
				// 修改目标染色体编码的后半部分
				for (int k = crossPos; k < chromLen; k++) {
					double tempSB = tempEncodes.get(k - crossPos);
					desEncodes.set(k, tempSB);
				}

				newChroms.get(i).setEncodes(srcEncodes);
				newChroms.get(i).setFitness(Double.MIN_VALUE);
				newChroms.get(otherIdx).setEncodes(desEncodes);
				newChroms.get(otherIdx).setFitness(Double.MIN_VALUE);
			}
		}
		return newChroms;
	}

}
