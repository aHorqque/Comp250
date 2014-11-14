public class Question5 {

	public static void Hanoi(int m, int i, int j)
	{
		if(m>=1){
			Hanoi(m-1, i, 6-i-j);
			System.out.println(i+">"+j);
			Hanoi(m-1, 6-i-j, j);	
		}
	}

	public static void main(String[] args){
		Hanoi(3,1,2);
	}

}