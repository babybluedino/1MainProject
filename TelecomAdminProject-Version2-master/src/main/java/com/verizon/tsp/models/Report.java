package com.verizon.tsp.models;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

public class Report {

	
	
	
	//private String planName;
	
	
	private double January;
	private double February;
	private double March;
	private double April;
	private double May;
	private double June;
	private double July;
	private double August;
	private double September;
	private double October;
	private double November;
	private double December;
	
	/*public String getPlanName() {
		return planName;
	}

	public void setPlanName(String planName) {
		this.planName = planName;
	}*/

	public double getJanuary() {
		return January;
	}

	public void setJanuary(double january) {
		January = january;
	}

	public double getFebruary() {
		return February;
	}

	public void setFebruary(double february) {
		February = february;
	}

	public double getMarch() {
		return March;
	}

	public void setMarch(double march) {
		March = march;
	}

	public double getApril() {
		return April;
	}

	public void setApril(double april) {
		April = april;
	}

	public double getMay() {
		return May;
	}

	public void setMay(double may) {
		May = may;
	}

	public double getJune() {
		return June;
	}

	public void setJune(double june) {
		June = june;
	}

	public double getJuly() {
		return July;
	}

	public void setJuly(double july) {
		July = july;
	}

	public double getAugust() {
		return August;
	}

	public void setAugust(double august) {
		August = august;
	}

	public double getSeptember() {
		return September;
	}

	public void setSeptember(double september) {
		September = september;
	}

	public double getOctober() {
		return October;
	}

	public void setOctober(double october) {
		October = october;
	}

	public double getNovember() {
		return November;
	}

	public void setNovember(double november) {
		November = november;
	}

	public double getDecember() {
		return December;
	}

	public void setDecember(double december) {
		December = december;
	}


	
	public int getColumnCount() {

        return 13;
    }

	public Report(double january, double february, double march, double april, double may, double june, double july,
			double august, double september, double october, double november, double december) {
	
		this.January = january;
		this.February = february;
		this.March = march;
		this.April = april;
		this.May = may;
		this.June = june;
		this.July = july;
		this.August = august;
		this.September = september;
		this.October = october;
		this.November = november;
		this.December = december; 
	}
	
}
