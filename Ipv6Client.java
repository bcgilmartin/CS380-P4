import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;
import java.io.OutputStream;
import java.util.Hashtable;
import java.lang.String;
import java.lang.Byte;
import java.nio.ByteBuffer;
import java.math.BigInteger;
import java.net.InetSocketAddress;



public final class Ipv6Client {

    public static void main(String[] args) throws Exception {
		
		
		//connecting to socket and setup io
        try (Socket socket = new Socket("codebank.xyz", 38004)) {
			System.out.println("\nConnected to server.");
			OutputStream os = socket.getOutputStream();
			InputStream is = socket.getInputStream();
			InputStreamReader isr = new InputStreamReader(is, "UTF-8");
			BufferedReader br = new BufferedReader(isr);
			
			
			
			//default payload length
			int payloadLength = 1;
			
			
			
			//loop for sending 12 packets
			for(int i = 1; i < 13; i++) {
				
				payloadLength *= 2;
				System.out.println("Data Length: " + payloadLength);
			

				//create the packets 
				byte[] packets = createPacket(payloadLength);
				
				
				
				//send packets byte array
				os.write(packets);
				
				
				
				//receive and print out reponse
				System.out.println(Integer.toHexString(is.read()) + Integer.toHexString(is.read()) + Integer.toHexString(is.read()) + Integer.toHexString(is.read()) +"\n");
			}
        }
		System.out.println("Disconnected from server.");
    }
	
	
	
	//create byte[] packets for Ipv6 
	public static byte[] createPacket(int payloadLength) {
		byte[] message = new byte[payloadLength + 40];
		message[0] = (byte)96;
		message[1] = (byte)0;
		message[2] = (byte)0;
		message[3] = (byte)0;
		message[4] = (byte)(payloadLength >> 8);
		message[5] = (byte)payloadLength;
		message[6] = (byte)17;
		message[7] = (byte)20;
		for(int i = 8; i < 18; i++) {
			message[i] = (byte)0;
		}
		message[18] = (byte)255;
		message[19] = (byte)255;
		message[20] = (byte)134;
		message[21] = (byte)71;
		message[22] = (byte)249;
		message[23] = (byte)197;
		for(int i = 24; i < 34; i++) {
			message[i] = (byte)0;
		}
		message[34] = (byte)255;
		message[35] = (byte)255;
		message[36] = (byte)52;
		message[37] = (byte)37;
		message[38] = (byte)88;
		message[39] = (byte)154;
		for(int i = 40; i < (payloadLength + 40); i++) {
			message[i] = (byte)0; 
		}
		return message;
	}
	
	
}