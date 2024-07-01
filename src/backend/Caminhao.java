package backend;

public class Caminhao extends Veiculo {
    private double cargaMaxima;
    private double comprimento;

    public Caminhao(String placa, double cargaMaxima, double comprimento) {
        super(placa, "Caminhao");
        this.cargaMaxima = cargaMaxima;
        this.comprimento = comprimento;
    }

    public double getCargaMaxima() {
        return cargaMaxima;
    }

    public void setCargaMaxima(double cargaMaxima) {
        this.cargaMaxima = cargaMaxima;
    }

    public double getComprimento() {
        return comprimento;
    }

    public void setComprimento(double comprimento) {
        this.comprimento = comprimento;
    }

    public int getCilindradas() {
        return 0;
    }
}