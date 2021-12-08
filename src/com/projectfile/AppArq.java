package com.projectfile;

public class AppArq {
    private Arquivo_Java arq;

    public AppArq(){
        arq = new Arquivo_Java("/Users/campos/Documents/www/projectFile/arquivo.dat");
    }

    public void executa(){
        arq.leArq();
        arq.exibirArq();
    }

    public static void main(String[] args) {
	    AppArq app = new AppArq();
        app.executa();
    }
}
