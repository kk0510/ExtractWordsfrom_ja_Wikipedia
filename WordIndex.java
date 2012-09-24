
import java.util.regex.*;   //パターン抽出に必要
import java.math.BigDecimal;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import java.util.*;



/**
 *
 * @author kosuke
 */
public class WordIndex{
    public static void main(String args[]){			
	String line = "";
        int count = 0;
        HashSet<String> documents = new HashSet<String>();
        HashMap<String, String> tm = new HashMap<String, String>();
        
        try {
                
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("C:/Users/kosuke/Desktop/wiki_rawtext_kakkoari.txt"),"utf-8"));
		while((line = br.readLine()) != null){
                   System.out.println(line);
                    //段落の確保
                   
                    if(line.contains("==")){
                        count++;
                    }
                    
                    
                    if(!line.contains("EOS")){
                        String temp[] = line.split("\t"); 
                        if(temp[1].contains("名詞,")){
                            if(tm.containsKey(temp[0])){
                                String[] lastnum = tm.get(temp[0]).split(",");
                                int last = Integer.valueOf(lastnum[lastnum.length-1]);
                                if(last!=count){
                                    StringBuffer sb=new StringBuffer();
                                    sb.append(tm.get(temp[0]));
                                    sb.append(",");
                                    sb.append(String.valueOf(count));
                                    tm.put(temp[0], sb.toString());
                                    //tm.put(temp[0], tm.get(temp[0]) +","+String.valueOf(count));
                                }
                                //continue;
                            }
                            if(!tm.containsKey(temp[0])){   //新規の単語だったら
                                StringBuffer sb=new StringBuffer();
                                    sb.append(temp[0]);
                                    sb.append(",");
                                    sb.append(String.valueOf(count));
                                    tm.put(temp[0], sb.toString());
                               
                            }
                            
                        }
                        if(temp[1].contains("動詞,自立")){
                            String[] basicform = temp[1].split(",");
                            if(tm.containsKey(basicform[6]) && flag==true){          
                                    StringBuffer sb=new StringBuffer();
                                    sb.append(tm.get(basicform[6]));
                                    sb.append(",");
                                    sb.append(String.valueOf(count));
                                    tm.put(basicform[6], sb.toString());
                                    flag = false;
                                    
                            }
                            if(!tm.containsKey(basicform[6])){   //新規の単語だったら
                                
                                StringBuffer sb=new StringBuffer();
                                    sb.append("<<VERB>>,");
                                    sb.append(String.valueOf(count));
                                   tm.put(basicform[6], sb.toString());
                           
                            }
                            
                            
                        }
                   }
                   
                   if(count%10000==0){
                       System.out.println(count+"段落終了");
                   }

                }
                System.out.println("おわり");
                File file = new File("C:\\Users\\kosuke\\Desktop\\noun_verb_mecab_article_index.txt");//File file = new File("C:\\Users\\kosuke\\Desktop\\noun_mecab_noun_danraku_index.txt");
                FileWriter filewriter = new FileWriter(file, true);
                for(Iterator itr = tm.keySet().iterator();itr.hasNext();){
                    String t = itr.next().toString();
                    filewriter.write(t+","+tm.get(t).replaceFirst(t+",", "")+"\r\n");

                }
                System.out.println(count);
                filewriter.close();
                br.close();
                
	} catch (FileNotFoundException e) {
	e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
	}
        
        
        
        
        

    }
    
}
