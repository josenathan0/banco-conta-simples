package chatbot;
import banco.entidades.*;
import banco.entidades.util.Data;
import banco.entidades.util.RecebeDados;
import banco.entidades.Conta;
import java.util.Scanner;
import java.util.Iterator;

public class CaixaChatBox {
    public static void main(String [] args) {
        Scanner scan = new Scanner(System.in);
        boolean sair = false;
        final int CRIAR = 1;
        final int LOCALIZAR = 2;
        System.out.println("Contas existentes: ");
        Iterator it = Agencia.getContas();
        while (it.hasNext())
            System.out.println(it.next());
        System.out.println();

        final String prompt = ("Digite a opção desejada: \n" + CRIAR + " - Criar: \n" + LOCALIZAR + " - Localizar: \n");
        int opcao1;
        Conta conta = null;
        Pessoa pessoa;

        do {

            opcao1 = RecebeDados.recebeInteiro(prompt);
        }
        while (opcao1 != CRIAR && opcao1 != LOCALIZAR);
        Agencia agencia = new Agencia();
        agencia.abrirCaixa();

        if(opcao1 == 1){
            System.out.println("Digite seu nome completo: ");
            String nome = scan.nextLine();
            System.out.println("Digite seu CPF: ");
            String cpf = scan.nextLine();
            pessoa = new Pessoa(nome, cpf);
            Conta contaCriada = new ContaSimples(pessoa);
            agencia.addConta(contaCriada);
            System.out.println("O numero da sua conta e: " + contaCriada.getNúmero() + "\n Anote o numero de sua conta.");
            conta = contaCriada;}
        if(opcao1 == 2){
            System.out.println("Digite o numero de sua conta: ");
            long identificador = scan.nextLong();
            conta = Agencia.localizarConta(identificador);
            if (conta == null) {
                System.err.println("Conta nao localizada: ");
                System.exit(0);
            }
            System.out.println(conta.getNome());}
        int escolha = 0;
        while(escolha != 6) {
            final int DEPOSITAR = 1, SACAR = 2, EXTRATO = 3, TRANSFERIR = 4, SALDO = 5, SAIR = 6;
            System.out.println(+ DEPOSITAR + " - Depositar: \n" + SACAR+ " - SACAR: \n"+ EXTRATO+ " - Extrato: \n" + TRANSFERIR + " - Transferir \n" + SALDO + " - Saldo: \n" + SAIR + " - Sair: \n");
            escolha = scan.nextInt();
            switch (escolha) {
                case DEPOSITAR:
                    System.out.println("Digite quanto deseja depositar: ");
                    double valorDeposito = scan.nextDouble();
                    conta.depositar(valorDeposito);
                    break;
                case SACAR:
                    System.out.println("Digite quanto deseja sacar: ");
                    double valorSaque = scan.nextDouble();
                    conta.sacar(valorSaque);
                    break;
                case EXTRATO:
                    System.out.println("Digite a data Inicial: DD/MÊS/ANO");
                    int diaInicial = scan.nextInt(), mesInicial = scan.nextInt(), anoInicial = scan.nextInt();
                    Data datainicial = new Data(diaInicial, mesInicial, anoInicial);
                    scan.nextLine();
                    System.out.println("Digite a data Final: DD/MÊS/ANO");
                    int diaFinal = scan.nextInt(), mesFinal = scan.nextInt(), anoFinal = scan.nextInt();
                    Data dataFinal = new Data(diaFinal, mesFinal, anoFinal);
                    System.out.println(conta.criarExtrato(datainicial,dataFinal));
                    break;
                case TRANSFERIR:
                    System.out.println("Digite o número da pessoa que você deseja transferir: ");
                    int numeroID = scan.nextInt();
                    scan.nextLine();
                    System.out.println("Digite o valor que deseja transferir: ");
                    double valor = scan.nextDouble();
                    conta.transferir(agencia.localizarConta(numeroID), valor);
                    break;
                case SALDO:

                    System.out.println("O seu saldo é de "+ conta.getSaldo());
                    break;

                case SAIR:
                    System.out.println("O seu saldo é de "+ conta.getSaldo());
                    System.out.println("Operação finalizada!");
                    break;
            }
        } agencia.fecharCaixa();
    }
}
