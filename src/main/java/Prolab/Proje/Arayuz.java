package Prolab.Proje;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.*;

@SuppressWarnings("serial")
public class Arayuz extends JFrame
{
	static ArrayList<Sehir> sehirler = new ArrayList<Sehir>();
	static double[][] veri = new double[81][81];
	static int toplamCikti = 0,indexNo = 0,enYuksekDeger = 50000;
	static JButton basla,ileri,geri,sil,rastgeleSecim; 
	static JLabel bekleme,iller;
	static JTextArea SonucText;
	static JFrame  frame = new JFrame("Harita");
	static ArrayList<JLabel> fotolar = new ArrayList<JLabel>(); 
	static JPanel panel; 
	static int no = 0;
	static int labelIndex = 0;
	static ArrayList<JCheckBox> sehirCheck = new ArrayList<JCheckBox>(); 
	static ArrayList<SehirGraph> graphs = new ArrayList<SehirGraph>();
	static ArrayList<Sonuc> ilkBesSonuc = new ArrayList<Sonuc>();
	
	
	public static void main( String[] args ) throws IOException, InterruptedException
    {
    	sehirler = Fonksiyonlar.txtVeriOku(sehirler);
    	veri = Fonksiyonlar.excelVeriOku(veri);
    	GUI();
    }
	
