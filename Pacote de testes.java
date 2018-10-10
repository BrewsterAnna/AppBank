package testes;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

import negocio.Conta;

public class Testa_Banco {
	public static final String NOMEARQ = "F:\\contas.txt";
	private static List<Conta> listaContas = new ArrayList<Conta>();
	private static Scanner in = new Scanner(System.in);

	public static void main(String[] args) {
		processa();

	}

	private static void processa() {
		int opcao = 0;
		
		lerContas(listaContas);
		do {
			opcao = menu();
			switch (opcao) {
			case 1:
				incluir();
				break;
			case 2:
				alterar();
				break;
			case 3:
				excluir();
				break;
			case 4:
				relatorios();
				break;
			case 5:
				fim();
				break;
			default:
				System.out.printf("%d opcão inválida!", opcao);
				break;
			}
		} while (opcao != 5);
	}

	private static void relatorios() {
		int opcao = 0;
		do {
			opcao = menuRelatorios();
			switch (opcao) {
			case 1:
				saldoNegativo();
				break;
			case 2:
				saldoAcimaValor();
				break;
			case 3:
				todasContas(listaContas);
				break;
			case 4:
				maioresValores();
				break;
			case 5:
				voltar();
				break;
			default:
				System.err.printf("%d opção inválida!\n", opcao);
				break;
			}
		} while (opcao != 5);
	}

	private static void voltar() {

	}

	private static void maioresValores() {
		float[] maiorValor = new float[3];

		if (listaContas.size() < 3) {
			System.out.println("Não é possivel fazer a comparação pois não existem 3 contas!");
		} else {
			System.out.println("Os três maiores valores: ");
			for (int i = 0; i < listaContas.size(); i++) {
				if (listaContas.get(i).getCorrente() > maiorValor[0]) {
					maiorValor[0] = listaContas.get(i).getCorrente();
				}
			}
			for (int i = 0; i < listaContas.size(); i++) {
				if ((listaContas.get(i).getCorrente() > maiorValor[1])
						&& (listaContas.get(i).getCorrente() != maiorValor[0])) {
					maiorValor[1] = listaContas.get(i).getCorrente();
				}
			}
			for (int i = 0; i < listaContas.size(); i++) {
				if ((listaContas.get(i).getCorrente() > maiorValor[2])
						&& (listaContas.get(i).getCorrente() != maiorValor[0])
						&& (listaContas.get(i).getCorrente() != maiorValor[1])) {
					maiorValor[2] = listaContas.get(i).getCorrente();
				}
			}
			for (int i = 0; i < maiorValor.length; i++) {
				System.out.printf("%.2f \n", maiorValor[i]);
			}
			System.out.printf("\n");
		}
	}

	private static void todasContas(List<Conta> listaContas) {
		System.out.printf("\n");
		System.out.printf("# LISTA DE CLIENTES CADASTRADOS #\r\n");
		for (Conta conta : listaContas) {
			System.out.print(conta.getNumero() + " ");
			System.out.print(conta.getNome() + " ");
			System.out.print(conta.getCorrente() + " ");
			System.out.println(conta.getPoupanca() + " ");
			System.out.print("");
		}
		System.out.printf("\n");
	}

	private static void saldoAcimaValor() {
		System.out.println("Informe o valor: ");
		float valor = in.nextFloat();

		System.out.println(" ");
		System.out.printf("Saldos acima do valor inserido: %.2f\n", valor);

		for (int i = 0; i < listaContas.size(); i++) {
			if (listaContas.get(i).getCorrente() > valor || listaContas.get(i).getPoupanca() > valor) {
				System.out.println(listaContas.get(i));
			}
		}
		System.out.println(" ");
	}

	private static void saldoNegativo() {
		System.out.println(" ");
		System.out.println("Saldo Negativo:");

		for (int i = 0; i < listaContas.size(); i++) {
			if (listaContas.get(i).getCorrente() < 0 || listaContas.get(i).getPoupanca() < 0) {
				System.out.println(listaContas.get(i));
			}
		}
		System.out.println(" ");
	}

	private static void incluir() {
		todasContas(listaContas);
		int num;

		System.out.print("Entre com número da conta: ");
		num = in.nextInt();
		if (!existeConta(num)) {
			Conta c = new Conta();

			c.setNumero(num);

			System.out.print("Nome do cliente: ");
			c.setNome(in.next());

			System.out.print("Saldo da conta corrente: ");
			c.setCorrente(in.nextFloat());
			if (c.getCorrente() <= 0) {
				do {
					System.out.println("Saldo menor ou igual a zero não pode ser inserido!");
					System.out.print("Digite o valor novamente: ");
					c.setCorrente(in.nextFloat());
				} while (c.getCorrente() <= 0);
			}
			System.out.print("Saldo da conta poupança: ");
			c.setPoupanca(in.nextFloat());
			if (c.getPoupanca() <= 0) {
				do {
					System.out.println("Saldo menor ou igual a zero não pode ser inserido!");
					System.out.print("Digite o valor novamente: ");
					c.setPoupanca(in.nextFloat());
				} while (c.getPoupanca() <= 0);
			}
			listaContas.add(c);
			System.out.println("Cliente inclúido com sucesso!\n");
			todasContas(listaContas);
		} else {
			System.out.println("Número de conta duplicado!\n");
		}
	}

