package com.projectfile;

import java.io.IOException;
import java.io.RandomAccessFile;

public class Arquivo_Java
{
    private String nomearquivo;
    private RandomAccessFile arquivo;

    public Arquivo_Java(String nomearquivo)
    {
        try
        {
            arquivo = new RandomAccessFile(nomearquivo, "rw");
        } catch (IOException e)
        { }
    }

    public void truncate(long pos) //desloca eof
    {
        try
        {
            arquivo.setLength(pos * Registro.length());
        } catch (IOException exc)
        { }
    }

    //semelhante ao feof() da linguagem C
    //verifica se o ponteiro esta no <EOF> do arquivo
    public boolean eof()
    {
        boolean retorno = false;
        try
        {
            if (arquivo.getFilePointer() == arquivo.length())
                retorno = true;
        } catch (IOException e)
        { }
        return (retorno);
    }

    //insere um Registro no final do arquivo, passado por par�metro
    public void inserirRegNoFinal(Registro reg)
    {
        seekArq(filesize());//ultimo byte
        reg.gravaNoArq(arquivo);
    }

    public int filesize(){
        try{
            return (int)arquivo.length()/Registro.length();
        } catch (IOException ex){

        }
        return 0;
    }

    public void exibirArq()
    {
        int i;
        Registro aux = new Registro();
        seekArq(0);
        i = 0;
        while (!this.eof())
        {
            System.out.println("Posicao " + i);
            aux.leDoArq(arquivo);
            aux.exibirReg();
            i++;
        }
    }

    public void exibirUmRegistro(int pos)
    {
        Registro aux = new Registro();
        seekArq(pos);
        System.out.println("Posicao " + pos);
        aux.leDoArq(arquivo);
        aux.exibirReg();
    }

    public void seekArq(int pos)
    {
        try
        {
            arquivo.seek(pos * Registro.length());
        } catch (IOException e)
        { }
    }

    public void leArq()
    {
        int codigo, idade;
        String nome;
        codigo = Entrada.leInteger("Digite o c�digo");
        while (codigo != 0)
        {
            nome = Entrada.leString("Digite o nome");
            idade = Entrada.leInteger("Digite a idade");
            inserirRegNoFinal(new Registro(codigo, nome, idade));
            codigo = Entrada.leInteger("Digite o c�digo");
        }
    }

    //.............................................................................
    /*

    insira aqui os m�todos de Ordena��o;

    */

    public void insercao_direta(){
        int pos = 0, TL = filesize();
        Registro aux = new Registro();
        Registro reg = new Registro();

        for(int i = 1; i < TL; i++){
            seekArq(i);
            reg.leDoArq(arquivo);
            pos = i;
            seekArq(pos -1);
            reg.leDoArq(arquivo);
            while (pos>0 && aux.getCodigo() < reg.getCodigo()){
                reg.gravaNoArq(arquivo);
                pos--;
                if(pos>0){
                    seekArq(pos-1);
                    reg.leDoArq(arquivo);
                }
            }
        }
    }

    public void executa()
    {
        leArq();
        exibirArq();
    }

    //m�todo principal
    public static void main(String args[])
    {
        Arquivo_Java a = new Arquivo_Java("c:\\arquivo.dat");
        a.executa();
    }
}
