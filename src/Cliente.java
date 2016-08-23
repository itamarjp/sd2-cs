import java.net.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Cliente {
    
     public static void main(String[] args) {

        try {
            Socket conexao = new Socket("127.0.0.1", 2001);
            DataInputStream entrada = new DataInputStream(conexao.getInputStream());
            DataOutputStream saida = new DataOutputStream(conexao.getOutputStream());
            String linha;
            BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));
            while (true) {
                System.out.println(">");
                linha = teclado.readLine();
                saida.writeUTF(linha);
                linha = entrada.readUTF();
                if(linha.equalsIgnoreCase("")){
                    System.out.println("Conexão encerrada!");
                    break;
                }
                System.out.println(linha);
            }
        } catch (IOException e) {
            System.out.println("IOException:" +e);
        }
    }
}
