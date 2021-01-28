package Prolab.Proje;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Sonuc {
	
	int toplamMesafe;
	ArrayList<Integer> sira = new ArrayList<Integer>();
	ArrayList<Sehir> sehirCizimListesi = new ArrayList<Sehir>();
	
	public static void sonuclariYaz(ArrayList<SehirGraph> graphs,Sonuc sonuc, BufferedWriter DosYaz) {
		try{
			
			sonuc.sehirCizimListesi = Guzergah.cizimGuzargahiOlustur(sonuc.sehirCizimListesi,sonuc.sira,graphs);
			sonuc.toplamMesafe =  Guzergah.toplamMesafeHesapla(sonuc.sehirCizimListesi);
			
			
			if(sonuc.toplamMesafe < Arayuz.enYuksekDeger) {
				
				Arayuz.enYuksekDeger = sonuc.toplamMesafe+400;
				
				Arayuz.toplamCikti++;
				DosYaz.write(Arayuz.indexNo+"-");
				for (int k = 0; k < sonuc.sehirCizimListesi.size(); k++) {
					DosYaz.write(sonuc.sehirCizimListesi.get(k).sehirKodu+"-");
				}
				
				DosYaz.write(sonuc.toplamMesafe+"");
				DosYaz.newLine();
			}

		}catch (IOException t) {
			System.out.println(t);
		}
	}
	
	public static void sirala(){
		try {
			int i = 0,devam = 0,index;
			if(Arayuz.toplamCikti < 1000) {
				index = Arayuz.toplamCikti;
			}else {
				index = 1000;
			}
			boolean kapa = true,sirala = false,yeniDegerAl = false;
			int[] sonucMesafe = new int[index];
			String[] sonucText = new String[index];
			File dosya = new File("txt/bulunanSonuclar.txt");
			File dosyaYaz = new File("txt/enKisaSiraliSonuclar.txt");
			if(Arayuz.toplamCikti > 999) {
				try(BufferedReader inputStream = new BufferedReader(new FileReader(dosya))){
					String satir;
					while (true) {
						if((satir = inputStream.readLine()) != null) {
							if(kapa) {
								sonucText[i] = satir;
							    String[] result = satir.split("-");
							    sonucMesafe[i] =  Integer.parseInt(result[result.length-1]);		    
							    i++;
							}
							
						    if(i == 1000 && devam == 0) {
						    	kapa = false;
						    	devam = 1;
						    	sirala = true;
						    }
						    
						    if(devam == 1 && sirala) {
						    	for (int k = 0; k < 1000-1; k++) 
						        { 
						            int minimumIndex = k; 
						            for (int j = k+1; j < 1000; j++) 
						                if (sonucMesafe[j] < sonucMesafe[minimumIndex]) 
						                    minimumIndex = j; 

						            int tempMesafe = sonucMesafe[minimumIndex]; 
						            String textTemp = sonucText[minimumIndex];
						            sonucMesafe[minimumIndex] = sonucMesafe[k]; 
						            sonucText[minimumIndex] = sonucText[k]; 
						            sonucMesafe[k] = tempMesafe; 
						            sonucText[k] = textTemp; 
						        } 
						    	yeniDegerAl = true;
						    }
						    
						    if(yeniDegerAl) {
						    	sonucText[499+devam] = satir;
							    String[] result = satir.split("-");
							    sonucMesafe[499+devam] =  Integer.parseInt(result[result.length-1]);	
						    	devam++;
						    	if(devam == 500) {
						    		yeniDegerAl = false;
						    		devam = 0;
						    	}
						    }
						}else {
							for (int k = 0; k < 1000-1; k++) 
					        { 
					            int minimumIndex = k; 
					            for (int j = k+1; j < 1000; j++) 
					                if (sonucMesafe[j] < sonucMesafe[minimumIndex]) 
					                    minimumIndex = j; 

					            int tempMesafe = sonucMesafe[minimumIndex]; 
					            String textTemp = sonucText[minimumIndex];
					            sonucMesafe[minimumIndex] = sonucMesafe[k]; 
					            sonucText[minimumIndex] = sonucText[k]; 
					            sonucMesafe[k] = tempMesafe; 
					            sonucText[k] = textTemp; 
					        } 
							break;
						}
						
					}
				}catch (Exception e) {
					System.out.println(e);
				}
			}else {
				try(BufferedReader inputStream = new BufferedReader(new FileReader(dosya))){
					String satir;
					while((satir = inputStream.readLine()) != null) {
						sonucText[i] = satir;
					    String[] result = satir.split("-");
					    sonucMesafe[i] =  Integer.parseInt(result[result.length-1]);		    
					    i++;
					}
				}catch (Exception e) {
					System.out.println(e);
				}
					
				for (int k = 0; k < Arayuz.toplamCikti-1; k++) 
		        { 
		            int minimumIndex = k; 
		            for (int j = k+1; j < Arayuz.toplamCikti; j++) 
		                if (sonucMesafe[j] < sonucMesafe[minimumIndex]) 
		                    minimumIndex = j; 

		            int tempMesafe = sonucMesafe[minimumIndex]; 
		            String textTemp = sonucText[minimumIndex];
		            sonucMesafe[minimumIndex] = sonucMesafe[k]; 
		            sonucText[minimumIndex] = sonucText[k]; 
		            sonucMesafe[k] = tempMesafe; 
		            sonucText[k] = textTemp; 
		        } 
			}
			int v = 0;
			try(BufferedWriter DosYaz = new BufferedWriter(new FileWriter(dosyaYaz))){
				for (int j = 0; j < sonucText.length; j++) {
					DosYaz.write(sonucText[j]);
		            DosYaz.newLine();
		            v++;
				}
				
			}catch (Exception e) {
				System.out.println(e);
			}
			if(v>5) {
				for(int i1= 0;i1<5;i1++) {
					Sonuc x = new Sonuc();
					String[] result = sonucText[i1].split("-");
					x.toplamMesafe = Integer.parseInt(result[result.length-1]);
					for(int i2 = 1;i2<result.length-1;i2++) {
						Sehir t = new Sehir();
						t = Sehir.sehirGetir(Arayuz.sehirler, Integer.parseInt(result[i2]));
						x.sehirCizimListesi.add(t);
					}
					System.out.println("Toplma Mesafe: "+x.toplamMesafe +"Yol: "+sonucText[i1]);
					Arayuz.ilkBesSonuc.add(x);
				}
			}else {
				Sonuc yedek = new Sonuc();
				for(int i1= 0;i1<v;i1++) {
					Sonuc x = new Sonuc();
					String[] result = sonucText[i1].split("-");
					x.toplamMesafe = Integer.parseInt(result[result.length-1]);
					for(int i2 = 1;i2<result.length-1;i2++) {
						Sehir t = new Sehir();
						t = Sehir.sehirGetir(Arayuz.sehirler, Integer.parseInt(result[i2]));
						x.sehirCizimListesi.add(t);
					}
					System.out.println("Toplma Mesafe: "+x.toplamMesafe +"Yol: "+sonucText[i1]);
					Arayuz.ilkBesSonuc.add(x);
				}
				yedek = Arayuz.ilkBesSonuc.get(Arayuz.ilkBesSonuc.size()-1);
				for (int j = v; j < 5; j++) {
					Arayuz.ilkBesSonuc.add(yedek);
				}
			}

		}catch(Exception e) {
			
		}
	}

	public static void permutasyon(
			  int n, int[] elements, BufferedWriter dosYaz) {
			 
			    if(n == 1) {
			    	sonucYerlestir(elements,dosYaz);
			    } else {
			        for(int i = 0; i < n-1; i++) {
			        	permutasyon(n - 1, elements,dosYaz);
			            if(n % 2 == 0) {
			            	takas(elements, i, n-1);
			            } else {
			            	takas(elements, 0, n-1);
			            }
			 }
			 permutasyon(n - 1, elements,dosYaz);
		}
	}
	
	private static void takas(int[] input, int a, int b) {
		int tmp = input[a];
	    input[a] = input[b];
	    input[b] = tmp;
	}
	
	private static void sonucYerlestir(int[] input, BufferedWriter dosYaz) {

		Sonuc yeni = new Sonuc();
		yeni.sira.add(40);
		for(int i = 0; i < input.length; i++) {
		    yeni.sira.add((input[i]-1));
		}
		yeni.sira.add(40);
		Arayuz.indexNo++;
		sonuclariYaz(Arayuz.graphs,yeni,dosYaz);
		
	}
	
}
