import java.util.*;
import java.io.*;

public class hashtagcounter {

	public static void main(String[] args) throws IOException
	{
		// Creating and Arraylist to store the input line by line.
		ArrayList<String> list = new ArrayList<String>(); 
		
		//Creating Hashtable to to store the hash value and the pointer to node in fionacci heap.
		Hashtable<String, FibNode> table=new Hashtable<String,FibNode>();
		
		
		String sh=" ";
		
		// Initializing the filewriter to null.
		FileWriter out=null; 
		
	   // If the output file name is provided this condition will execute.
		if ( args.length==2)
		{
		  out=new FileWriter(args[1]);
		}
	    // This statement will execute only if incorrect number of arguments are provided.
		else if( args.length==0 || args.length>2)
		{
			System.out.println("Provide correct arguements.");
			System.exit(0);
		}
		
		//Creating a Fibonacci heap object.
		FibHeap heap=new FibHeap();
		
		try {
			// Reading the input file
			FileReader ip= new FileReader(args[0]);
			BufferedReader read =new BufferedReader(ip);
			
		// reading the input file line by line into the arraylist(list) to be called again for parsing the hashtags.
		
		while((sh=read.readLine())!=null)
		{   
			//adding each line of the input file into separate entries of arraylist.
			list.add(sh);
		}
		
        //Adding the input file entries into hashtable and fibonacci heap.
		  for( int i=0;i<list.size();i++)
		  {
			//Splitting each element of the arraylist into the hashtag and corresponding frequency value . 
			String[] str1=list.get(i).split(" "); 
			
			
			//if the first character of the first element of the splitted string is # then the format of line is hashtag followed 
			//by space and then frequency.		
			 if ( str1[0].charAt(0)=='#') 
		   {
				//Parsing the tag from the first element of splitted string.
				 String tag=str1[0].substring(1,str1[0].length());         
				 
				//Parsing the key value from the second element of the splitted string.
				 int tKey=Integer.parseInt(str1[1]); 
				 
				//Condition to check if the table contains the key or not , if it contains the key then update the key value.	 
				 if( table.containsKey(tag))
				 {	 
				    //update the value of key in the hashtable.
					int incKey=table.get(tag).key+tKey;
					
					//make the corresponding change in fib heap.
					 heap.incKey(table.get(tag), incKey);
				 }
				 else 
				 {    //Executes if the table does not contain the tag value. 
					 
					//Putting the node into the fib heap from here 
					 FibNode n1=new FibNode(tag,tKey);
					 heap.nodeInsert(n1);
					table.put(tag,n1);
				 }
				  
			}
			 
          // Conditions if the string is a digit //
			 
			else if(Character.isDigit(str1[0].charAt(0)))
			{
			    //Parsing the integer value  of the string which is the number of top highest frequency elements in the heap.	
				int rNum=Integer.parseInt(str1[0]);
				
				// ArrayList to store the removed nodes from heap
				ArrayList<FibNode> rNodes = new ArrayList<FibNode>();
				
				
				for( int j=0;j<rNum;j++)
				{
					
					//Remove nodes from table and the heap.
					FibNode n2=heap.removeMax();
					table.remove(n2.getTag());
					
					
					// Add the removed nodes into the arraylist rNodes.
					FibNode rNode=new FibNode(n2.getTag(),n2.key);
					rNodes.add(rNode);
					
					// Condition to check for the length of command line arguments.
					
					//Executed if output file is provided.
					if(args.length==2)
					{
						if(j<rNum-1)
					    {
						  out.write(n2.getTag()+",");
					     }
					   else
					   {
						 out.write(n2.getTag());
					   }
					
				    } 
					// executed if output file is not provided.
					else if( args.length==1)
					{
						if(j<rNum-1)
						{
							System.out.print(n2.getTag()+",");
						}
						else
						{
							System.out.print(n2.getTag());
						}
					}
				 	
				}
		     
			//	Inserting the deleted nodes again into the fibonacci heap and hashtable
				for( int k=0;k<rNodes.size();k++)
				{
					heap.nodeInsert(rNodes.get(k));
					table.put(rNodes.get(k).getTag(), rNodes.get(k));
				}
				
				//Checking if the output should go to standard output or it should be written to a specified output file.
		         if( args.length==2)	
		        	 {
		        	 out.write("\n");
		        	 }
			        
		         else if(args.length==1)
					{
						System.out.print("\n");
					}
				
			}
			 
		//Condition if the string encountered is stop , then we stop  the execution of program. 
			 
			else if(str1[0].toLowerCase().equals("stop"))
			{
			  break;
			}
			
		  }
		 
		  read.close();
		  
		} catch(IOException ex)
		{
			ex.printStackTrace();
		}
		finally
		{
		    // If an output file path is provided then only this will be executed.
		    if( args.length==2)
		    	{
		    	 out.flush();
		    	 out.close();
		    	}
			
		}
		  
		
	}
	
}
