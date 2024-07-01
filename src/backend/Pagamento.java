package backend;

public class Pagamento {
    private double valor;
    private String formaPagamento;
    private Cliente cliente;
    private Veiculo veiculo;

    public Pagamento(double valor, String formaPagamento, Cliente cliente, Veiculo veiculo) {
        this.valor = valor;
        this.formaPagamento = formaPagamento;
        this.cliente = cliente;
        this.veiculo = veiculo;
    }

    // Getters and Setters
    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(String formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Veiculo getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(Veiculo veiculo) {
        this.veiculo = veiculo;
    }

}