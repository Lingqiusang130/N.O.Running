package com.fjnu.domain.optimizer.data;

import java.util.ArrayList;
import java.util.List;

public class DataOparation {

	List<Double> encodes = new ArrayList<Double>();
	List<Double> best_u = new ArrayList<Double>();
	private double measure_x1[];
	private double measure_u[];

	// public void setMeasure_x1() {
	// }
	// //心率
	// public double[] getMeasure_x1() {
	// double measure_x1[] = { 0, 20, 25, 33, 32, 38, 44, 47, 48, 39, 44 };
	// return measure_x1;
	// }
	//
	// public void setMeasure_u() {
	// }
	// //速度
	// public double[] getMeasure_u() {
	// double measure_u[] = { 0, 3, 4, 5, 4, 4, 5, 4, 3, 4, 4 };
	// return measure_u;
	// }

	public void setParameter(List<Double> encodes) {
		this.encodes = encodes;
	}

	public double[] getMeasure_x1() {
		return measure_x1;
	}

	public void setMeasure_x1(double[] measure_x1) {
		this.measure_x1 = measure_x1;
	}

	public double[] getMeasure_u() {
		return measure_u;
	}

	public void setMeasure_u(double[] measure_u) {
		this.measure_u = measure_u;
	}

	public List<Double> getParameter() {
		return this.encodes;
	}

	public List<Double> getBest_u() {
		return best_u;
	}

	public void setBest_u(List<Double> best_u) {
		this.best_u = best_u;
	}

}
