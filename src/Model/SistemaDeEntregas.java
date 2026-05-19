package Model;

import java.util.ArrayList;
import java.util.List;

public class SistemaDeEntregas {
    private List<Entrega> entregas = new ArrayList<>();

    public boolean registrarEntrega(Entrega e){
        entregas.add(e);
        return true;
    }

    public List<Entrega> getEntregas() {
        return entregas;
    }
}
