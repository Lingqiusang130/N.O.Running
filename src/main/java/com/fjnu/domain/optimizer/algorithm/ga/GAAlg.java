package com.fjnu.domain.optimizer.algorithm.ga;

import com.fjnu.domain.optimizer.algorithm.ga.chromsome.Chromosome;
import com.fjnu.domain.optimizer.algorithm.ga.chromsome.DefaultChromOpt;
import com.fjnu.domain.optimizer.algorithm.ga.chromsome.IChromOperation;
import com.fjnu.domain.optimizer.algorithm.ga.chromsome.ModelingChromOpt;
import com.fjnu.domain.optimizer.algorithm.ga.chromsome.ServiceChromOpt;
import com.fjnu.domain.optimizer.algorithm.ga.population.DefaultPopuOpt;
import com.fjnu.domain.optimizer.algorithm.ga.population.IPopuOperation;
import com.fjnu.domain.optimizer.algorithm.ga.population.Population;
import com.fjnu.domain.optimizer.data.DataOparation;

public class GAAlg {

	// 产生随机数
	private GAParameter parameter = null;
	private IChromOperation chromOpt = null;
	private IPopuOperation popuOpt = null;
	private Population population = null;
	private Population tempPopulation = null;
	private Chromosome bestChrom = null;
	private Chromosome curBestChrom = null;

	public GAParameter getParameter() {
		return parameter;
	}

	public void setParameter(GAParameter parameter) {
		this.parameter = parameter;
	}

	public GAAlg() {
		chromOpt = new DefaultChromOpt();
		parameter = new GAParameter();
		new DataOparation();
		popuOpt = new DefaultPopuOpt(chromOpt);
		population = new Population(popuOpt, chromOpt);
		tempPopulation = new Population(popuOpt, chromOpt);
		bestChrom = new Chromosome(chromOpt);
		curBestChrom = new Chromosome(chromOpt);
	}

	public GAAlg(IChromOperation chromOpt, GAParameter parameter,
			Population population, Chromosome chrom) {
		this.chromOpt = chromOpt;
		this.parameter = parameter;
		new DataOparation();
		popuOpt = new DefaultPopuOpt(chromOpt);
		this.population = population;
		this.tempPopulation = population;
		this.bestChrom = chrom;
		this.curBestChrom = chrom;
	}

	public GAAlg(IChromOperation chromOpt) {
		this.chromOpt = chromOpt;
		parameter = new GAParameter();
		new DataOparation();
		popuOpt = new DefaultPopuOpt(chromOpt);
		population = new Population(popuOpt, chromOpt);
		tempPopulation = new Population(popuOpt, chromOpt);
		bestChrom = new Chromosome(chromOpt);
		curBestChrom = new Chromosome(chromOpt);
	}

	public void setGAparameter() {
		parameter.setMaxIterNum(1000);
	}

	/**
	 * 
	 * 运行GA算法,返回结果
	 */

	public Chromosome run() {
		// 设定算法参数
		setGAparameter();

		// 保存全局最优染色体
		bestChrom = population.getBestChrom();

		int itemNum = 1;
		int maxItemNum = parameter.getMaxIterNum();

		while (itemNum <= maxItemNum) {
			// 选择操作,产生临时种群
			tempPopulation = population.choose();

			// 进行单点交叉操作
			tempPopulation.crossover(1);

			// 进行单点变异操作
			tempPopulation.mutate(1);

			// 获取当代的最优染色体
			curBestChrom = tempPopulation.getBestChrom();

			if (Double.compare(curBestChrom.getFitness(), 25d) > 0) {
				bestChrom = curBestChrom;
				break;
			}

			if (curBestChrom.getFitness() > bestChrom.getFitness()) {
				bestChrom = curBestChrom;
			}

			itemNum++;
		}
		return bestChrom;
	}

}
