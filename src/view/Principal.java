package view;

import java.util.concurrent.Semaphore;

import controller.ThreadPessoa;

public class Principal {
	public static void main(String[] args) {
		int qtdCompradores = 300;
		int qtdIngressos = 100;
		int permissao = 1;
		
		ThreadPessoa pessoas[] = new ThreadPessoa[qtdCompradores];
		Semaphore semaphore = new Semaphore(permissao);
		
		for (int i = 0; i < pessoas.length; i++) {
			pessoas[i] = new ThreadPessoa(i, qtdIngressos,semaphore);
		}
		
		for (int i = 0; i < pessoas.length; i++) {
			pessoas[i].start();
		}
	}
}