	private static void alterar() {
		int num;

		System.out.print("Entre com o número da conta: ");
		num = in.nextInt();
		if (existeConta(num)) {
			for (int i = 0; i < listaContas.size(); i++) {
				Conta conta = listaContas.get(i);
				if (num == listaContas.get(i).getNumero()) {
					conta.setNumero(num);
					System.out.print("Nome do cliente: ");
					conta.setNome(in.next());
					System.out.print("Saldo da conta corrente: ");
					conta.setCorrente(in.nextFloat());
					System.out.print("Saldo da conta poupança: ");
					conta.setPoupanca(in.nextFloat());
					System.out.println("Conta alterada com sucesso!\n");
					todasContas(listaContas);
					return;
				}
			}
		} else {
			System.out.println("Conta não existe!");
		}
	}

	private static void excluir() {
		todasContas(listaContas);
		int num;

		System.out.print("Entre com o número da conta que deseja deletar: ");
		num = in.nextInt();
		for (int i = 0; i < listaContas.size(); i++) {
			Conta conta = listaContas.get(i);
			if (!existeConta(num)) {
				System.out.println("Conta não existe!\n");
				break;
			}
			if (num == conta.getNumero()) {
				if (conta.getCorrente() == 0 && conta.getPoupanca() == 0) {
					System.out.println("conta excluida com sucesso!");
					listaContas.remove(i);
				} else {
					System.out.println("Não pode excluir conta com saldo!\n");
				}
				todasContas(listaContas);
				break;
			}
		}
	}

	public static void fim() {
		System.out.println("Fim da operação");
		try {
			escrever();
			todasContas(listaContas);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static boolean existeConta(int num) {
		for (Conta c : listaContas) {
			if (num == c.getNumero()) {
				return true;
			}
		}
		return false;
	}

	private static int menuRelatorios() {
		int opcao;
		System.out.println("# MENU DE RELATÓRIOS #");
		System.out.println("1 - Saldo Negativo");
		System.out.println("2 - Saldo Acima de um valor");
		System.out.println("3 - Todas as contas");
		System.out.println("4 - Três maiores valores");
		System.out.println("5 - Voltar");
		System.out.print("Escolha uma opção: ");
		opcao = in.nextInt();
		return opcao;
	}

	private static int menu() {
		int opcao;
		
		System.out.println("# MENU #");
		System.out.println("1 - Inclusão");
		System.out.println("2 - Alteração de cliente");
		System.out.println("3 - Exclusão de cliente");
		System.out.println("4 - Relatórios gerenciais");
		System.out.println("5 - Sair");
		System.out.print("Escolha uma opção: ");
		opcao = in.nextInt();
		System.out.printf("\n");
		return opcao;

	}

	public static void escrever() throws IOException {
		BufferedWriter buffWrite = new BufferedWriter(new FileWriter(NOMEARQ));

		for (int i = 0; i < listaContas.size(); i++) {
			Conta conta = listaContas.get(i);
			buffWrite.write(conta.getNumero() + " " + conta.getNome() + " " + conta.getCorrente() + " "
					+ conta.getPoupanca() + " " + "\r\n");
		}

		buffWrite.flush();
		buffWrite.close();
	}

	private static int lerContas(List<Conta> contas) {
		Scanner ler = abreArquivo(NOMEARQ);
		Conta conta;
		int cont = -1;

		try {
			while (ler.hasNext()) {
				conta = new Conta();
				conta.setNumero(ler.nextInt());
				conta.setNome(ler.next());
				conta.setCorrente(ler.nextFloat());
				conta.setPoupanca(ler.nextFloat());
				contas.add(conta);
			}

		} catch (NoSuchElementException erro) {
			System.out.println("Erro: formatacao do arquivo");
		} catch (IllegalStateException erro) {
			System.out.println("Erro: leitura do arquivo");
		}
		fechaArquivo(ler);

		return cont;
	}

	private static Scanner abreArquivo(String arq) {
		Scanner ler = null;

		try {
			ler = new Scanner(new File(arq));
		} catch (FileNotFoundException erro) {
			System.out.println("Erro: arquivo nao existe");
			System.exit(0);
		}
		return ler;
	}

	private static void fechaArquivo(Scanner ler) {
		if (ler != null) {
			ler.close();
		}
	}

}
