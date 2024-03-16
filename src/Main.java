import java.util.Scanner;
import java.util.StringTokenizer;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Digite o IP incluindo os pontos, EXEMPLO: 192.168.1.0/34: ");
        String input = scanner.nextLine();

        StringTokenizer tokenizer = new StringTokenizer(input, "/");

        // Obtendo o número IP e a máscara
        String numero = "";
        String mascara = "";
        if (tokenizer.countTokens() == 2) {
            numero = tokenizer.nextToken();
            mascara = "/" + tokenizer.nextToken(); // Mantém a máscara no formato "/n"
        } else {
            System.out.println("Formato inválido! Forneça o número IP seguido da máscara separados por '/'");
            return;
        }

        // Processamento do número IP
        StringTokenizer ipTokenizer = new StringTokenizer(numero, ".");
        String[] octetos = new String[4];
        int numOctetosValidos = 0;
        int i = 0;
        while (ipTokenizer.hasMoreTokens() && i < 4) {
            String octeto = ipTokenizer.nextToken();
            try {
                int valor = Integer.parseInt(octeto);
                if (valor < 0 || valor > 255) {
                    continue;
                }
                octetos[i] = octeto;
                numOctetosValidos++;
                i++;
            } catch (NumberFormatException e) {
            }
        }

        // Verificação final do número de octetos
        if (numOctetosValidos != 4) {
            System.out.println("Endereço IP inválido!");
            return;
        }

        // Verificação da classe do IP
        String classeIP = "";
        int primeiroOcteto = Integer.parseInt(octetos[0]);
        if (primeiroOcteto >= 0 && primeiroOcteto <= 127) {
            classeIP = "Classe A";
        } else if (primeiroOcteto >= 128 && primeiroOcteto <= 191) {
            classeIP = "Classe B";
        } else {
            classeIP = "Classe C";
        }

        // Verificação da máscara
        int prefixo = Integer.parseInt(mascara.substring(1));
        boolean mascaraValida = false;
        switch (classeIP) {
            case "Classe A":
                if (prefixo == 8) {
                    mascaraValida = true;
                }
                break;
            case "Classe B":
                if (prefixo == 16) { // Alterado para considerar a máscara completa
                    mascaraValida = true;
                }
                break;
            case "Classe C":
                if (prefixo == 24) { // Alterado para considerar a máscara completa
                    mascaraValida = true;
                }
                break;
        }

        // Exibir mensagens de erro e informações
        if (mascaraValida) {
            System.out.println("Endereço IP: " + numero);
            System.out.println("Classe do IP: " + classeIP);
            System.out.println("Máscara informada: " + mascara);
            System.out.println("Máscara válida para a classe do IP!");
        } else {
            System.out.println("Endereço IP: " + numero);
            System.out.println("Classe do IP: " + classeIP);
            System.out.println("Máscara informada: " + mascara);
            System.out.println("Máscara inválida para a classe do IP!");
            System.out.println("A máscara correta para a classe " + classeIP + " é: " + getMascaraPadrao(classeIP));
        }
    }

    private static String getMascaraPadrao(String classeIP) {
        switch (classeIP) {
            case "Classe A":
                return "/8";
            case "Classe B":
                return "/16";
            case "Classe C":
                return "/24";
            default:
                return "";
        }
    }
}
