package Prolab.Proje;

import java.util.ArrayList;

public class Guzergah {
	
	public static int toplamMesafeHesapla(ArrayList<Sehir> sehirCizimListesi ) {
		int mesafe = 0;
		for (int i = 1; i < sehirCizimListesi.size(); i++) {
			mesafe = mesafe + Sehir.sehirlerArasıMesafeGetir(Arayuz.veri, sehirCizimListesi.get(i-1).sehirKodu, sehirCizimListesi.get(i).sehirKodu);
		}
		return mesafe;
	}
	
	//listeki olan il kodlarını sehirlerini bularak listeler
	public static ArrayList<Sehir> cizimGuzargahiOlustur(ArrayList<Sehir> sehirCizimListesi,ArrayList<Integer> list,ArrayList<SehirGraph> graphs){
		
		for (int j = 0; j < (list.size()-1); j++) {
			
			SehirGraph graphBaslangic= null;
			SehirGraph graphBitis = null;
			
			for (int j2 = 0; j2 < graphs.size(); j2++) {
				if(graphs.get(j2).baslangic == list.get(j)) {
					graphBaslangic = graphs.get(j2);
				}
				else if(graphs.get(j2).baslangic == list.get(j+1)) {
					graphBitis = graphs.get(j2);
				}
			}
			
			for (int i = 0; i < graphBaslangic.graphIcınSehirListesi.size(); i++) {
				if(i == graphBitis.baslangic) {
					for (int k = graphBaslangic.graphIcınSehirListesi.get(i).sehirSirasi.size()-1; k > -1; k--) {
						sehirCizimListesi.add(graphBaslangic.graphIcınSehirListesi.get(i).sehirSirasi.get(k));
					}
				}
			}
		}
		return sehirCizimListesi;
	}
	
	public static String guzergahTextOlustur(ArrayList<Sehir> sehirCizimListesi) {
		String text = "Kocaeli ->";
		int j = 0;
		j++;
		for (int i = 1; i < sehirCizimListesi.size(); i++) {
			if(!Sehir.sehirGetir(Arayuz.sehirler, sehirCizimListesi.get(i).sehirKodu).sehirAdi.equals(Sehir.sehirGetir(Arayuz.sehirler, sehirCizimListesi.get(i-1).sehirKodu).sehirAdi)) {
				text = text + Sehir.sehirGetir(Arayuz.sehirler, sehirCizimListesi.get(i).sehirKodu).sehirAdi +"-> ";
				j++;
			}
			if(j > 16) {
				j = 0;
				text = text +  "\n";
			}
		}
		text =  text + "\n Toplam Mesafe:"; 
		return text;
	}
}
