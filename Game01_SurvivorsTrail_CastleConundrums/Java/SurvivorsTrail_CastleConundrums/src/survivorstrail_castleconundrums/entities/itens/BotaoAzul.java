package survivorstrail_castleconundrums.entities.itens;

import survivorstrail_castleconundrums.entities.Itens;

public class BotaoAzul extends Itens {

    public int timer = 600;

    public BotaoAzul(double _x, double _y, int _width, int _height) {
        super(_x, _y, _width, _height);

        setSprites(Itens.BOTAO_AZUL_DISABLE, Itens.BOTAO_ENABLE);

    }

    public void Enable() {
        enable = true;
        timer = 600;
    }

    public void tick() {
        if (isAtivo()) {
            showMensagem = true;

            Mensagem = "" + timer / 60;

            if (timer > 0) {
                timer--;
            } else {
                Disable();
                showMensagem = false;
            }
        }
    }
}
