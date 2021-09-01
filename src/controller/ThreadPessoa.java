package controller;

import java.util.concurrent.Semaphore;

public class ThreadPessoa extends Thread{
	private int id;
	private static int ingressos;
	private Semaphore semaphore;
	private int qtdIngressosComprar;
	
	public ThreadPessoa(int id, int ingressos,Semaphore semaphore) {
		this.id = id+1;
		ThreadPessoa.ingressos = ingressos;
		this.semaphore = semaphore;
	}
	
	@Override
	public void run() {
		if(fazerLogin()) {
			if (comprar()) {
				try {
					semaphore.acquire();
					validarCompra();
				} catch (Exception e) {
					e.printStackTrace();
				}finally {
					semaphore.release();
				}				
			}
		}
	}
	
	private boolean fazerLogin() {
		boolean loginRealizadoEmTempo = false;
		int tempoLogin =(int) ((Math.random()*1951)+50);
		try {
			this.sleep(tempoLogin);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(tempoLogin>1000){
				System.err.println("O comprador #"+this.id+" demorou " + tempoLogin + "ms para fazer o login e n�o "
										+ "poder� realizar a compra por ter esgotado o tempo m�ximo");
			}else {
				loginRealizadoEmTempo = true;
			}
		}
		return loginRealizadoEmTempo;
	}
	
	private boolean comprar() {
		boolean tempoDeCompraFoiSuficiente = false;
		int tempoDeCompra = (int) ((Math.random()*2001)+1000);
		qtdIngressosComprar = (int) ((Math.random()*4)+1);
		try {
			this.sleep(tempoDeCompra);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(tempoDeCompra>2500) {
				System.err.println("O comprador #"+this.id+" demorou " + tempoDeCompra + "ms para comprar os ingressos "
										+ "e n�o poder� realizar a compra por ter esgotado  o tempo de sess�o");
			}else {
				tempoDeCompraFoiSuficiente = true;
			}
		}
		return tempoDeCompraFoiSuficiente;
	}
	
	private void validarCompra() {	
		if(ThreadPessoa.ingressos >= qtdIngressosComprar) {
			ThreadPessoa.ingressos -= qtdIngressosComprar;
			System.out.println("\nO comprador #"+this.id+" comprou "+qtdIngressosComprar+" ingressos \n"
								+ "Ainda existem "+ingressos+" ingressos dispon�veis para compra\n");
		}else {
			System.err.println("N�o h� ingressos dispon�veis para o comprador #"+this.id);
		}
	}
}
