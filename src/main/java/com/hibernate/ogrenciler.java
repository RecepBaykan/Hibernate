package com.hibernate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "ogrenciler")
public class ogrenciler {
	
	@Id  
	@Column(name = "okulNO")
	private String okulNo;
	
	@Column(name = "ad")
	private String ad;
	
	@Column(name = "soyad")
	private String soyad;
	
	@Column(name ="cinsiyet")
	private String cinsiyet;
	
	
	
	public String getOkulNo() {
		return okulNo;
	}
	public void setOkulNo(String okulNo) {
		this.okulNo = okulNo;
	}
	public String getAd() {
		return ad;
	}
	public void setAd(String ad) {
		this.ad = ad;
	}
	public String getSoyad() {
		return soyad;
	}
	public void setSoyad(String soyad) {
		this.soyad = soyad;
	}
	
	public String getCinsiyet() {
		return cinsiyet;
	}
	public void setCinsiyet(String cinsiyet) {
		this.cinsiyet = cinsiyet;
	};

}
