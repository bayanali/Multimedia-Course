package lzw;
import java .io.File;
import java.io.PrintWriter;
import java.io.FileNotFoundException;
import java.util.*;

public class LZW {
    
    Vector<Integer> Tags = new Vector <Integer>();
      
    void CreateDictionary(HashMap D)
    {
          String AlphapetS = "abcdefghijklmnopqrstuvwxyz",
                 AlphapetC = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
          
      for (int i = 0; i < AlphapetC.length(); i++)
      {
         String temp = "";
         temp += AlphapetC.charAt(i);
         D.put(temp,temp.hashCode());
      }
      
      for (int i = 0; i < AlphapetS.length(); i++)
      {

         String temp = "";
         temp += AlphapetS.charAt(i);   
         D.put(temp,temp.hashCode());
      }
      
    }

    void Compress(String s) throws FileNotFoundException {
        
        String Data = "C:\\Users\\bayan\\Desktop\\Java Coding\\LZW\\src\\lzw\\"+s+".txt";
        String sub = "" , TagsPath = "C:\\Users\\bayan\\Desktop\\Java Coding\\LZW\\src\\lzw\\Tags.txt",
                content = new Scanner(new File(Data)).useDelimiter("\\Z").next();
        
        HashMap <String,Integer> D = new HashMap <String,Integer> (); 
        CreateDictionary(D);
  
      int j = 1, index = 0, indexofD = 128;
      for (int i = 0; i < content.length(); i++)
      {
          
          sub += content.charAt(i);
          index = D.get(sub); 
          if (j < content.length())
          sub += content.charAt(j);
          
          while (D.containsKey(sub) && j < content.length())
          {                
              index = D.get(sub);
              i = j;
              j++;             
              sub += content.charAt(j);  
          }
          
          Tags.add(index);        
          D.put(sub, indexofD);    
          j++;    
          sub = "";
          indexofD++; 
          System.out.println(Tags.lastElement());         
      } 
      
      File file = new File(TagsPath);
      PrintWriter PW = new PrintWriter(file);
        
        for (int tt : Tags) 
        PW.println(tt);     
        
        PW.close();
    }
  
    void Decompress(String p) throws FileNotFoundException {
     
        int j = 0,i = 0, indexofD = 128;
        String sub = "", Data = "", Path = "C:\\Users\\bayan\\Desktop\\Java Coding\\LZW\\src\\lzw\\",
               DecompressedPath = "C:\\Users\\bayan\\Desktop\\Java Coding\\LZW\\src\\lzw\\Decompressed.txt";
        Path += p + ".txt";
        File read = new File(Path);
        Scanner inputFile = new Scanner (read);  
        File file = new File(DecompressedPath);
        PrintWriter write = new PrintWriter(file);
        HashMap <Integer,String> D = new HashMap <Integer,String> (); 
        
        j = inputFile.nextInt();
        D.put(j,String.valueOf(Character.toChars(j)));
        Data += D.get(j);      
        while(inputFile.hasNext())
        {
            i = inputFile.nextInt();            
            sub = D.get(j);
            D.put(indexofD, sub);
            
            if (!D.containsKey(i))
                D.put(i,String.valueOf(Character.toChars(i)));
            
            sub += D.get(i).charAt(0);
            D.replace(indexofD,sub);
            Data += D.get(i);
            indexofD++;
            sub = "";
            j = i; 
        }
        
        write.print(Data);
        write.close();
    }

    public static void main(String[] args) throws FileNotFoundException {
       
        LZW obj = new LZW();
        String Data = "Data", Tags = "Tags";
        obj.Compress(Data);
        obj.Decompress(Tags);        
}
}