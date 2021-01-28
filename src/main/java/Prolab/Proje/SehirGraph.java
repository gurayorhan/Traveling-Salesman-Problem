package Prolab.Proje;

import java.util.ArrayList;

public class SehirGraph {
	
	public int baslangic;
	public int[] mesafe = new int[81];
	public int[][] graph = new int[81][81];
	public int[][] sehirKod = new int[81][81];
	public int[][] sehirSonu = new int[81][81];
	public ArrayList<SehirList> graphIcınSehirListesi = new ArrayList<SehirList>();
	public boolean geldimmi;
	

	public static int minimumUzaklık(int[] mesafe, boolean[] ziyaretBilgisi)
	{
	   int min = Integer.MAX_VALUE, minIndex = 0;
	   for (int v = 0; v < 81; v++) {
	     if (ziyaretBilgisi[v] == false && mesafe[v] <= min) {
	    	 min = mesafe[v];
	    	 minIndex = v;
	     }
	   }
	   return minIndex;
	}
	
	
	public static SehirGraph enKisaYol(SehirGraph graph,int sehirSayisi)
	{
		
	    boolean[] ziyaretBilgisi = new boolean[sehirSayisi];
	 
	    for (int i = 0; i < sehirSayisi; i++) {
	    	graph.mesafe[i] = Integer.MAX_VALUE;
	    	ziyaretBilgisi[i] = false;
	    }
	    
	    graph.mesafe[graph.baslangic] = 0;


	    for (int count = 0; count < 80; count++)
	    {
	      int u = minimumUzaklık(graph.mesafe, ziyaretBilgisi);
	      
	      ziyaretBilgisi[u] = true;
	   
	      for (int v = 0; v < sehirSayisi; v++) {
	    	  if (!ziyaretBilgisi[v] && graph.graph[u][v]!= 0 && graph.mesafe[u] != Integer.MAX_VALUE && (graph.mesafe[u]+graph.graph[u][v]) < graph.mesafe[v]) {
	    		   graph.mesafe[v] = graph.mesafe[u] + graph.graph[u][v];
	    		   if(u == 0) {
	    			   graph.sehirSonu[u][v] = 82;
	    		   }
	    		   else {
	    			   graph.sehirSonu[u][v] = u;
	    		   }
	    	  }
	      }   
	    }
	    return graph;
	}
	
	public static SehirGraph graphTablosuOlustur(int baslangic,SehirGraph graph) {
		
		graph.baslangic = (baslangic-1);
		graph.geldimmi = false;
		
		for (int i = 0; i < Arayuz.sehirler.size(); i++) {
    		int komsuSayisi =  Arayuz.sehirler.get(i).komsular.size();
    		int komsuIndex = 0;
    		for (int k = 0; k < 81; k++) {
    			if(k == ( Arayuz.sehirler.get(i).komsular.get(komsuIndex)-1) && komsuSayisi > komsuIndex ) {
    				graph.graph[i][k] = Sehir.sehirlerArasıMesafeGetir( Arayuz.veri,  Arayuz.sehirler.get(i).sehirKodu,  Arayuz.sehirler.get(i).komsular.get(komsuIndex));
    				graph.sehirKod[i][k] =  Arayuz.sehirler.get(i).komsular.get(komsuIndex);
    				komsuIndex++;
    				if(komsuSayisi == komsuIndex) {
    					komsuSayisi = 20;
    					komsuIndex = 0;
    				}
    			}
    			else {
    				graph.graph[i][k] = 0;
    				graph.sehirKod[i][k] = 0;
    			}
			}
		}
		
		return SehirGraph.enKisaYol(graph,81);
		
	}
	
		public static ArrayList<SehirList> sehirleriSirala(SehirGraph graph,ArrayList<SehirList> graphIcınSehirListesi){
			
			for (int i = 0; i < Arayuz.veri.length; i++) {
	    		SehirList yeniList = new SehirList();
	    		Sehir yeniSehir;
	    		int deger = i;
				yeniSehir = Sehir.sehirGetir(Arayuz.sehirler, (deger+1));
				yeniList.sehirSirasi.add(yeniSehir);
	    		while(deger != graph.baslangic) {
	        	    for (int j = 0; j < 81; j++) {
	        	    	if(graph.sehirSonu[j][deger] != 0) {
	        	    		if(graph.sehirSonu[j][deger] == 82) {
	        	    			Sehir yeniSehir1;
	            	    		deger = 0;
	            	    		yeniSehir1 = Sehir.sehirGetir(Arayuz.sehirler, (deger+1));
	            				yeniList.sehirSirasi.add(yeniSehir1);
	            	    		break;
	        	    		}
	        	    		else {
	        	    			Sehir yeniSehir1;
	            	    		deger = graph.sehirSonu[j][deger];
	            	    		yeniSehir1 = Sehir.sehirGetir(Arayuz.sehirler, (deger+1));
	            				yeniList.sehirSirasi.add(yeniSehir1);
	            	    		break;
	        	    		}
	        	    	}
	        	    }
	    		}
	    		graphIcınSehirListesi.add(yeniList);
			}
			
			return graphIcınSehirListesi;
		}
	
	
}
