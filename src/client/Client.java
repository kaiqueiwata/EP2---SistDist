package client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import client.Peers;
import client.UDPListener;

public class Client {
	public String nomeCliente;
	public int porta;
	public int portaTCP;
	public long tamanhoArquivo;
	public String IPClient;
	public ArrayList<Peers> peers;
	public String caminho;
	public int ttl;
	public int tempo;
	public long startTime = System.currentTimeMillis();
	public String nomeArquivo;
	
	public Client(String nome, String IPClient, String porta, String[] enderecos, String caminho, String ttl,
			String tempo, String portaTCP) {

		this.nomeCliente = nome;
		this.IPClient = IPClient;
		this.porta = Integer.parseInt(porta);
		this.caminho = caminho;
		this.portaTCP = Integer.parseInt(portaTCP);
		this.ttl = Integer.parseInt(ttl);
		this.tempo = Integer.parseInt(tempo);

		peers = new ArrayList<Peers>();

		for (int i = 0; i < enderecos.length; i++) {

			String nomePeer = enderecos[i].split(":")[0];
			String ipPeer = enderecos[i].split(":")[1];
			int portaPeer = Integer.parseInt(enderecos[i].split(":")[2]);

			Peers peer = new Peers(nomePeer, ipPeer, portaPeer);
			peers.add(peer);

		}

	}

	public Peers getPeerAleatorio() {

		Random rnd = new Random();
		int indice = rnd.nextInt(this.peers.size());
		return peers.get(indice);

	}

	public String NomeArquivo() {

		Scanner scan = new Scanner(System.in);
		System.out.println("Solicitacao de arquivo");
		System.out.println("Escreva o nome do arquivo desejado: ");
		String fileName = scan.nextLine();
		return fileName;

	}

	public void criaMensagem(String IPCliente, int portaCliente, int TTL) {

	    nomeArquivo = NomeArquivo();
		Peers peerReceptor = getPeerAleatorio();
		String msg = String.format("%s;%d;%s;%d;%d", IPCliente, portaCliente, nomeArquivo, TTL, portaTCP);

		System.out.printf("Console %s: pesquisando por %s no %s.\n", nomeCliente, nomeArquivo,
				peerReceptor.nomePeer);

		enviarMensagem(msg, peerReceptor.ipPeer, peerReceptor.portaPeer);

	}
	
	//cliente UDP
	private void enviarMensagem(String mensagem, String IPdestino, int portaDestino) {

		try {
			DatagramSocket clienteSocket = new DatagramSocket();
			InetAddress endereco = InetAddress.getByName(IPdestino);

			byte[] dados = new byte[1024];
			dados = mensagem.getBytes();

			DatagramPacket pacoteParaEnviar = new DatagramPacket(dados, dados.length, endereco, portaDestino);

			clienteSocket.send(pacoteParaEnviar);

			clienteSocket.close();
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void start() {
		TCPserver tcpServer = new TCPserver(this);
		tcpServer.start();
		UDPListener listen = new UDPListener(this);
		listen.start();
		while (true) {
			criaMensagem(IPClient, porta, ttl);
//			long elapsed = System.currentTimeMillis() - startTime;
//			if (elapsed >= tempo) {
//				System.out.printf("Console %s: Tempo esgotado para consulta do arquivo %s \n", nomeCliente, nomeArquivo);
//				break;
//			}
		}
	}

}
