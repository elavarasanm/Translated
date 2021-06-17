package varsan;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.supercsv.io.CsvMapWriter;
import org.supercsv.io.ICsvMapWriter;
import org.supercsv.prefs.CsvPreference;

public class demo {

    private static long startTime = System.currentTimeMillis();

	static void count(String filename ,Map<String,Integer> words,ArrayList<String> english) throws Exception
	{     String str1="";
          
          ArrayList<String> inputText = new ArrayList<String>();
		  BufferedReader br = new BufferedReader(new FileReader(filename));
		     while ((str1 = br.readLine()) != null) 
		     {
		         StringTokenizer st = new StringTokenizer(str1, ",.;:'\" ");
		          while (st.hasMoreTokens()) 
		          {
		            inputText.add(st.nextToken());
		            }
		     }
		         br.close();
		
		for(int i=0;i<english.size();i++)
		{
		Integer count=0;
		for(String word:inputText)
		 {
			if(word.equals(english.get(i)))
			{
			   count=words.get(word);
			   if(count !=null)
		        count++;
			    else
				count=1;
        }
			
			words.put(english.get(i),count);
		}
	}
		
	}
	

	public static Map<String,String> fillMap() {
		String Frenchfile = "C:\\Users\\Hp Elite Book\\Desktop\\translate\\french_dictionary.csv";
	     Map<String,String> map = new HashMap<String,String>();
			  try {
			        BufferedReader input = new BufferedReader(new FileReader(Frenchfile));
			        String line;
			        while((line = input.readLine())!=null){
			        
			            String[] pair = line.split(",");
			        	map.put(pair[0], pair[1]);
			         }
			    input.close();
	        }catch (IOException e) {
		      e.printStackTrace();
	        }
	return map;	  
	}
	
	private static String replaceTag(String str, Map<String,String> map) {
	    Map<String,Integer> count = new HashMap<String,Integer>();
		for (Map.Entry<String, String> entry : map.entrySet()) {
			if (str.contains(entry.getKey())) {
				str = str.replace(entry.getKey(), entry.getValue());
				
			}
		
		}
		return str;
	}
	public static void main(String[] args) throws IOException {
		
		long beforeUsedMem=Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
		 String shakespeare = "C:\\Users\\Hp Elite Book\\Desktop\\translate\\t8.shakespeare.txt";
		    String output="C:\\Users\\Hp Elite Book\\Desktop\\translate\\t8.shakespeare.translated.txt";
			String perfomance="C:\\Users\\Hp Elite Book\\Desktop\\translate\\perfomance.txt";
			String frequency="C:\\Users\\Hp Elite Book\\Desktop\\\\translate\\frequency.csv";
		    String Englishfile ="C:\\Users\\Hp Elite Book\\Desktop\\translate\\find_words.txt";
			String Frenchfile = "C:\\Users\\Hp Elite Book\\Desktop\\translate\\french_dictionary.csv";
			
			ArrayList<String> english = new ArrayList<>();
			ArrayList<String> french = new ArrayList<>();
					  
			    BufferedReader input;
				try {
					input = new BufferedReader(new FileReader(Frenchfile));
				     String st;
			        while((st = input.readLine())!=null){
			              String[] pair = st.split(",");
			              english.add(pair[0]);
			               french.add(pair[1]);
			         }
			      input.close();
	           
			Map<String, Integer> words=new LinkedHashMap<String, Integer>();
		    try {
				count(shakespeare,words,english);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		//	System.out.println(words);

			ICsvMapWriter writer = new CsvMapWriter(new FileWriter(frequency), CsvPreference.EXCEL_PREFERENCE);
			try {


			final String[] header = new String[] { "English", "French", "count" };
			writer.writeHeader(header);
			int i=0;
			for (Entry<String, Integer> e : words.entrySet()) {
			    Object key = e.getKey();
			    Object value = e.getValue();

			final HashMap data1 = new HashMap();
			data1.put(header[0], key);
			data1.put(header[1], french.get(i));
			data1.put(header[2], value);
			writer.write(data1, header);
			i++;

			}
			//System.out.println("Writing Completed...!");
			} finally {
			writer.close();
			}
	

	   Map<String,String> variableMap = fillMap();
		Path path = Paths.get(shakespeare);
		Path out = Paths.get(output);
		Stream<String> lines;
		
			try {
				lines = Files.lines(path,Charset.forName("UTF-8"));
				List<String> replacedLines = lines.map(line -> replaceTag(line,variableMap))
	                    .collect(Collectors.toList());
				Files.write(out, replacedLines, Charset.forName("UTF-8"));
				
				lines.close();
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		

			} catch (IOException e) {
				
				e.printStackTrace();
			}
				int mb = 1024 * 1024; 
				 
				// get Runtime instance
				Runtime instance = Runtime.getRuntime();
		 
				System.out.println("***** Heap utilization statistics [MB] *****\n");
		 
				// available memory
				System.out.println("Total Memory: " + instance.totalMemory() / mb);
		 
				// free memory
				System.out.println("Free Memory: " + instance.freeMemory() / mb);
		 
				// used memory
				System.out.println("Used Memory: "
						+ (instance.totalMemory() - instance.freeMemory()) / mb);
		 
				// Maximum available memory
				System.out.println("Max Memory: " + instance.maxMemory() / mb);
				

				System.out.println("***** Time to process:*****\n");
				long endTime = System.currentTimeMillis();
			    System.out.println("It took " + (endTime - startTime) + " milliseconds");
	}
	
	}

