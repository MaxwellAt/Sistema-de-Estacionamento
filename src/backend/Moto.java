package backend;

public class Moto extends Veiculo {
    private int cilindradas;

    public Moto(String placa, String marca, int cilindradas) {
        super(placa, "Moto");
        this.setMarca(marca);
        this.cilindradas = cilindradas;
    }

    public int getCilindradas() {
        return cilindradas;
    }

    public void setCilindradas(int cilindradas) {
        this.cilindradas = cilindradas;
    }

}