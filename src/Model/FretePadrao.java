package Model;

public class FretePadrao implements ModalidadeFrete{
    public double calcularValor(double pesoDaEncomenda){
        return pesoDaEncomenda * 5.0;
    }
}
