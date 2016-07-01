import java.util.Random;
import java.util.Stack;


public class SortStackMain 
{
	static final int COUNT = 30;
	static final int MAX_VALUE = 1000;
	
	public static void main(String[] args) 
	{
		boolean sortIsGood = true;
		
		Random generator = new Random( System.nanoTime() );
		Stack<Integer> stack = new Stack<Integer>();
		
		for(int i = 0; i < COUNT; i++)
			stack.push(generator.nextInt(MAX_VALUE));
		
		stack = sortStack(stack);
		
		boolean countIsGood = size(stack) == COUNT;
			
		int tmp = stack.pop();
		while(!stack.isEmpty())
		{
			System.out.print(tmp + ", ");
			
			if(tmp > stack.peek())
				sortIsGood = false;
			
			tmp = stack.pop();
		}
		System.out.println(tmp);
		
		if(!countIsGood)
			System.out.println("Erreur: il manque des elements dans la pile");
		else if(!sortIsGood)
			System.out.println("Erreur: le trie a echoue");
		else
			System.out.println("It's all good");
	}
    
	private static int size(Stack<Integer> stack) {
	
		int size = 0;
		Stack<Integer> tmp = new Stack<Integer>();
		while ( !(stack.empty())){
			//jenleve les elements du stack pour les placer dans un stack temporaire 
			tmp.push(stack.pop()); 
			size++;
		}
		
		//je replace les elements dans mon stack initial
		for(int i =0 ; i< size ; i++){
			stack.push(tmp.pop());
		}
		return size;
		
	
    }
    
	static Stack<Integer> sortStack(Stack<Integer> stack)
	{
		 Stack<Integer> sortedStack = new Stack<Integer>();
		 while( !stack.isEmpty() )
		    {
			 int poppedElement = stack.pop();
/*on verifie que lelement sur le dessus du stack ordonne est plus petit que lelement qui etait
sur le dessus du stack precedemment (avant detre enleve par le pop)*/
			 while( !sortedStack.isEmpty() && (sortedStack.peek() < poppedElement) ) {
				 
				 //on ajoute lelement le plus petit sur la pile
				 stack.push( sortedStack.pop() );
		        }
			 	
		        sortedStack.push( poppedElement );
		    }
		
		 return sortedStack; 
	}
}
