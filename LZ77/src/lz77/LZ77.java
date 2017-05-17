package lz77;
import java.io.File;
import java.io.PrintWriter;
import java.io.FileNotFoundException;
import java.util.*;

public class LZ77 {

    class Tag {

        int position;
        int length;
        char ch;
    }

    Vector<Tag> Compress(String s) throws FileNotFoundException {
        Vector<Tag> tags = new Vector<Tag>();
        String Data = "C:\\Users\\bayan\\Desktop\\Java Coding\\LZ77\\src\\lz77\\"+s+".txt";
        String sub, key = "", TagsPath = "C:\\Users\\bayan\\Desktop\\Java Coding\\LZ77\\src\\lz77\\Tags.txt",
                content = new Scanner(new File(Data)).useDelimiter("\\Z").next();
        int j = 1, p = 0, found = 0;
        key += content.charAt(0);
        for (int i = 0; i < content.length(); i++)//Search window
        {
            Tag t = new Tag();
            while (j < content.length())//Look-ahead Window
            {
                sub = content.substring(0, i);
                found = sub.lastIndexOf(key);
                if (found == -1) {
                    break;
                }
                p = found;
                key += content.charAt(j);
                i = j;
                j++;
            }
            //System.out.println(i + " " + j + " " + key);
            t.position = (j - key.length()) - p;
            t.length = key.length() - 1;
            t.ch = key.charAt(key.length() - 1);
            tags.add(t);
            key = "";
        }
        
        File file = new File(TagsPath);
        PrintWriter PW = new PrintWriter(file);
        
        for (Tag tt : tags) 
        {
            System.out.println(tt.position + " " + tt.length + " " + tt.ch);
            PW.println(tt.position);
            PW.println(tt.length);
            PW.println(tt.ch);
        }
        PW.close();
        
        return tags;
    }
  
    void Decompress(String p) throws FileNotFoundException {
        
         
        String ori = "",in, Path = "C:\\Users\\bayan\\Desktop\\Java Coding\\LZ77\\src\\lz77\\",
               DecompressedPath = "C:\\Users\\bayan\\Desktop\\Java Coding\\LZ77\\src\\lz77\\Decompressed.txt";
        Path = p+".txt";
        File read = new File(Path);
        Scanner inputFile = new Scanner (read);
        Tag t = new Tag(); 
        File file = new File(DecompressedPath);
        PrintWriter write = new PrintWriter(file);
        while (inputFile.hasNext())
        {
            t.position = inputFile.nextInt();
            t.length = inputFile.nextInt();
            in = inputFile.next();
            t.ch = in.charAt(0);
            
        while (t.length != 0) 
        {
            ori += ori.charAt(ori.length() - t.position);
            t.length--;
        }

        ori += t.ch;
        }
       
        write.print(ori);
        write.close();
    }

    String Decompress(Vector<Tag> v)
    {
        String ori = "";
        for (Tag t : v) {
            while (t.length != 0) {
                ori += ori.charAt(ori.length() - t.position);
                t.length--;
            }

            ori += t.ch;
        }
        return ori;
    }

    public static void main(String[] args) throws FileNotFoundException {

        LZ77 obj = new LZ77();
        
        String Data = "Data", Tags = "Tags";
        obj.Compress(Data);
        obj.Decompress(Tags);

    }
}
