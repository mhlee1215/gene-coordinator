import java.util.Scanner;
public class Main
{
	public static byte getbase64Byte ( byte bsrc ) {

		if (bsrc < 26)       bsrc = (byte)(bsrc +'A');
		else if (bsrc < 52)  bsrc = (byte)(bsrc + 'a'- 26);
		else if (bsrc < 62)  bsrc = (byte)(bsrc + '0'- 52);
		else if (bsrc < 63)  bsrc = ( byte ) '+';
		else                      bsrc = ( byte ) '/';

		return bsrc;

	}
	
	public static String GetBase64Encode (String str)
    {
		byte btemp;
		int sidx, didx, nsrclen;
		if (str == null)  return  null;

        byte data[] = new byte[str.length()+2];
        // str.getBytes(0, str.length(), data, 0);

		// for ( int i = 0; i < str.length (); i++ ) data [i] = ( byte )str.charAt ( i );
		data = str.getBytes ();

        byte dest[] = new byte[( data.length + 3 - data.length/3)*4];
        // 3-byte to 4-byte conversion

		nsrclen =data.length;
		// System.out.println ( " Len : " + nsrclen );
		didx = 0; sidx = 0;
		while ( nsrclen > 2 ) {

            dest[didx++]   = getbase64Byte ( (byte) ((data[sidx] >>> 2) & 0x003f));
            dest[didx++] = getbase64Byte ((byte) ((data[sidx+1] >>> 4) & 0x000f | ( ( data[sidx] & 0x03 ) << 4)));
            dest[didx++] = getbase64Byte((byte) ((data[sidx+2] >>> 6) & 0x0003 | ((data[sidx+1] & 0x0f)<< 2) ));
            dest[didx++] = getbase64Byte((byte) (data[sidx+2] & 0x3f ));
			nsrclen -= 3;
			sidx += 3;
        }

		// System.out.println ( " Len : " + nsrclen + "," + didx + "," + sidx  );

		if ( nsrclen != 0 ) {
			btemp = getbase64Byte ( (byte) ((data[sidx] >>> 2) & 0x003f));
			dest[didx++] = btemp;
			if ( nsrclen > 1) {
				dest[didx++] = getbase64Byte ((byte) ((data[sidx+1] >>> 4) & 0x000f | ( ( data[sidx] & 0x03 ) << 4)));
				dest[didx++] = getbase64Byte((byte) ( ((data[sidx+1] & 0x0f)<< 2) ));
											//	base64_table[(current[1] & 0x0f) << 2];
				dest[didx++] =( byte) '=';
			}
			else {
				dest[didx++] = getbase64Byte ((byte) ( ( ( data[sidx] & 0x03 ) << 4)));
				// dest[didx++] = base64_table[(current[0] & 0x03) << 4];
				dest[didx++] =( byte) '=';
				dest[didx++] = ( byte)'=';
			}
		}


        // add padding
		/*
        for (int idx = dest.length-1; idx > (str.length()*4)/3; idx--)
            dest[idx] = ( byte ) '=';
		*/
	   int reallength = 0;
		for(int i=0;i<dest.length;i++)
		{
			reallength = i;
			int nzero = 0;
			if( (char)dest[i] == (char)nzero ) break;
		}
		return new String(dest, 0, reallength );

       //return new String(dest, 0, dest.length );

    }
	
   public static String getBase64Decode(String doc){
	   String str = doc;
		byte data[] = new byte[str.length()];
		// str.getBytes(0, str.length(), data, 0);
		data = str.getBytes();
		int tail = str.length();
		//if( tail == 0 ) return "";
		while (data[tail - 1] == '=')
			tail--;
		byte dest[] = new byte[tail - data.length / 4];
		// ascii printable to 0-63 conversion
		for (int idx = 0; idx < data.length; idx++) {
			if (data[idx] == '=')
				data[idx] = 0;
			else if (data[idx] == '/')
				data[idx] = 63;
			else if (data[idx] == '+')
				data[idx] = 62;
			else if (data[idx] >= '0' && data[idx] <= '9')
				data[idx] = (byte) (data[idx] - ('0' - 52));
			else if (data[idx] >= 'a' && data[idx] <= 'z')
				data[idx] = (byte) (data[idx] - ('a' - 26));
			else if (data[idx] >= 'A' && data[idx] <= 'Z')
				data[idx] = (byte) (data[idx] - 'A');
		}
		// 4-byte to 3-byte conversion
		int sidx, didx;
		for (sidx = 0, didx = 0; didx < dest.length - 2; sidx += 4, didx += 3) {
			dest[didx] = (byte) (((data[sidx] << 2) & 255) | ((data[sidx + 1] >>> 4) & 0x000f));
			dest[didx + 1] = (byte) (((data[sidx + 1] << 4) & 255) | ((data[sidx + 2] >>> 2) & 0x003f));
			dest[didx + 2] = (byte) (((data[sidx + 2] << 6) & 255) | (data[sidx + 3] & 077));
		}
		if (didx < dest.length && sidx + 1 < data.length)
			dest[didx] = (byte) (((data[sidx] << 2) & 255) | ((data[sidx + 1] >>> 4) & 0x000f));
		if (++didx < dest.length && sidx + 2 < data.length)
			dest[didx] = (byte) (((data[sidx + 1] << 4) & 255) | ((data[sidx + 2] >>> 2) & 0x003f));
		String decoded = new String(dest, 0, dest.length);
		return decoded;
   }
   
   public static void main(String[] args){
	   
	  String test = "p2";
	  String encoded = Main.GetBase64Encode(test);
	  String decoded = Main.getBase64Decode(encoded);
	  System.out.println(test+", "+encoded+", "+decoded);
      /*Scanner keyboard = new Scanner(System.in);

      long a;
      long b,c,d;

      a = keyboard.nextInt();
      b = keyboard.nextInt();
      c = keyboard.nextInt();
      d = keyboard.nextInt();
      
      long bigA = a < b ? b : a;
      long bigB = c < d ? d : c;
      long smallA = a > b ? b : a;
      long smallB = c > d ? d : c;
      
      if(bigA > bigB && smallA > smallB)
    	  System.out.println("cross");
      else if(bigA < bigB && smallA < smallB)
    	  System.out.println("cross");
      else if(bigA == bigB || smallA == smallB)
    	  System.out.println("cross");
      else
    	  System.out.println("not cross");*/
     
   }
}