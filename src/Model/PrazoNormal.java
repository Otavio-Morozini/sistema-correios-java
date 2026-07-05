package Model;

public class PrazoNormal implements PrazoEntrega {
    @Override
    public int obterDiasUteis() {
        return 5;
    }

    @Override
    public String obterDescricao() {
        return "5 dias úteis (Normal)";
    }
}
