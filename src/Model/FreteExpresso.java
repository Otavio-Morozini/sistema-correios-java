package Model;

public class FreteExpresso implements ModalidadeFrete{
    public double calcularValor(double pesoDaEncomenda){
        return pesoDaEncomenda * 10.0;
    }
}
