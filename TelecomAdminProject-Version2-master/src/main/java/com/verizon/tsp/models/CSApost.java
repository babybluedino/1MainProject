package com.verizon.tsp.models;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonBackReference;

public class CSApost {

	private long csaId;
	private String csaFname;
	
	private String csaLname;
	
	private String csaMobileNo;
	
	private String csaEmailId;
	
	
	private PrimaryLanguage csaPrimaryLang;
	
	private int tcId;

	public long getCsaId() {
		return csaId;
	}

	public void setCsaId(long csaId) {
		this.csaId = csaId;
	}

	public String getCsaFname() {
		return csaFname;
	}

	public void setCsaFname(String csaFname) {
		this.csaFname = csaFname;
	}

	public String getCsaLname() {
		return csaLname;
	}

	public void setCsaLname(String csaLname) {
		this.csaLname = csaLname;
	}

	public String getCsaMobileNo() {
		return csaMobileNo;
	}

	public void setCsaMobileNo(String csaMobileNo) {
		this.csaMobileNo = csaMobileNo;
	}

	public String getCsaEmailId() {
		return csaEmailId;
	}

	public void setCsaEmailId(String csaEmailId) {
		this.csaEmailId = csaEmailId;
	}

	public PrimaryLanguage getCsaPrimaryLang() {
		return csaPrimaryLang;
	}

	public void setCsaPrimaryLang(PrimaryLanguage csaPrimaryLang) {
		this.csaPrimaryLang = csaPrimaryLang;
	}

	public int getTcId() {
		return tcId;
	}

	public void setTcId(int tcId) {
		this.tcId = tcId;
	}

	public CSApost(long csaId, String csaFname, String csaLname, String csaMobileNo, String csaEmailId,
			PrimaryLanguage csaPrimaryLang, int tcId) {
		super();
		this.csaId = csaId;
		this.csaFname = csaFname;
		this.csaLname = csaLname;
		this.csaMobileNo = csaMobileNo;
		this.csaEmailId = csaEmailId;
		this.csaPrimaryLang = csaPrimaryLang;
		this.tcId = tcId;
	}

	public CSApost() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
}
