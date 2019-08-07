package client;

import java.net.DatagramPacket;
import java.net.DatagramSocket;




public class UDPListener extends Thread {
	
	int porta;
	String peerComArquivo[];
	
	public UDPListener(int porta) {
		this.porta = porta;	
	}
	@Override
	public void run() {
		try {
			DatagramSocket serverSocket = new DatagramSocket(porta);			
			System.out.println("ouvindo...");
			byte[] dadosRecebidos;
			while(true) {
				dadosRecebidos = new byte[1024];
				
				DatagramPacket pacoteRecebido = new DatagramPacket(dadosRecebidos, dadosRecebidos.length);
				
				serverSocket.receive(pacoteRecebido);
				
				String mensagemRecebida = new String(pacoteRecebido.getData());
				String[] mensagemInterpretada = mensagemRecebida.split(";");
				
				String peerIP = mensagemInterpretada[0];
				int peerPorta = Integer.parseInt(mensagemInterpretada[1].trim());
				peerComArquivo [0] = peerIP;
				peerComArquivo [1] = Integer.toString(peerPorta);
				
				
				
			}
			
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	
	
}
