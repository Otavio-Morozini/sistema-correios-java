package Controller;

import Model.*;
import java.util.List;

public class CorreioController {

    private SistemaDeEntregas sistema;

    public CorreioController() {
        this.sistema = new SistemaDeEntregas();
    }
    public void adicionarEntrega(Entrega entrega){
        sistema.registrarEntrega(entrega);
    }

    public List<Entrega> obterTodasEntregas() {
        return sistema.getEntregas();
    }

    public List<Entrega> listarTodasEntregas() {
        return sistema.getEntregas();
    }
}