	public static void GUI() {
		
		frame.setSize(1920,1080);
		basla = new JButton("Çalıştır"); 
		geri = new JButton("Önceki Harita"); 
		iller  = new JLabel("İller");
		ileri = new JButton("Sonraki Harita"); 
		sil = new JButton("Seçimi Temizle"); 
		rastgeleSecim = new JButton("Rastgele Seçim"); 
		panel = new JPanel();
		bekleme = new JLabel(); 
		SonucText = new JTextArea();
		rastgeleSecim.setBounds(1450, 825, 220, 80);
		iller.setBounds(100, 20, 100, 10);
		sil.setBounds(1450, 910, 220, 80);
        geri.setBounds(1718, 375, 150, 40);
        ileri.setBounds(1718, 425, 150, 40);
        basla.setBounds(1450, 740, 220, 80);
		SonucText.setBounds(230, 735, 1200, 260);
		ImageIcon i = new ImageIcon("harita/harita.jpg");
		
		int degerx = 40,degerY=10;
		
		for(int t = 0; t< 81;t++) {
			if(t == 41) {
				degerY+=100;
				degerx = 40;
			}
			if(sehirler.get(t).sehirKodu != 41) {
				JCheckBox checkBox1 = new JCheckBox();
				checkBox1.setText(sehirler.get(t).sehirAdi);
				checkBox1.setBounds(degerY,degerx, 100,20); 
				degerx = degerx +23;
				sehirCheck.add(checkBox1);
				if(t> 40) {
					frame.add(sehirCheck.get(t-1));
				}else {
					frame.add(sehirCheck.get(t));
				}
				
			}
		}
        
		bekleme.setIcon(i);
		panel.add(bekleme);
        frame.add(geri); 
        frame.add(sil);
        frame.add(basla); 
        frame.add(ileri); 
        frame.add(SonucText);
        frame.add(iller);
        frame.add(rastgeleSecim);
		frame.add(panel);
		
		SonucText.append("  İlleri seçerek çalıştır butonuna basınız. \n ------------------------------------------------------ \n - En az 1 En Fazla 10 ile Seçebilirsiniz.\n - İkinci Kez Çalıştırmak istiyorsannız lütfen Tekrar başlatın.\n - Rastgele Seçim Butonu rastgele il seçimi gerçekleştirir. \n----------------------------------------------------------------------------------------------\n");
		frame.setVisible(true);
		geri.setEnabled(false);
		ileri.setEnabled(false);
		basla.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ArrayList<Sehir> aranacakSehirler = new ArrayList<Sehir>();
				for(int o = 0; o<80;o++) {
					if(sehirCheck.get(o).isSelected()) {
						System.out.println(sehirCheck.get(o).getText());
						aranacakSehirler.add(Sehir.sehirGetirText(sehirler,sehirCheck.get(o).getText()));
					}
				}
		
				if(aranacakSehirler.size() > 10 || aranacakSehirler.size() == 0) {
					SonucText.append("İşlem Başarısız. Girilen Şehir Sayisi 0 yada 10 dan Fazla.\n");
				}else {
					
					for(int o = 0; o<80;o++) {
						sehirCheck.get(o).setEnabled(false);
					
					}
				sil.setEnabled(false);
				rastgeleSecim.setEnabled(false);
				ileri.setEnabled(true);
				panel.remove(bekleme);
				basla.setEnabled(false);
				System.out.println(aranacakSehirler.size());
				int[] girilenSehirKodlari = new int[aranacakSehirler.size()];
				for (int j = 0; j < girilenSehirKodlari.length; j++) {
					girilenSehirKodlari[j] = aranacakSehirler.get(j).sehirKodu;
				}
				
				
				for (int i = 0; i < (aranacakSehirler.size()+1); i++) {
					if(i == 0) {
						SehirGraph graph = new SehirGraph();
				    	graph = SehirGraph.graphTablosuOlustur(41, graph);
				    	graph.graphIcınSehirListesi = SehirGraph.sehirleriSirala(graph, graph.graphIcınSehirListesi);
						graphs.add(graph);
					}else {
						SehirGraph graph = new SehirGraph();
				    	graph = SehirGraph.graphTablosuOlustur(aranacakSehirler.get(i-1).sehirKodu, graph);
				    	graph.graphIcınSehirListesi = SehirGraph.sehirleriSirala(graph, graph.graphIcınSehirListesi);
						graphs.add(graph);
					}
				}
				
				try {
					File dosya = new File("txt/bulunanSonuclar.txt");
					FileWriter dosyaYaz = new FileWriter(dosya);
					BufferedWriter DosYaz = new BufferedWriter(dosyaYaz);
					Sonuc.permutasyon(aranacakSehirler.size(), girilenSehirKodlari,DosYaz);
					DosYaz.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				
				Sonuc.sirala();
				
				for (int j = 0; j < ilkBesSonuc.size(); j++) {
					Fonksiyonlar.haritaCiz(ilkBesSonuc.get(j).sehirCizimListesi, (j+1));
				}
				
				String text = Guzergah.guzergahTextOlustur(ilkBesSonuc.get(no).sehirCizimListesi)+" "+ilkBesSonuc.get(no).toplamMesafe+"\n";
				ImageIcon i = new ImageIcon("harita/yeni-harita1.jpg");
				JLabel x1 = new JLabel();
				x1.setIcon(i);
		    	fotolar.add(x1);
		    	SonucText.setText(text);
		    	fotolar.get(no).setIcon(i);
		    	panel.add(fotolar.get(no));
		    	frame.invalidate();
		    	frame.validate();
		    	frame.repaint();
		    	
			}
			}
		});
		
		sil.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SonucText.setText(" ");
				SonucText.append(" İlleri seçerek çalıştır butonuna basınız. \n ------------------------------------------------------ \n - En az 1 En Fazla 10 ile Seçebilirsiniz.\n - İkinci Kez Çalıştırmak istiyorsannız lütfen Tekrar başlatın.\n - Rastgele Seçim Butonu rastgele il seçimi gerçekleştirir. \n----------------------------------------------------------------------------------------------\n");
				for(int o = 0; o<80;o++) {
					if(sehirCheck.get(o).isSelected()) {
						sehirCheck.get(o).setSelected(false);
					}
				}
			}
		});
		
		rastgeleSecim.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for(int o = 0; o<80;o++) {
					if(sehirCheck.get(o).isSelected()) {
						sehirCheck.get(o).setSelected(false);
					}
				}
				Random r=new Random();
				int j=0,i;
				i = r.nextInt(6);
				i+=5;
				while(j != i) {
					int yeniSayi=r.nextInt(80);
					if(!sehirCheck.get(yeniSayi).isSelected()) {
						sehirCheck.get(yeniSayi).setSelected(true);
						j++;
					}
				}
				
				
			}
		});
		
		ileri.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(no<5) {
					if(no == 3) {
						ileri.setEnabled(false);
					}
					if(no == 4) {
			    		no = 4;
			    		labelIndex++;
			    	}else {
						geri.setEnabled(true);
			    		no++;
			    		labelIndex++;
			    	}
					String text = Guzergah.guzergahTextOlustur(ilkBesSonuc.get(no).sehirCizimListesi)+" "+ilkBesSonuc.get(no).toplamMesafe+"\n";
					ImageIcon i = new ImageIcon("harita/yeni-harita"+(no+1)+".jpg");
					JLabel yeni = new JLabel();
					yeni.setIcon(i);
			    	fotolar.add(yeni);
			    	if(labelIndex != 0) {
			    		panel.remove(fotolar.get(labelIndex-1));
			    	}
			    	SonucText.append(text);
			    	fotolar.get(labelIndex).setIcon(i);
			    	panel.add(fotolar.get(labelIndex));
			    	frame.invalidate();
			    	frame.validate();
			    	frame.repaint();
				}
			}
		});
		
		geri.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(no>=0) {
					if(no == 1) {
						geri.setEnabled(false);
					}
					if(no == 0) {
			    		no = 0;
			    		labelIndex++;
			    	}else {
						ileri.setEnabled(true);
			    		labelIndex++;
			    		no--;
			    	}
					String text = Guzergah.guzergahTextOlustur(ilkBesSonuc.get(no).sehirCizimListesi)+" "+ilkBesSonuc.get(no).toplamMesafe+"\n";
					ImageIcon i = new ImageIcon("harita/yeni-harita"+(no+1)+".jpg");
					JLabel yeni = new JLabel();
					yeni.setIcon(i);
			    	fotolar.add(yeni);
			    	if(labelIndex != 0) {
			    		panel.remove(fotolar.get(labelIndex-1));
			    	}
			    	SonucText.append(text);
			    	fotolar.get(labelIndex).setIcon(i);
			    	panel.add(fotolar.get(labelIndex));
			    	frame.invalidate();
			    	frame.validate();
			    	frame.repaint();
			    	
				}
			}
		});
	}

}  
