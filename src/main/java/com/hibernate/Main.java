package com.hibernate;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Main {

	public static void main(String[] args) {

		Main m = new Main();
		SessionFactory sf = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(ogrenciler.class)
				.buildSessionFactory();

		Session session = sf.getCurrentSession();

		try {

			session.beginTransaction();
			
			// Normal şekilde listeleyelim
			System.out.println("Listeleme");
			List<ogrenciler> ogr = session.createQuery("from ogrenciler").getResultList();
			m.listele(ogr);

			// Koşullu listeleyelim (örnekler yapalım)

			// 1. koşul, okulNumarası 10'dan küçük olanlar

			System.out.println("---------1. koşul--------------------");
			String sorgu = "FROM ogrenciler o WHERE o.okulNo < '10'";
			m.kosulluSorguListele(session, sorgu);

			// 2. koşul, okulNumarası 10'dan küçük olanlar ve 11'e eşit olanlar
			System.out.println("---------2. koşul--------------------");
			sorgu = "FROM ogrenciler o WHERE o.okulNo < '10' or o.okulNo = '11'";
			m.kosulluSorguListele(session, sorgu);

			// 3. koşul, çinde a harfi gecen ogrenci adlarını sırala
			System.out.println("---------3. koşul--------------------");
			sorgu = "FROM ogrenciler o WHERE o.ad like '%a%'";
			m.kosulluSorguListele(session, sorgu);
			
			// Orderby -> sayıları küçükten büyüğe yada stringleri alfabetik sıralama işlemi
			
			// okulNo küçükten büyüğe
			System.out.println("-------------okul no küçükten büyüğe sıralama---------------------");
			sorgu = "from ogrenciler o ORDER BY o.okulNo";
			m.kosulluSorguListele(session, sorgu);
			
			// isimleri alfabetik sıralama
			System.out.println("-----------------isimleri alfabetik sıralama------------------");
			sorgu = "from ogrenciler o ORDER BY o.ad";
			m.kosulluSorguListele(session, sorgu);
			
			// DEC by ile de tersi yapılır.
			
			
			// Group by ID
			
			//Öğrencileri ve kızları gruplayıp, dağılımlarını inceleyelim
			sorgu = "SELECT o.cinsiyet FROM ogrenciler o GROUP BY o.ad";
			List<String> cinsiyetler = session.createQuery(sorgu).getResultList();
			
			// Toplam öğrenci sayısı
			System.out.println("Toplam öğrenci sayısı: " + cinsiyetler.size());
			
			// Cinsiyet dağılımı
			
			
			System.out.println("Cinsiyet dağılımı");
			int erkekSayisi = 0;
			for(String cinsiyet: cinsiyetler) {
				
				if(cinsiyet.equals("erkek")) {
					erkekSayisi ++;
				}
				
			}
			int kizSayisi = cinsiyetler.size() - erkekSayisi;
			System.out.println("Erkek öğrenci sayisi= " + erkekSayisi);
			System.out.println("Kız öğrenci sayisi= " + kizSayisi);
			
			
			// Yeni öğrenci ekleyelim
			
			//m.ogrenciEkle(session,"Cevahir", "Pınar", "23", "kiz");
			
		
			// Öğrenci bilgilerini düzenleyelim
			
			ogrenciler ogrenci = session.get(ogrenciler.class, 11);
			ogrenci.setAd("Veli");
			session.update(ogrenci);
		
			// Öğrenci silme
			
			// null point exception hatası olabilir, try catch eklenebilir
			ogrenci = session.get(ogrenciler.class, 11);
			session.delete(ogrenci);
			System.out.println("veri silindi");
				
			
			
			

			session.getTransaction().commit();;

		} finally {
			sf.close();
		}

	}

	void listele(List<ogrenciler> ogr) {

		
		
		for (ogrenciler ogrenci : ogr) {

			System.out.println("Öğrencinin");
			System.out.println("okul numarası: " + ogrenci.getOkulNo());
			System.out.println("adı: " + ogrenci.getAd());
			System.out.println("soyadı: " + ogrenci.getSoyad());
			System.out.println("cinsiyeti: " + ogrenci.getCinsiyet());
			System.out.println();
		}

	}

	void kosulluSorguListele(Session s, String sorgu) {
		
		List<ogrenciler> ogr = s.createQuery(sorgu).getResultList();

		listele(ogr);
	}
	
	void ogrenciEkle(Session s,String ad, String soyad, String okulNo, String cinsiyet) {
		ogrenciler ogrenci = new ogrenciler();
		ogrenci.setAd(ad);
		ogrenci.setSoyad(soyad);
		ogrenci.setOkulNo(okulNo);
		ogrenci.setCinsiyet(cinsiyet);
		s.save(ogrenci);
		s.close();
		
		
		
		System.out.println("öğrenci eklendi");
		
		
	}

}
