package com.fjnu.domain.optimizer.algorithm.ga;

import com.fjnu.util.fileUtil.FileNameUtil;
import com.fjnu.util.fileUtil.PropFileUtil;

/**
 * GA参数类
 */
public class GAParameter {

	// 配置文件工具类
	private PropFileUtil propFileUtil = null;
	// 配置文件名
	private String gaCfgFileName = null;
	/**
	 * 染色体的长度
	 */
	private int chromLen;
	/**
	 * 交叉概率
	 */
	private float crossProb = 0;
	/**
	 * 变异概率
	 */
	private float mutateProb = 0;
	/**
	 * 最大迭代次数
	 */
	private int maxIterNum = 0;
	/**
	 * 种群规模
	 */
	private int popuSize = 0;
	/**
	 * 最大适应值
	 */
	private double maxFitness = 0;

	/**
	 * 算法输出的excel文件名
	 */
	private String excelFileName = null;

	public PropFileUtil getPropFileUtil() {
		return propFileUtil;
	}

	public void setPropFileUtil(PropFileUtil propFileUtil) {
		this.propFileUtil = propFileUtil;
	}

	public int getChromLen() {
		if (chromLen == 0) {
			chromLen = Integer.parseInt(propFileUtil.getParameterValue(
					"chromLen").trim());
		}
		return chromLen;
	}

	public void setChromLen(int chromLen) {
		if (this.chromLen != chromLen) {
			this.crossProb = chromLen;
			propFileUtil.saveParameter("chromLen", String.valueOf(chromLen)
					.trim());
		}
	}

	public float getCrossProb() {
		if (Float.compare(crossProb, 0) == 0) {
			crossProb = Float.parseFloat(propFileUtil.getParameterValue(
					"crossProb").trim());
		}
		return crossProb;
	}

	public void setCrossProb(float crosProb) {
		if (Float.compare(this.crossProb, crosProb) != 0) {
			this.crossProb = crosProb;
			propFileUtil.saveParameter("crossProb", String.valueOf(crosProb)
					.trim());
		}
	}

	public float getMutateProb() {
		if (Float.compare(mutateProb, 0) == 0) {
			mutateProb = Float.parseFloat(propFileUtil.getParameterValue(
					"mutateProb").trim());
		}
		return mutateProb;
	}

	public void setMutateProb(float mutateProb) {
		if (Float.compare(this.mutateProb, mutateProb) != 0) {
			this.mutateProb = mutateProb;
			propFileUtil.saveParameter("mutateProb", String.valueOf(mutateProb)
					.trim());
		}

	}

	public int getMaxIterNum() {
		if (maxIterNum == 0) {
			maxIterNum = Integer.parseInt(propFileUtil.getParameterValue(
					"maxIterNum").trim());
		}
		return maxIterNum;
	}

	public void setMaxIterNum(int maxIterNum) {
		if (this.maxIterNum != maxIterNum) {
			this.maxIterNum = maxIterNum;
			propFileUtil.saveParameter("maxIterNum", String.valueOf(maxIterNum)
					.trim());
		}
	}

	public int getPopuSize() {
		if (popuSize == 0) {
			popuSize = Integer.parseInt(propFileUtil.getParameterValue(
					"popuSize").trim());
		}
		return popuSize;
	}

	public void setPopuSize(int popuSize) {
		if (this.popuSize != popuSize) {
			this.popuSize = popuSize;
			propFileUtil.saveParameter("popuSize", String.valueOf(popuSize)
					.trim());
		}
	}

	public double getMaxFitness() {
		if (Double.compare(maxFitness, 0) == 0) {
			maxFitness = Double.parseDouble(propFileUtil.getParameterValue(
					"maxFitness").trim());
		}
		return maxFitness;
	}

	public void setMaxFitness(double maxFitness) {
		if (Double.compare(this.maxFitness, maxFitness) != 0) {
			this.maxFitness = maxFitness;
			propFileUtil.saveParameter("maxFitness", String.valueOf(maxFitness)
					.trim());
		}
	}

	public String getExcelFileName() {
		if (excelFileName == null) {
			excelFileName = propFileUtil.getParameterValue("excelFileName");
		}
		return excelFileName;
	}

	public void setExcelFileName(String excelFileName) {
		boolean isUpdated = false;
		if (this.excelFileName == null) {
			if (excelFileName != null) {
				isUpdated = true;
			}
		} else {
			if (excelFileName != null) {
				if (!this.excelFileName.equals(excelFileName)) {
					isUpdated = true;
				}
			}
		}
		if (isUpdated) {
			this.excelFileName = excelFileName;
			propFileUtil.saveParameter("excelFileName", excelFileName);
		}
	}

	public GAParameter() {
		String basePath = FileNameUtil.getProjectAbsolutePathName()
				+ "src/main/java/com/fjnu/domain/optimizer/algorithm/ga/";
		gaCfgFileName = basePath + "GAConf.properties";
		propFileUtil = PropFileUtil.getInstance(gaCfgFileName);
	}

	public GAParameter(String gaCfgFileName) {
		this.gaCfgFileName = gaCfgFileName;
		propFileUtil = PropFileUtil.getInstance(gaCfgFileName);
	}

}
