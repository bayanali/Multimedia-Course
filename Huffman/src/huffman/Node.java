package huffman;
import java.util.Comparator;

public class Node implements Comparable <Node> {
    
    double p ; //Probability
    String chars ; 
    Node left; 
    Node right;

    public Node()
    {
      p = 0.0;
      chars = ""; 
      left = null;
      right = null;
    }
    
    public Node(double p, String chars)
    {
        this.p = p;
        this.chars = chars;
        left = null;
        right = null;
    }
    
    public int compareTo(Node t) {
        
        if (this.p == t.p)
            return 0;
        else if (this.p > t.p)
            return 1;
        else 
            return -1;
    }

    
}
