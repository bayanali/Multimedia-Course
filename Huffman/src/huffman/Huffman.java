package huffman;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class Huffman {
    
    public Queue <Node> getProbability(String content) throws FileNotFoundException
    {
        int size = content.length(), c = 1;
        
        Vector <Character> VecOfContent = new Vector <Character>();      
        
        Queue <Node> q = new PriorityQueue<Node>();      
        
        for (int i = 0; i < content.length(); i++)
            VecOfContent.add(content.charAt(i));
              
        while (!VecOfContent.isEmpty())
        {
            Node n = new Node(); 
            for (int j = 1; j < VecOfContent.size(); j++)
            {
                if (VecOfContent.elementAt(0) == VecOfContent.elementAt(j))
                {
                    c++; 
                    VecOfContent.remove(j);
                    j--;          
                }
            }
            
            n.chars = "";
            n.chars += VecOfContent.elementAt(0);
            n.p = (double) c/size;          
            q.add(n);          
            c = 1;
            VecOfContent.remove(0);                           
        }
        
       return q;          
    }
    
    public Node buildTree(Queue <Node> q) throws FileNotFoundException
    {
        Queue <Node> nodes = new PriorityQueue <Node>(); 
        nodes = q;
        while (q.size() != 1)
        {
            Node sum = new Node();
            Node min1 = new Node();
            Node min2 = new Node();
            min1 = q.poll();
            min2 = q.poll();
            sum.p = min1.p + min2.p; 
            sum.chars = min1.chars + min2.chars;
            sum.left = min1; 
            sum.right = min2; 
            q.add(sum);
        } 
       return q.poll();
    }
    
    public void Coding(Node n, Map m, String s)
    {     
        if (n.left == null && n.right == null)
        {
            m.put(n.chars, s);
            //System.out.println(n.chars + " " + s);
            return;
        }
                 
        Coding(n.left,m, s+"1");
        Coding(n.right, m ,s+"0");
    }
    
    public void Compress(String fileName) throws FileNotFoundException
    {
        String Data = "C:\\Users\\bayan\\Desktop\\Java Coding\\Huffman\\src\\huffman\\"+ fileName +".txt",
               content = new Scanner(new File(Data)).useDelimiter("\\Z").next(), compress = "",
               compressedPath = "C:\\Users\\bayan\\Desktop\\Java Coding\\Huffman\\src\\huffman\\Compressed.txt",
               tablePath = "C:\\Users\\bayan\\Desktop\\Java Coding\\Huffman\\src\\huffman\\Table.txt";
        
       Queue <Node> nodes = getProbability(content);
       Map table = new HashMap(); 
       Coding(buildTree(nodes),table,"");
      
       for ( int i = 0; i < content.length(); i++)
       {
           String temp = "";
           temp += content.charAt(i);
           compress += table.get(temp);             
       }
       
       File file = new File(compressedPath), file2 = new File(tablePath);
       PrintWriter write = new PrintWriter(file), write2 = new PrintWriter(file2);
       write.print(compress);
       
       for (Object i : table.entrySet())
           write2.println(i);
   
       
        write.close();
        write2.close();
      
       System.out.println(table);
       System.out.println(compress);        
    }
    
    public void Decompressed(String fileName) throws FileNotFoundException, IOException, ClassNotFoundException
    {
       String decompressedPath = "C:\\Users\\bayan\\Desktop\\Java Coding\\Huffman\\src\\huffman\\Decompressed.txt",
               compressedPath = "C:\\Users\\bayan\\Desktop\\Java Coding\\Huffman\\src\\huffman\\"+fileName+".txt",
               content = new Scanner(new File(compressedPath)).useDelimiter("\\Z").next(),
               tablePath = "C:\\Users\\bayan\\Desktop\\Java Coding\\Huffman\\src\\huffman\\Table.txt";
       
        File read= new File(tablePath), write = new File(decompressedPath);
        PrintWriter w = new PrintWriter(write); 
        Scanner sc = new Scanner(read);
        Map table = new HashMap();
        
        String currentLine;
        while(sc.hasNext())
        {
            currentLine = sc.next();
            StringTokenizer st = new StringTokenizer(currentLine,"=",false);//tokenize the currentLine          
            String key = st.nextToken(), //put tokens to currentLine in map
                    value = st.nextToken();       
            table.put(value,key);
        }
       
       String Data = "" , temp = ""; 
       for (int i = 0; i < content.length(); i++)
       {
           temp += content.charAt(i);
           if (table.containsKey(temp))
           {
              Data += table.get(temp);
              temp = "";            
           }
       }
       
       w.print(Data); 
       w.close();
  }

     
    public static void main(String[] args) throws FileNotFoundException, IOException, ClassNotFoundException {
       
    //P(a)=0.17, P(b)=0.22, P(c)=0.15, P(d)=0.14, P(e)=0.3, P(f)=0.02
    Huffman x = new Huffman(); 
    Queue <Node> q = new PriorityQueue<Node>(); 
    Node a = new Node(0.17,"a"),
         b = new Node(0.22, "b"),
         c = new Node(0.15,"c"),
         d = new Node(0.14,"d"),
         e = new Node(0.3,"e"),
         f = new Node(0.02,"f");
     q.add(a);
     q.add(b);
     q.add(c);
     q.add(d);
     q.add(e);
     q.add(f);
     
     x.Compress("Data");
     x.Decompressed("Compressed");
              
    }   
}
