package backend;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Relatorio {
    public void registrarOcupacao(Vaga vaga, boolean ocupada) {
        String status = ocupada ? "ocupada" : "desocupada";
        String relatorio = "A vaga " + vaga.getNumero() + " foi " + status + ".\n";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("relatórios de ocupação.txt", true))) {
            writer.write(relatorio);
        } catch (IOException e) {
            System.out.println("Ocorreu um erro ao escrever no arquivo 'relatórios de ocupação.txt'.");
            e.printStackTrace();
        }
    }
}