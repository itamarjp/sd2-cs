package clienteServidor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Cliente extends Thread {
    
    //quando eu quero conversar, para parar de conversar vou enviar pela thread true
    private static boolean done = false;
    //Objeto do tipo socket x
    private Socket conexao;
    
    public Cliente(Socket s){
        conexao =s;
    }
    
    public static void main(String[] args){
        
        try {
            Socket conexao = new Socket("127.0.0.1",2000);
            PrintStream saida = new PrintStream(conexao.getOutputStream());
            
            BufferedReader teclado = new BufferedReader( new InputStreamReader(System.in));
            
            System.out.print("Entre com o seu nome: ");
            String meuNome = teclado.readLine();
            saida.println(meuNome);
            //enviar o nome para o servidor
            
            Thread t = new Cliente(conexao);
            //Startei a thread e criei a variavel
            t.start();
            String linha;
            
            //Quando o done passar para verdadeiro.
            while(!done){
                System.out.println("> ");;
                linha = teclado.readLine();
                saida.println(linha);
                
            }
        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
  public void run(){
        
        BufferedReader entrada = null;
        try {
            entrada = new BufferedReader(new InputStreamReader(conexao.getInputStream()));
            String linha;
            while(true){
                linha = entrada.readLine();
                if(linha.trim().equals("")){
                    System.out.print("Conexão encerrada!!!");
                    break;
                }
                System.out.println();
                System.out.println(linha);
                System.out.print("...> ");
            }
        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                entrada.close();
            } catch (IOException ex) {
                Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        done = true;
    }
}
