import java.util.*;

public class FibNode
{
        FibNode left, right, child, parent;
        int deg = 0;       
        boolean childCut = false; 
        private String nHash;
        int key;

        FibNode(String nHash,int key)
        {
           this.left = this;
           this.right = this;
           this.parent = null;
           this.deg = 0;
           this.nHash = nHash;
           this.key = key;

        }

        public  String  getTag()
        {
            return this.nHash;
        }

 }
