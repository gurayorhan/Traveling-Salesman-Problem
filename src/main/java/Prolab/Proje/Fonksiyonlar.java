package Prolab.Proje;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class Fonksiyonlar {
	
	public static double[][] excelVeriOku(double[][] veri) throws IOException {
    	FileInputStream fis=new FileInputStream(new File("excel/ilmesafe.xls"));  
    	HSSFWorkbook wb=new HSSFWorkbook(fis);   
    	HSSFSheet sheet=wb.getSheetAt(0);  
    	FormulaEvaluator formulaEvaluator=wb.getCreationHelper().createFormulaEvaluator();  
    	
    	int x = 0;
    	int y = -1;
    	
    	for(Row row: sheet)
    	{  
	    	for(Cell cell: row)
	    	{  
		    	switch(formulaEvaluator.evaluateInCell(cell).getCellType())  
		    	{  
			    	case Cell.CELL_TYPE_NUMERIC:
			    		veri[y][x] = cell.getNumericCellValue();
			    		x++;
			    		break;  
			    	case Cell.CELL_TYPE_STRING: 
			    		break;  
			    }  
	    	}  
	    	y++;
	    	x=0; 
    	}
    	
    	return veri;
    }
	
	@SuppressWarnings("resource")
	public static ArrayList<Sehir> txtVeriOku(ArrayList<Sehir> sehirler) throws IOException {
		
    	BufferedReader inputStream = null;
    	
 		inputStream = new BufferedReader(new FileReader("txt/sehirvekomsulari.txt"));

		String satir;
		while ((satir = inputStream.readLine()) != null) {
			Sehir yeni = new Sehir();
		    String[] result = satir.split("-");
		    yeni.sehirAdi = result[0];
		    yeni.sehirKodu = Integer.parseInt(result[1]);
		    yeni.sehirX = Integer.parseInt(result[2]);
		    yeni.sehirY = Integer.parseInt(result[3]);
		    for (int i = 4; i < result.length; i++) {
		    	yeni.komsular.add(Integer.parseInt(result[i]));
			}
		    sehirler.add(yeni);
		}
		
		return sehirler;
	}

	
	public static void haritaCiz(ArrayList<Sehir> sehirler,int no) {
		nu.pattern.OpenCV.loadShared();
    	Mat goruntuDizisi=new Mat();
    	goruntuDizisi=Imgcodecs.imread("harita/harita.jpg"); 
    	int x =0,y=0,il = 0;
    	for (int i = 0; i < sehirler.size(); i++) {
    		if(i!=0) {
    			if(sehirler.get(i-1) != sehirler.get(i)) {
    				Imgproc.putText(goruntuDizisi, Integer.toString(Sehir.sehirlerArasıMesafeGetir(Arayuz.veri, il, sehirler.get(i).sehirKodu))+"Km", new org.opencv.core.Point(((sehirler.get(i).sehirX+x)/2),((sehirler.get(i).sehirY)+y)/2), 1, 1.0, new Scalar(0 ,0,255),1);
    			}
    			
    			Imgproc.arrowedLine(goruntuDizisi, new org.opencv.core.Point(x,y), new org.opencv.core.Point(sehirler.get(i).sehirX,sehirler.get(i).sehirY),new Scalar(0 ,0,255),2,0,0,0.1);
    			Imgproc.circle(goruntuDizisi, new org.opencv.core.Point(sehirler.get(i).sehirX,sehirler.get(i).sehirY),2,new Scalar(0 ,0,0),5);
    		}
    		if(i != 0) {
    			if(sehirler.get(i-1) == sehirler.get(i)) {
        			Imgproc.circle(goruntuDizisi, new org.opencv.core.Point(sehirler.get(i).sehirX,sehirler.get(i).sehirY),5,new Scalar(0 ,0,255),10);
        			Imgproc.circle(goruntuDizisi, new org.opencv.core.Point(sehirler.get(i).sehirX,sehirler.get(i).sehirY),2,new Scalar(0 ,0,0),5);
    			}   		
    		}
    		else if(i == 0) {
    			Imgproc.putText(goruntuDizisi,"Merkez", new org.opencv.core.Point(sehirler.get(i).sehirX-30,sehirler.get(i).sehirY-20), 1, 1.3, new Scalar(0,0,255),2);
    			Imgproc.circle(goruntuDizisi, new org.opencv.core.Point(sehirler.get(i).sehirX,sehirler.get(i).sehirY),5,new Scalar(0 ,0,255),10);
    		}
    		x = sehirler.get(i).sehirX;
    		y = sehirler.get(i).sehirY;
    		il= sehirler.get(i).sehirKodu;
    	}
    	Imgcodecs.imwrite("harita/yeni-harita"+no+".jpg", goruntuDizisi);
	}
}
