package client;

import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPserver extends Thread{
	private Client cliente;
	private int count = 0;
	public TCPserver(Client cliente) {
		this.cliente = cliente;
	}
	
	@Override
	public void run(){
		while(true) {
			String caminhoFinal = cliente.caminho + "\\arquivo" +  (count++) + ".mp4" ;
			try {
				ServerSocket serverSocket = new ServerSocket(cliente.portaTCP);
				Socket socket = serverSocket.accept();
				DataInputStream dis = new DataInputStream(socket.getInputStream());
				System.out.println("CAMINHO FINAL: "+caminhoFinal);
				FileOutputStream fos = new FileOutputStream(caminhoFinal);
				byte[] buffer = new byte[4096];
				
				long tamanhoArquivo = cliente.tamanhoArquivo;
				long leitura = 0;
				long totalLeitura = 0;
				long restante = tamanhoArquivo;
				while((leitura = dis.read(buffer, 0, (int) Math.min(buffer.length, restante))) > 0) {
					totalLeitura += leitura;
					restante -= leitura;
//					System.out.println("leitura " + totalLeitura + " bytes");
					fos.write(buffer, 0, (int) leitura);
				}
				System.out.printf("Console %s: DOWNLOAD FINALIZADO\n", cliente.nomeCliente);
				fos.close();
				dis.close(); 
				serverSocket.close();
			}
			catch(IOException ex) {
				
			}
		}
	}
}

