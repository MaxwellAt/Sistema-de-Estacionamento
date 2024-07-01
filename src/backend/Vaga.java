package backend;
import java.util.Date;
public class Vaga {
    private int numero;
    private String localizacao;
    private String status;
    private String tipoVeiculo;
    private Veiculo veiculo;
    private Date entrada;
    private boolean reservada;

    public Vaga(int numero, String localizacao, String tipoVeiculo) {
        this.numero = numero;
        this.localizacao = localizacao;
        this.tipoVeiculo = tipoVeiculo;
        this.status = "livre";
    }

    public String toString() {
        return localizacao + " - " + numero + " - " + status + " - " + tipoVeiculo;
    }
    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTipoVeiculo() {
        return tipoVeiculo;
    }

    public void setTipoVeiculo(String tipoVeiculo) {
        this.tipoVeiculo = tipoVeiculo;
    }

    public Veiculo getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(Veiculo veiculo) {
        this.veiculo = veiculo;
    }

    public boolean setEntrada(Date entrada) {
        this.entrada = entrada;
        return true;
    }
    public Date getHoraEntrada() {
        return entrada;
    }

    public void reservar() {
        this.status = "reservada";
        this.reservada = true;
    }

    public void liberar() {
        this.status = "livre";
        this.reservada = false;
    }

    public boolean isReservada() {
        return reservada;
    }
}