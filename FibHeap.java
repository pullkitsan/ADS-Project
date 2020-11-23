import java.util.*;


public class FibHeap
{

    private FibNode nMax;
    private int numNodes;

    // --------- The method inserts a new node into the fibonacci heap----------//
    public void nodeInsert(FibNode n1)
    {


        //check if max node is not null
        if (nMax != null) {

        
            n1.left = nMax;
            n1.right = nMax.right;
            nMax.right = n1;

           // Checking if the node becomes null or not 
            if ( n1.right!=null) {                               
                n1.right.left = n1;
            }
            if ( n1.right==null)
            {
                n1.right = nMax;
                nMax.left = n1;
            }
            if (n1.key > nMax.key) {
                nMax = n1;
            }
        } else {
            nMax = n1;

        }

        numNodes++;
    }


    //-------- The method cuts the node a from b -------//
    
    public void removeNode(FibNode a, FibNode b)
    {
        // removing the a and reducing the degree of b
        a.left.right = a.right;
        a.right.left = a.left;
        b.deg--;

        // Conditions to check if resetting the child is required or not
        if (b.child == a) {
            b.child = a.right;
        }

        if (b.deg == 0) {
            b.child = null;
        }

        // adding x to the root list
        a.left = nMax;
        a.right = nMax.right;
        nMax.right = a;
        a.right.left = a;

        
        a.parent = null;

        //Updating the child cut values to be false
        a.childCut = false;
    }

    //--------- This method performs the cascading cut --------//
    public void cascadeCut(FibNode b)
    {
        FibNode a = b.parent;

        //Checking if the parent exists or not
        if (a != null) {
            if (!b.childCut) {
                b.childCut = true;
            } else {
                // it's marked, cut it from parent
                removeNode(b, a);

                // cut its parent as well
                cascadeCut(a);
            }
        }
    }

    //Increase the value of key for the given node in heap
    public void incKey(FibNode a, int val)
    {
        if (val < a.key) {
        }

        a.key = val;

        FibNode b = a.parent;

        if ((b != null) && (a.key > b.key)) {
            removeNode(a, b);
            cascadeCut(b);
        }

        if (a.key > nMax.key) {
            nMax = a;
        } 
    }

    //---------- The method performs remove max operation ---------//
    public FibNode removeMax()
    {
        FibNode c = nMax;
        if (c != null) {
            int nChild = c.deg;
            FibNode a = c.child;
            FibNode trav;

            
            while (nChild > 0) {
                trav = a.right;

                // removing a from the child list
                a.left.right = a.right;
                a.right.left = a.left;

                // adding a to root liist
                a.left = nMax;
                a.right = nMax.right;
                nMax.right = a;
                a.right.left = a;

               
                a.parent = null;
                a = trav;
                nChild--;

            }


            // remove z from root list of heap
            c.left.right = c.right;
            c.right.left = c.left;

            if (c == c.right) {
                nMax = null;

            } else {
               nMax = c.right;
               merge();
           }
           numNodes--;
           return c;
       }
        return null;
    }

   //------------ This method performs merging operation --------//
    public void merge()
    {
        List<FibNode> table = new ArrayList<FibNode>(45);

        for (int i = 0; i < 45; i++) {
            table.add(null);
        }
                       
     // Initializing the number of roots to 0
        int nRoots = 0;
        FibNode a = nMax;

       // Condition executes if node a is not null
        if (a != null) {
            nRoots++;
            a = a.right;                     

            while (a != nMax) {
                nRoots++;
                a = a.right;
            }
        }

   // Condition executes the number of roots becomes 0     
        while (nRoots > 0) {
             
        	boolean stat=true;
            int nDeg = a.deg;
            FibNode next = a.right;

            // Condition to check if combining and merge is required
            while(stat) {
                FibNode b = table.get(nDeg);
                if (b == null) {
                    break;
                }

                // Condition to check which key is g
                if (a.key < b.key) {
                    FibNode temp = b;
                    b = a;
                    a = temp;
                }

                //make b the child of a as a key value is more 
                mChild(b, a);

                //setting the degree to null
                table.set(nDeg, null);
                nDeg++;
            }

            // Updating and storing a in the degree table
            table.set(nDeg, a);

            // traverse through the list 
            a = next;
            nRoots--;
        } 
        //Setting the max node value to null
        nMax = null;

        // Degree table combine operation 
        for (int i = 0; i < 45; i++) {
            FibNode b = table.get(i);
            if (b == null) {
                continue;
            }

            //Condition executes till the max node does'nt become null
            if (nMax != null) {

                // removing the node 
                b.left.right = b.right;
                b.right.left = b.left;

                // adding to rot list.
                b.left = nMax;
                b.right = nMax.right;
                nMax.right = b;
                b.right.left = b;

                // Condition executes if the key value becomes more than max value
                if (b.key > nMax.key) {
                    nMax = b;
                }
            } else {
                nMax = b;
            }
        }
    }

    //-------------------This method make y a child of a -----------//
    public void mChild(FibNode y, FibNode a)
    {
        // remove b from root list of heap
        y.left.right = y.right;
        y.right.left = y.left;
        y.parent = a;

        //Condition executes if child value of a is null
        if (a.child == null) {
            a.child = y;
            y.right = y;
            y.left = y;
        } else {
            y.left = a.child;
            y.right = a.child.right;
            a.child.right = y;
            y.right.left = y;
        }
        a.deg++;

        // childCut value is set to false
        y.childCut = false;
    }

}