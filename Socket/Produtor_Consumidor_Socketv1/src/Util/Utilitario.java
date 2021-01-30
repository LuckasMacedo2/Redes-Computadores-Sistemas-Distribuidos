package Util;

import Message.*;

public class Utilitario {
    public static Mensagem montarMensagem(String str){
        String[] v = str.split(";");

        if (v.length < 4)
            return null;

        return new Mensagem(Integer.parseInt(v[0].trim()),
                TipoCliente.values()[Integer.parseInt(v[1].trim())],
                TipoOperacao.values()[Integer.parseInt(v[2].trim())],
                Integer.parseInt(v[3].trim()));
        // .trim() -> Remove os espaços em branco

    }
}
