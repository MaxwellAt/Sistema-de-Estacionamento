package backend;

public class Carro extends Veiculo {
    private String cor;

    public Carro(String placa, String marca, String modelo, String cor) {
        super(placa, "Carro");
        this.setMarca(marca);
        this.setModelo(modelo);
        this.cor = cor;
    }

}
