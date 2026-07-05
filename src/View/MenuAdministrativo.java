package View;

import Controller.*;
import Model.*;

import java.util.List;

public class MenuAdministrativo {
    private MenuGeral menuGeral;

    public MenuAdministrativo(MenuGeral menuGeral) {
        this.menuGeral = menuGeral;
    }

    public void exibir() {
        System.out.println("\n--- SISTEMA ADMINISTRATIVO - TODAS AS ENTREGAS ---");
        List<Entrega> lista = menuGeral.getController().listarTodasEntregas();
        if (lista.isEmpty()) {
            System.out.println("Nenhuma entrega solicitada no sistema até agora.");
            return;
        }
        for (Entrega e : lista) {
            System.out.println(e.toString());
            System.out.println("--------------------------------------------------");
        }
    }
}
