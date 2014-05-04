public class Test{
public static void main(String[]args){    
    try{
        int i = 5/0;
    }catch(ArithmeticException ex){
        System.out.println("NAPAKA");
    }	
		System.out.println("Hello world");
	}
}
