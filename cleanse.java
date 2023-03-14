import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
public class cleanse {

	public static void main(String[] args) {
		
		ArrayList<ArrayList<String>> rowArray = new ArrayList<ArrayList<String>>();
		ArrayList<Double> rowMeans = new ArrayList<Double>();
		int rowNo = 0;
		double row1Total = 0;
		double row2Total = 0;
		double row3Total = 0;
		double row4Total = 0;
		double row5Total = 0;
		double row6Total = 0;
		double row7Total = 0;
		boolean skip = false;
		try  
		{  
		//the file to be opened for reading  
		FileInputStream fis=new FileInputStream("C:\\Users\\lat14\\OneDrive - Loughborough University\\Desktop\\Ai methods cw\\dataset.txt");       
		Scanner sc=new Scanner(fis);    //file to be scanned  
		//returns true if there is another line to read 
		
		
		while((sc.hasNextLine()))  
		{  
		
		String s = sc.nextLine();
		
		ArrayList<String> rowList = new ArrayList<String>();
		
		rowList.addAll(Arrays.asList(s.split("\\s+")));
		for (String val : rowList) { 
			if (!(val.matches("^-?[0-9]\\d*(\\.\\d+)?$"))) {
				System.out.print(rowNo);System.out.print("  ");
				System.out.println(rowList);
				skip = true;
			}		      
	           		
	      }
		if (skip) {
			skip = false;
			rowNo++;
			continue;
		}
		if (rowList.size() != 7) {
			
			System.out.print(rowNo);System.out.print("  ");
			System.out.println(rowList);
			continue;
		}
		rowNo++;
		//returns the line that was skipped  
		row1Total += Double.parseDouble(rowList.get(0));
		row2Total += Double.parseDouble(rowList.get(1));
		row3Total += Double.parseDouble(rowList.get(2));
		row4Total += Double.parseDouble(rowList.get(3));
		row5Total += Double.parseDouble(rowList.get(4));
		row6Total += Double.parseDouble(rowList.get(5));
		row7Total += Double.parseDouble(rowList.get(6));
		rowArray.add(rowList);
		}  
		
		double rows = rowArray.size();
		System.out.println("");
		
		rowMeans.add(row2Total / rows);
		rowMeans.add(row3Total / rows);
		rowMeans.add(row4Total / rows);
		rowMeans.add(row5Total / rows);
		rowMeans.add(row6Total / rows);
		rowMeans.add(row7Total / rows);
		sc.close();     //closes the scanner  
		}  
		catch(IOException e)  
		{  
		e.printStackTrace();  
		}
		ArrayList<Double> sds = new ArrayList<Double>();
		double sumsquared2 = 0.0;
		double sumsquared3 = 0.0;
		double sumsquared4 = 0.0;
		double sumsquared5 = 0.0;
		double sumsquared6 = 0.0;
		double sumsquared7 = 0.0;
		
		for (ArrayList<String> row: rowArray) {
			sumsquared2+= Math.pow((Double.parseDouble(row.get(1)) - rowMeans.get(0)), 2);
			sumsquared3+= (Math.pow((Double.parseDouble(row.get(2)) - rowMeans.get(1)), 2));
			sumsquared4+= (Math.pow((Double.parseDouble(row.get(3)) - rowMeans.get(2)), 2));
			sumsquared5+= (Math.pow((Double.parseDouble(row.get(4)) - rowMeans.get(3)), 2));
			sumsquared6+= (Math.pow((Double.parseDouble(row.get(5)) - rowMeans.get(4)), 2));
			sumsquared7+= (Math.pow((Double.parseDouble(row.get(6)) - rowMeans.get(5)), 2));
		}
		
		double sd1 = (Math.sqrt(sumsquared2 / rowNo));
		System.out.println(rowMeans.get(0) + (2*sd1));
		System.out.println(rowMeans.get(0) - (2*sd1));
		System.out.println();
		double sd2 = (Math.sqrt(sumsquared3 / rowNo));
		System.out.println(rowMeans.get(1) + (2*sd2));
		System.out.println(rowMeans.get(1) - (2*sd2));
		System.out.println();
		double sd3 = (Math.sqrt(sumsquared4 / rowNo));
		System.out.println(rowMeans.get(2) + (2*sd3));
		System.out.println(rowMeans.get(2) - (2*sd3));
		System.out.println();
		double sd4 = (Math.sqrt(sumsquared5 / rowNo));
		System.out.println(rowMeans.get(3) + (2*sd4));
		System.out.println(rowMeans.get(3) - (2*sd4));
		System.out.println();
		double sd5 = (Math.sqrt(sumsquared6 / rowNo));
		System.out.println(rowMeans.get(4) + (2*sd5));
		System.out.println(rowMeans.get(4) - (2*sd5));
		System.out.println();
		double sd6 = (Math.sqrt(sumsquared7 / rowNo));
		System.out.println(rowMeans.get(5) + (2*sd6));
		System.out.println(rowMeans.get(5) - (2*sd6));
		System.out.println();
		
		rowNo = 0;
		ArrayList<ArrayList<String>> toRemove = new ArrayList<ArrayList<String>>();
		for (ArrayList<String> row: rowArray) {
			
			if ((Double.parseDouble(row.get(1)) > rowMeans.get(0) + (3*sd1)) || (Double.parseDouble(row.get(1)) < rowMeans.get(0) - (3*sd1))) {
				System.out.println(row);
				toRemove.add(row);
				System.out.println("col1");
				System.out.println(rowNo);
				rowNo++;
				continue;
			}
			if ((Double.parseDouble(row.get(2)) > rowMeans.get(1) + (3*sd2)) || (Double.parseDouble(row.get(2)) < rowMeans.get(1) - (3*sd2))) {
				System.out.println(row);
				toRemove.add(row);
				System.out.println("col2");
				System.out.println(rowNo);
				rowNo++;
				continue;
			}
			if ((Double.parseDouble(row.get(3)) > rowMeans.get(2) + (3*sd3)) || (Double.parseDouble(row.get(3)) < rowMeans.get(2) - (3*sd3))) {
				System.out.println(row);
				toRemove.add(row);
				System.out.println("col3");
				System.out.println(rowNo);
				rowNo++;
				continue;
			}
			if ((Double.parseDouble(row.get(4)) > rowMeans.get(3) + (3*sd4)) || (Double.parseDouble(row.get(4)) < rowMeans.get(3) - (3*sd4))) {
				System.out.println(row);
				toRemove.add(row);
				System.out.println("col4");
				System.out.println(rowNo);
				rowNo++;
				continue;
			}
			if ((Double.parseDouble(row.get(5)) > rowMeans.get(4) + (3*sd5)) || (Double.parseDouble(row.get(5)) < rowMeans.get(4) - (3*sd5))) {
				System.out.println(row);
				toRemove.add(row);
				System.out.println("col5");
				System.out.println(rowNo);
				rowNo++;
				continue;
			}
			if ((Double.parseDouble(row.get(6)) > rowMeans.get(5) + (3*sd6)) || (Double.parseDouble(row.get(6)) < rowMeans.get(5) - (3*sd6))) {
				System.out.println(row);
				toRemove.add(row);
				System.out.println("col6");
				System.out.println(rowNo);
				rowNo++;
				continue;
			}
			
			rowNo++;
		}
		
		rowArray.removeAll(toRemove);
		
		System.out.println(rowArray.size());
		
		
		ArrayList<ArrayList<String>> train = new ArrayList<ArrayList<String>>();
		ArrayList<ArrayList<String>> validation = new ArrayList<ArrayList<String>>();
		ArrayList<ArrayList<String>> test = new ArrayList<ArrayList<String>>();
		double originalSize = rowArray.size();
		System.out.println();
		System.out.println(originalSize * 0.6);
		while (rowArray.size() >  originalSize * 0.4) {
			int indx = (int) (Math.random() * rowArray.size());
		
			train.add(rowArray.get(indx));
			rowArray.remove(rowArray.get(indx));
			
		}
		System.out.println(train);
		System.out.println(train.size());
		
	    int newSize = rowArray.size();
		while (rowArray.size() >  newSize * 0.5) {
			int indx = (int) (Math.random() * rowArray.size());
			
			validation.add(rowArray.get(indx));
			rowArray.remove(rowArray.get(indx));
			
		}
		System.out.println(validation);
		System.out.println(validation.size());
		
		test.addAll(rowArray);
		System.out.println(test);
		System.out.println(test.size());
		
		sendToFile(train, "trainData.txt");
		sendToFile(validation, "validationData.txt");
		sendToFile(test, "testData.txt");
		
		train = standardise(train);
		validation = standardise(validation);
		test = standardise(test);
		
		sendToFile(train, "standardisedTrainData.txt");
		sendToFile(validation, "standardisedValidationData.txt");
		sendToFile(test, "standardisedTestData.txt");
	}
	
	private static ArrayList<ArrayList<String>> standardise(ArrayList<ArrayList<String>> array) {
		for (int x = 1; x < 7; x++) {
			double min = 100000;
			double max = -100000;
			for (ArrayList<String> row: array ) {
				if (Double.parseDouble(row.get(x)) < min) {
					min = Double.parseDouble(row.get(x));
				}
				if (Double.parseDouble(row.get(x)) > max) {
					max  = Double.parseDouble(row.get(x));
				}
			}
			for (ArrayList<String> row: array ) {
				double standardised = (0.8 * ((Double.parseDouble(row.get(x)) - min) / (max - min))) + 0.1;
				row.set(x, Double.toString(to2Dp(standardised)));
			}
		}
		return array;
	}

	private static double to2Dp(double valueToRound) {
		return Math.round(valueToRound * 100)/ 100.0;
		
	}

	public static void sendToFile(ArrayList<ArrayList<String>> list, String fileName) {
		
			File myObj = new File(fileName);
			try {
				FileWriter myWriter = new FileWriter(fileName);
				for (ArrayList<String> row: list) {
					for (String cell: row) {
						myWriter.write(cell + " ");
					}
					myWriter.write("\n");
				}
				myWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		
	}

}
