package arithmetic;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;


public class Arithmetic {
    
    class range
    {
        double low = 0.0; 
        double high = 0.0; 
    }
    
    public HashMap getProbability(String content) throws FileNotFoundException
    {
        int size = content.length(), c = 1;
        
        Vector <Character> VecOfContent = new Vector <Character>();      
        
        HashMap <Character, range> ranges = new HashMap <Character, range>();      
        
        String tablePath = "C:\\Users\\bayan\\Desktop\\Coding\\Java Coding\\Arithmetic\\src\\arithmetic\\Table.txt";
        File file = new File (tablePath);
        PrintWriter write = new PrintWriter(file);
       
        for (int i = 0; i < content.length(); i++)
            VecOfContent.add(content.charAt(i));
          
         double low = 0.0 , high = 0.0, probability = 0.0; 
        while (!VecOfContent.isEmpty())
        {
           range r = new range();
            for (int j = 1; j < VecOfContent.size(); j++)
            {
                if (VecOfContent.elementAt(0) == VecOfContent.elementAt(j))
                {
                    c++; 
                    VecOfContent.remove(j);
                    j--;          
                }
            }
            probability = (double) c/size; 
            write.println(VecOfContent.elementAt(0));
            write.println(probability); 
            low = high;
            high += probability;
            r.low = low; 
            r.high = high; 
            ranges.put(VecOfContent.elementAt(0),r);
            c = 1;
            VecOfContent.remove(0);                           
        }
        
       write.close();      
       return ranges;          
    }
    
     public double Compress(String fileName) throws FileNotFoundException
    {
        String Data = "C:\\Users\\bayan\\Desktop\\Coding\\Java Coding\\Arithmetic\\src\\arithmetic\\"+ fileName +".txt",
               content = new Scanner(new File(Data)).useDelimiter("\\Z").next(),
               compressedPath = "C:\\Users\\bayan\\Desktop\\Coding\\Java Coding\\Arithmetic\\src\\arithmetic\\Compressed.txt";
        
        File file = new File ( compressedPath);
        PrintWriter write = new PrintWriter(file);
                    
       HashMap <Character, range> ranges = new HashMap <Character, range>();
       ranges = getProbability(content);
       
       /*
       range r = new range();
       r.low = 0.0;
       r.high = 0.8;
       ranges.put( 'A',r);
       range r1 = new range();
       r1.low = 0.8;
       r1.high = 0.82;
       ranges.put('B', r1);
       range r2 = new range();
       r2.low = 0.82;
       r2.high = 1.0;
       ranges.put('C', r2);
       */
       
       double lower = 0.0 , upper = ranges.get(content.charAt(0)).high ,range;
       System.out.println(lower + " " + upper + " " + content.charAt(0) );
       for (int i = 1; i < content.length(); i++)
       {
           range = upper - lower; 
           double lower1 = lower;
           lower = lower + ( range*ranges.get(content.charAt(i)).low);
           upper = lower1 + (range*ranges.get(content.charAt(i)).high); 
           System.out.println(lower + " " + upper  + " " + content.charAt(i));
       }
       
       double value = (lower+upper)/2;
        write.println(value);
        write.println(content.length());
        write.close();
        return value;     
    }
     
     public void Decompress(String compressedfile) throws FileNotFoundException
     {
         String path1 = "C:\\Users\\bayan\\Desktop\\Coding\\Java Coding\\Arithmetic\\src\\arithmetic\\" + compressedfile + ".txt",
         path2 = "C:\\Users\\bayan\\Desktop\\Coding\\Java Coding\\Arithmetic\\src\\arithmetic\\Table.txt",
         path3 = "C:\\Users\\bayan\\Desktop\\Coding\\Java Coding\\Arithmetic\\src\\arithmetic\\Decompressed.txt",
                 Data = ""; 
         double low = 0.0, high = 0.0, compressedValue = 0.0, probability = 0.0;
         int len = 0; 
        String Char  = "";
        File read1 = new File(path1) , read2 = new File(path2), write = new File(path3);
        Scanner inputFile1 = new Scanner (read1) , inputFile2 = new Scanner(read2);
        
        PrintWriter write1 = new PrintWriter(write);
        HashMap <String, range> ranges = new HashMap <String, range>();
       
        while (inputFile1.hasNext())
        {
            compressedValue = inputFile1.nextDouble(); 
            len = inputFile1.nextInt(); 
        }
            
        while (inputFile2.hasNext())
        {
            
            range r = new range();
            Char = inputFile2.nextLine();
            probability = Double.parseDouble(inputFile2.nextLine());
            low = high; 
            high += probability; 
            r.low = low; 
            r.high = high;
            ranges.put(Char,r);
        }
         
        low = 0.0; 
        high = 0.0; 
        double range = 0.0; 
        for (int i = 0; i < len; i++)
        {          
            for(HashMap.Entry<String,range> element: ranges.entrySet())
            {
                low = element.getValue().low; 
                high = element.getValue().high; 
                //System.out.println(low + " " + high);
                Char = element.getKey();
                if (compressedValue > low && compressedValue < high)
                {
                    Data += Char; 
                    range = high - low; 
                    compressedValue = ( compressedValue - low) / range;
                    break;
                }                   
            }
        }
        
         write1.println(Data);
         write1.close();
     }
    
    
    public static void main(String[] args) throws FileNotFoundException {
        
        Arithmetic x = new Arithmetic(); 
        x.Compress("Data");
        x.Decompress("Compressed");
    }
    
}
