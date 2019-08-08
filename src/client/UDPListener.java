package client;

import java.net.DatagramPacket;
import java.net.DatagramSocket;




public class UDPListener extends Thread {
	
	int porta;
	String caminho;
	String nomeArquivo;
	private Client cliente;
	
	public UDPListener(Client cliente) {
		this.cliente = cliente;
		this.porta = cliente.porta;	
		this.caminho = cliente.caminho;
	}
	
	@Override
	public void run() {		
		boolean running = true;
		try {
			System.out.println(porta);
			DatagramSocket serverSocket = new DatagramSocket(porta);			
			System.out.println("ouvindo...");
			byte[] dadosRecebidos;
			while(running) {
				dadosRecebidos = new byte[1024];
				
				DatagramPacket pacoteRecebido = new DatagramPacket(dadosRecebidos, dadosRecebidos.length);
				
				serverSocket.receive(pacoteRecebido);
				
				String mensagemRecebida = new String(pacoteRecebido.getData());
			
				long tamanhoArquivo = Integer.parseInt(mensagemRecebida.trim());
				
				cliente.tamanhoArquivo = tamanhoArquivo;

			}
			serverSocket.close();
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		
	}
	
	
	
}
