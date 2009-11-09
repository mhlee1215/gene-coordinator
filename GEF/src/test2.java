import java.awt.Point;
import java.util.HashMap;


public class test2 {
	public static void main(String[] argv)
	{
		HashMap map =new HashMap();
		map.put(new Point(1, 1), "a");
		
		
		System.out.println(map.get(new Point(1, 1)));
	}
}
