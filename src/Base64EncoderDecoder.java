import java.io.UnsupportedEncodingException;
import java.util.*;

public class Base64EncoderDecoder {
	
	public static void main(String args[]) {
		testBase64();
		
	}
	
	public static void testBase64(){
		Base64EncoderDecoder example = new Base64EncoderDecoder();
		example.print();
	}
	
	char[] base64Table = { '=', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R',
			'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
			'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7',
			'8', '9', '+', '/' };

	public void print() {
		String test = "Man";
		System.out.println(encode(test));

		String test2 = "TWFu";
		System.out.println(decode(test2));
	}

	public String decode(String test) {
		Queue<Byte> index = findDecodingIndex(test);
		String bitPattern = findDecodedBitPattern(index);
		StringBuilder result = new StringBuilder();
		for (int i = 0; i < bitPattern.length(); i += 8) {
			result.append( (char) Byte.parseByte(bitPattern.substring(i, i + 8), 2));
		}
		return result.toString();
	}
	
	public Queue<Byte> findDecodingIndex(String test) {
		Queue<Byte> index = new LinkedList<Byte>();
		for (int i = 0; i < test.length(); i++) {
			for (int j = 0; j < base64Table.length; j++) {
				if (test.charAt(i) == base64Table[j]) {
					index.add((byte) j);
				}
			}
		}
		return index;
	}
	
	public String findDecodedBitPattern(Queue<Byte> index){
		StringBuilder result = new StringBuilder();
		while (!index.isEmpty()) {
			result.append(String.format("%6s", Integer.toBinaryString(index.remove())).replace(" ", "0"));
		}
		return result.toString();
	}

	public String encode(String test) {
		String bitPattern = findBitPattern(test);
		String checkBitPattern = checkBitPattern(bitPattern);
		Queue<String> index = findEncodingIndex(checkBitPattern);

		StringBuilder result = new StringBuilder();

		while (!index.isEmpty()) {
			result.append(base64Table[Byte.parseByte(index.remove(), 2)]);
		}
		return result.toString();
	}

	public String findBitPattern(String test) {
		byte[] input;
		String bitPattern = "";
		try {
			input = test.getBytes("US-ASCII");
			for (int i = 0; i < input.length; i++) {
				bitPattern += String.format("%8s", Integer.toBinaryString(input[i])).replace(" ", "0");
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return bitPattern;
	}

	public String checkBitPattern(String bitPattern) {
		while (bitPattern.length() % 24 != 0) {
			bitPattern += "0";
		}
		return bitPattern;
	}

	public Queue<String> findEncodingIndex(String bitPattern) {
		Queue<String> index = new LinkedList<String>();
		for (int i = 0; i < bitPattern.length(); i += 6) {
			index.add(bitPattern.substring(i, i + 6));
		}
		return index;
	}

}
