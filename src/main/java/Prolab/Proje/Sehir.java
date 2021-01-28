package Prolab.Proje;

import java.util.ArrayList;

public class Sehir {
	
	public String sehirAdi;
	public int sehirKodu;
	public int sehirX;
	public int sehirY;
	public ArrayList<Integer> komsular = new ArrayList<Integer>(); 
	
	public static Sehir sehirGetir(ArrayList<Sehir> sehirler,int sehirKodu) {
		for (int i = 0; i < sehirler.size(); i++) {
			if(sehirler.get(i).sehirKodu == sehirKodu) {
				return sehirler.get(i);
			}
		}
		return null;
	}
	
	public static Sehir sehirGetirText(ArrayList<Sehir> sehirler,String sehirAdi) {
		for (int i = 0; i < sehirler.size(); i++) {
			
			if(sehirler.get(i).sehirAdi.equals(sehirAdi.trim())) {
				return sehirler.get(i);
			}
		}
		return null;
	}
	
	public static int sehirlerArasıMesafeGetir(double[][] veri,int suankiIlKodu,int gidilcekIlKodu) {
		return (int)veri[suankiIlKodu-1][gidilcekIlKodu-1];
	}
	
}
