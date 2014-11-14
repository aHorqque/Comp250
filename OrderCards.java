
import java.util.ArrayDeque;

public class Q3 {

	public static void main(String []args)
	{
		int length = 8; 
		ArrayDeque<Integer> baseCards = new ArrayDeque<Integer>();
		
		ArrayDeque<Integer> orderCards = new ArrayDeque<Integer>();
		
		for(int i=1; i<length+1; i++)
			baseCards.add(i);
		
		while(baseCards.peekLast()!=null)
			{
				if(orderCards.peekLast()!=null)	
				{	orderCards.push(orderCards.removeLast());
					
					orderCards.push(baseCards.removeLast());
	
					System.out.println(orderCards);}
				else	
				{
					orderCards.push(baseCards.removeLast());
					
					System.out.println(orderCards);}

			}	
		
		System.out.println(orderCards);

		
	}
	
}
