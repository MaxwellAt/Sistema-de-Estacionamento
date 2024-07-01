package backend;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class GerenciadorEstacionamento {
    private Repositorio repositorio;
    private String filename;
    private List<Cliente> clientes;
    private double taxaPorHora = 10.0;

    public GerenciadorEstacionamento(String filename) {
        this.repositorio = Repositorio.loadFromJson(filename);
        this.filename = filename;
        this.clientes = new ArrayList<>();
        if (this.repositorio == null) {
            this.repositorio = new Repositorio();
        }
    }
    public void setTaxaPorHora(double taxaPorHora) {
        this.taxaPorHora = taxaPorHora;
        repositorio.saveToJson(filename);
    }

    public double getTaxaPorHora() {
        return taxaPorHora;
    }
    public Reserva criarReserva(Cliente cliente, Vaga vaga, int tempoReserva) {
        if (vaga.isReservada()) {
            // Lançar uma exceção ou retornar null
            return null;
        }

        vaga.reservar();
        return new Reserva(cliente, vaga, tempoReserva);
    }

    public void cadastrarVaga(int numero, String localizacao, String tipoVeiculo) {
        Vaga vaga = new Vaga(numero, localizacao, tipoVeiculo);
        repositorio.getVagas().add(vaga);
        repositorio.saveToJson(filename);
    }

    public void removerVaga(int numero) {
        for (Vaga vaga : repositorio.getVagas()) {
            if (vaga.getNumero() == numero) {
                repositorio.getVagas().remove(vaga);
                repositorio.saveToJson(filename);
                break;
            }
        }
    }

    public boolean entradaVeiculo(Veiculo veiculo, Date horarioEntrada) {
        for(Vaga vaga: repositorio.getVagas()){
            if(vaga.getStatus().equals("livre") && vaga.getTipoVeiculo().equals(veiculo.getTipo())){
                vaga.setEntrada(horarioEntrada);
                vaga.setVeiculo(veiculo);
                vaga.setStatus("ocupada");
                String mensagem = horarioEntrada + ": " + veiculo.getPlaca() + " ocupou a vaga " + vaga.getNumero();
                repositorio.getRelatorios().addHistoricoOcupacao(mensagem);
                repositorio.saveToJson(filename);
                return true;
            }
        }
        return false;
    }
    public Repositorio getRepositorio() {
        return repositorio;
    }
    public Veiculo buscarVeiculo(String placa) {
        // Verificar se a placa existe no mapa de placas
        if (repositorio.getRelatorios().getPlacas().containsKey(placa)) {
            // Se a placa existir, procurar o veículo nas vagas
            for (Vaga vaga : repositorio.getVagas()) {
                if (vaga.getVeiculo() != null && vaga.getVeiculo().getPlaca().equals(placa)) {
                    return vaga.getVeiculo();
                }
            }
        }
        return null;
    }
    public double saidaVeiculo(Veiculo veiculo, Date horarioSaida) {
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy HH:mm:ss");
        for(Vaga vaga: repositorio.getVagas()){
            if(vaga.getVeiculo() != null && vaga.getVeiculo().getPlaca().equals(veiculo.getPlaca())){
                long tempoEstacionado = horarioSaida.getTime() - vaga.getHoraEntrada().getTime();
                double valor = calcularValor(tempoEstacionado);
                vaga.setVeiculo(null);
                vaga.setStatus("livre");
                String mensagem = horarioSaida + ": " + veiculo.getPlaca() + " saiu da vaga " + vaga.getNumero();
                repositorio.getRelatorios().addHistoricoOcupacao(mensagem);
                repositorio.getRelatorios().addPlaca(veiculo.getPlaca(), mensagem);
                // Adicionar a transação ao histórico com data e hora
                String transacao = "Transação em " + sdf.format(horarioSaida) + ": " + valor + " R$ recebido de " + veiculo.getPlaca() + " na saída.";
                repositorio.getRelatorios().getTransacoesFinanceiras().addTransacao(transacao);
                repositorio.getRelatorios().getTransacoesFinanceiras().updateSaldo((double) valor);
                repositorio.saveToJson(filename);
                return valor;
            }
        }
        return 0.0;
    }

    public List<String> getRelatorioFinanceiroDiario(Date dia) {
        SimpleDateFormat sdf = new SimpleDateFormat("MMM. dd, yyyy HH:mm:ss", new Locale("en", "US"));
        List<String> transacoesDoDia = new ArrayList<>();

        for (String transacao : repositorio.getRelatorios().getTransacoesFinanceiras().getHistorico()) {
            try {
                String dataTransacaoStr = transacao.substring(13, 35); // extrai a data da transação
                Date dataTransacao = sdf.parse(dataTransacaoStr);

                if (isSameDay(dataTransacao, dia)) {
                    transacoesDoDia.add(transacao);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        return transacoesDoDia;
    }

    private boolean isSameDay(Date date1, Date date2) {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
        return fmt.format(date1).equals(fmt.format(date2));
    }
    public void cadastrarCliente(Cliente client) {
        Cliente cliente = new Cliente(client.getNome(), client.getTelefone(), client.getEmail());
        repositorio.addCliente(cliente);
        repositorio.saveToJson(filename);
    }
    public void atualizarCliente(Cliente clienteAtualizado) {
        Cliente clienteExistente = repositorio.getClientePorNome(clienteAtualizado.getNome());

        if (clienteExistente != null) {
            clienteExistente.setTelefone(clienteAtualizado.getTelefone());
            clienteExistente.setEmail(clienteAtualizado.getEmail());
            clienteExistente.setVeiculos(clienteAtualizado.getVeiculos());
            repositorio.saveToJson(filename);
        } else {
            throw new IllegalArgumentException("Cliente não encontrado: " + clienteAtualizado.getNome());
        }
    }
    public Cliente getClientePorNome(String nome) {
        for (Cliente cliente : repositorio.getClientes()) {
            if (cliente.getNome().equals(nome)) {
                return cliente;
            }
        }
        return null;
    }
    public List<Cliente> getClientes() {
        return repositorio.getClientes();
    }
    public void removerCliente(Cliente cliente) {
        clientes.remove(cliente);
        repositorio.removeCliente(cliente);
        repositorio.saveToJson(filename);
    }
    private double calcularValor(long tempoEstacionado) {
        double taxaPorHora = 10.0;
        double horasEstacionado = tempoEstacionado / 1000.0 / 60.0 / 60.0;
        return horasEstacionado * taxaPorHora;
    }
    public List<Vaga> getVagas() {
        return repositorio.getVagas();
    }

    public List<Reserva> getReservas() {
        return repositorio.getReservas();
    }

    public int proximoNumeroVaga() {
        if (repositorio.getVagas().isEmpty()) {
            return 1;
        } else {
            int maxNumero = 0;
            for (Vaga vaga : repositorio.getVagas()) {
                if (vaga.getNumero() > maxNumero) {
                    maxNumero = vaga.getNumero();
                }
            }
            return maxNumero + 1;
        }
    }

    public List<Vaga> getVagasDisponiveis() {
        List<Vaga> vagasDisponiveis = new ArrayList<>();
        for (Vaga vaga : this.getVagas()) {
            if (!vaga.isReservada() && !vaga.getStatus().equals("ocupada")) {
                vagasDisponiveis.add(vaga);
            }
        }
        return vagasDisponiveis;
    }
}