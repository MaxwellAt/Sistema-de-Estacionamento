package backend;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class Reserva {
    private Cliente cliente;
    private Vaga vaga;
    private transient Timer timer; // Mark this field as transient

    public Reserva(Cliente cliente, Vaga vaga, int tempoReserva) {
        this.cliente = cliente;
        this.vaga = vaga;
        this.vaga.reservar();

        this.timer = new Timer();
        this.timer.schedule(new TimerTask() {
            @Override
            public void run() {
                // Liberar a vaga após o tempo de reserva expirar
                vaga.liberar();
            }
        }, tempoReserva * 60 * 1000); // tempoReserva em minutos
    }


    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Vaga getVaga() {
        return vaga;
    }

    public void setVaga(Vaga vaga) {
        this.vaga = vaga;
    }
}