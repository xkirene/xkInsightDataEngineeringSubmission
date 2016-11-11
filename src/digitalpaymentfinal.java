import java.io.*;
import java.util.*;
public class digitalpaymentfinal{
	
	private static Map<String,Set<String>> network;
	private static ArrayList<String> payer;
	private static ArrayList<String> receiver;
	private static String id1;
	private static String id2;
	private static Queue<String> q;


	//Create networking for every user using the given input file batch_payment
	//Use HashMap to store networks of every user
	public static void generateNetworking(String in){
		try{
		network=new HashMap<>();
		BufferedReader reader= new BufferedReader(new FileReader(in));
        reader.readLine();
        String line = null; 
        while((line=reader.readLine())!=null){ 
            String item[] = line.split(", ");
    		id1=item[1];
    		id2=item[2];
			if(!network.containsKey(id1)){
				Set<String> m=new HashSet<String>();
				network.put(id1,m);
			}
			if(!network.containsKey(id2)){
				Set<String> n=new HashSet<String>();
				network.put(id2, n);
			}
			network.get(id1).add(id2);
			network.get(id2).add(id1);
		}
        reader.close();
		}
		catch (Exception e) { 
            e.printStackTrace(); 
		}
	}
	
	//Payments needed to be verified coming from another input file stream_payment.
	//Use two ArrayLists to store two users in stream_payment.
	public static void makePayments(String payment){
		try{
	        payer=new ArrayList<String>();
	        receiver=new ArrayList<String>();
			BufferedReader reader= new BufferedReader(new FileReader(payment));
	        reader.readLine();
	        String line = null; 
	        while((line=reader.readLine())!=null){ 
	            String item[] = line.split(", ");
	            payer.add(item[1]);
	            receiver.add(item[2]);
	            if(!network.containsKey(item[1])){
					Set<String> i1=new HashSet<String>();
					network.put(item[1], i1);
	            }
	            if(!network.containsKey(item[1])){
					Set<String> i2=new HashSet<String>();
					network.put(item[1], i2);
	            }
	            
	        }
	        reader.close();
		}
		catch (Exception e) { 
            e.printStackTrace(); 
		}
				
	}
	
	//Check the possibility of user1 being a friend of user2 within the maxDegree of friendship given.
	//maxDegree could be any positive integer. In this problem, it should be 1,2,4
	public static boolean fraudPaymentCheck(String id1,String id2,int maxDegree){
		int level=0;
		q=new LinkedList<String>();
		Set<String> visited=new HashSet<String>();
		if(id1==id2){
			return true;
		}
		q.add(id1);
		visited.add(id1);
		while(!q.isEmpty()){
			level++;
			if(level>maxDegree){
				return false;
			}
			for(int i=0;i<q.size();i++){
				String front=q.remove();
				if(network.get(front).contains(id2)){
					return true;
				}
				Iterator<String> iter=network.get(front).iterator();
				while(iter.hasNext()){
					String n=iter.next();
					if(!visited.contains(n)){
						q.add(n);
						visited.add(n);
					}
				}
			}
		}
		return false;
	}
	//Write the payment "trusted" or "unverified" into an output file.
	public static void writeOutput(ArrayList<Boolean> result,String output){
		try{
			File csv=new File(output);
		    BufferedWriter bw = new BufferedWriter(new FileWriter(csv,true));
		    for(int g=0;g<result.size();g++){
		    	if(result.get(g)==true){
		    		bw.write("trusted");
		    		bw.newLine();
		    	}
		    	else{
		    		bw.write("unverified");
		    		bw.newLine();
		    	}
		    }
		    bw.close();
		}
		catch (FileNotFoundException e) { 
			e.printStackTrace(); 
			} 
		catch (IOException e) { 
			e.printStackTrace(); 
			} 
	}
		
	public static void main(String[] args){
		String input=args[0];
		generateNetworking(input);
		//System.out.println(network);
		String input2=args[1];
		makePayments(input2);
		ArrayList<Boolean> finalresult=new ArrayList<Boolean>();
		for(int k=0;k<payer.size();k++){
			boolean singleResult=fraudPaymentCheck(payer.get(k),receiver.get(k),Integer.parseInt(args[2]));
			finalresult.add(singleResult);
		}
		writeOutput(finalresult,args[3]);
		ArrayList<Boolean> finalresult2=new ArrayList<Boolean>();
		for(int k=0;k<payer.size();k++){
			boolean singleResult=fraudPaymentCheck(payer.get(k),receiver.get(k),Integer.parseInt(args[4]));
			finalresult2.add(singleResult);
		}
		writeOutput(finalresult2,args[5]);
		ArrayList<Boolean> finalresult4=new ArrayList<Boolean>();
		for(int k=0;k<payer.size();k++){
			boolean singleResult=fraudPaymentCheck(payer.get(k),receiver.get(k),Integer.parseInt(args[6]));
			finalresult4.add(singleResult);
		}
		writeOutput(finalresult4,args[7]);
		
		System.out.println("result generated successfully");
	}
		
	
}



