package backend;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Repositorio {
    private List<Vaga> vagas;
    private List<Reserva> reservas;
    private Relatorios relatorios;
    private List<Cliente> clientes;


    public Repositorio() {
        this.vagas = new ArrayList<>();
        this.reservas = new ArrayList<>();
        this.relatorios = new Relatorios();
        this.clientes = new ArrayList<>();
    }

    public List<Vaga> getVagas() {
        return vagas;
    }

    public void addVaga(Vaga vaga) {
        this.vagas.add(vaga);
    }

    public List<Reserva> getReservas() {
        return reservas;
    }

    public void addReserva(Reserva reserva) {
        this.reservas.add(reserva);
    }
    public List<Cliente> getClientes() {
        return clientes;
    }

    public void addCliente(Cliente cliente) {
        this.clientes.add(cliente);
    }

    public Cliente getClientePorNome(String nome) {
        for (Cliente cliente : clientes) {
            if (cliente.getNome().equals(nome)) {
                return cliente;
            }
        }
        return null;
    }
    public void removeCliente(Cliente cliente) {
        clientes.remove(cliente);
    }
    public Relatorios getRelatorios() {
        return relatorios;
    }

    // Métodos para salvar e carregar dados em JSON usando Gson
    public void saveToJson(String filename) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create(); // Cria um objeto Gson com formatação bonita
        try (FileWriter writer = new FileWriter(filename)) { // Abre o arquivo JSON para escrita
            gson.toJson(this, writer); // Escreve o objeto Repositorio no arquivo JSON
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //    Metod necessario para corrigir o bug JsonIOException: 'Abstract classes can't be instantiated!' (R8)
    public static Repositorio loadFromJson(String filename) {
        Gson gson = new GsonBuilder() // Registra o deserializador personalizado para a classe Veiculo
                .registerTypeAdapter(Veiculo.class, new VeiculoDeserializer()) // Registra o serializador personalizado para a classe Veiculo
                .create(); // Cria um objeto Gson


        try (FileReader reader = new FileReader(filename)) { // Abre o arquivo JSON para leitura
            return gson.fromJson(reader, Repositorio.class); // Lê o arquivo JSON e cria um objeto Repositorio
        } catch (IOException e) { // Trata exceções de I/O
            e.printStackTrace();
            return null;
        }
    }
    // Classe Relatorios
    public static class Relatorios {
        private List<String> todoHistoricoOcupacao;
        private Map<String, List<String>> placas;
        private TransacoesFinanceiras transacoesFinanceiras;

        public Relatorios() {
            this.todoHistoricoOcupacao = new ArrayList<>();
            this.placas = new HashMap<>();
            this.transacoesFinanceiras = new TransacoesFinanceiras();
        }

        public List<String> getTodoHistoricoOcupacao() {
            return todoHistoricoOcupacao;
        }

        public void addHistoricoOcupacao(String historico) {
            this.todoHistoricoOcupacao.add(historico);
        }

        public Map<String, List<String>> getPlacas() {
            return placas;
        } // Adiciona uma placa ao mapa de placas

        public void addPlaca(String placa, String entradaSaida) { // Recebe uma placa e uma string com a entrada e saída
            // Cada placas tem uma lista de entradas e saídas
            this.placas.computeIfAbsent(placa, k -> new ArrayList<>()).add(entradaSaida); // Adiciona a placa ao mapa de placas
            // Se a placa já existir, adiciona a entrada e saída à lista de entradas e saídas
            // Se a placa não existir, cria uma nova lista de entradas e saídas e adiciona a entrada e saída
        }

        public TransacoesFinanceiras getTransacoesFinanceiras() {
            return transacoesFinanceiras;
        }

        // Classe TransacoesFinanceiras
        public static class TransacoesFinanceiras {
            private List<String> historico;
            private double saldo;

            public TransacoesFinanceiras() {
                this.historico = new ArrayList<>();
                this.saldo = 0;
            }

            public List<String> getHistorico() {
                return historico;
            }

            public void addTransacao(String transacao) {
                this.historico.add(transacao);
            }

            public double getSaldo() {
                return saldo;
            }

            public void updateSaldo(double valor) {
                this.saldo += valor;
            }
        }
    }
}