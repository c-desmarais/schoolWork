import java.io.*;
import java.util.Stack;

public class PostfixSolverMain 
{
	public static void main(String[] args) throws IOException 
	{
		Stack<Double> stack = new Stack<Double>();
		
		String s = "25 5 2 * + 15 3 / 5 - +";
		
		for(String token : s.split("\\s")) 
		{	
			switch(token){
			case("+"):
				stack.push(stack.pop() + stack.pop());
				break;
			case("-"): 
				double tmpMoins1 = stack.pop(); 
				double tmpMoins2 = stack.pop();
				stack.push(tmpMoins2 - tmpMoins1); 
				break; 
			case("*"):
				stack.push(stack.pop() * stack.pop()); 
				break; 
			case("/"): 
				double tmpDiv1 = stack.pop(); 
				double tmpDiv2 = stack.pop();
				stack.push(tmpDiv2 / tmpDiv1); 
				break;
			default: 
				double operande = Double.parseDouble(token); 
				stack.push(operande); 
				break; 
			
			}
			
			
		}
     
		System.out.println("25 + 5*2 + 15/3 - 5 = "+stack.peek());
		if(stack.peek() == 35)
			System.out.println("It's all good");
		else
			System.out.println("Erreur: mauvais resultat");
     }
}
