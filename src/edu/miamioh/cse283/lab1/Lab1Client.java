package edu.miamioh.cse283.lab1;

import java.io.*;
import java.net.*;

/**
 * Template client for CSE283 Lab1, FS2014.
 * 
 * This client should read a remote address and one integer from the command
 * line, and send a single datagram packet containing that integer to the
 * remote address on port 4242.  The client should then check for a response
 * from the server, which will also be a single integer in a datagram packet.
 * Both integers should be echoed to the console.
 * 
 * @author dk
 */
public class Lab1Client {
	/** Buffer size for packets received from the server. */
	public static final int BUFFER_SIZE=256;
	
	/** Port on which the server will be listening. */
	public static final int PORT=4242;

	/**
	 * Runs the Lab1Client.
	 * 
	 * @param args is an array containing each of the command-line arguments.
	 * @throws IOException if there is a networking error.
	 */
	public static void main(String[] args) throws IOException {
		if (args.length != 2) {
			System.out.println("Usage: java Lab1Client <inet address> <integer>");
			return;
		}

		// Construct a socket to use for communication (see: DatagramSocket):
		
		
		DatagramSocket s = new DatagramSocket();

		try {			
			// pack the integer into a byte array (see: ByteArrayOutputStream, DataOutputStream):
			int t=Integer.parseInt(args[1]); // get from args[1]
			
			ByteArrayOutputStream b = new ByteArrayOutputStream(BUFFER_SIZE);
			DataOutputStream d = new DataOutputStream(b);
			d.writeInt(t);
			byte[] tbyte = b.toByteArray();

			// build a DatagramPacket containing integer t:
			
			InetAddress i = InetAddress.getByName(args[0]);
			DatagramPacket tpack = new DatagramPacket(tbyte, tbyte.length);
			
			// send the packet to address args[0] on port PORT (see: InetAddress):
			s.connect(i, PORT);			
			s.send(tpack);
			
			// echo it to the console (don't change this):
			System.out.print("sent: " + t);
			
			// receive a response (see: DataInputStream, ByteArrayInputStream):
			s.receive(tpack);
			ByteArrayInputStream input = new ByteArrayInputStream(tpack.getData());
			DataInputStream in = new DataInputStream(input);
			
			
			// echo to console (don't change this either):
			int r=in.readInt(); // get from packet
			System.out.println("; received: " + r);

		} finally {
			s.close();
			// close the socket:
		}
	}
}
