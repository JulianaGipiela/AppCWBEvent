package com.example.juliana.teste;

/**
 * Created by juliana on 20/05/2017.
 */

public class Evento {
    private int idevento;
    private String imagemInicio;
    private String nomeInico;
    private String dataInicio;
    private String decricao;
    private int idusuario;
    private String datacadastro;
    private boolean efavorito = false;
    private String local;

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getDecricao() {
        return decricao;
    }

    public void setDecricao(String decricao) {
        this.decricao = decricao;
    }

    public int getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(int idusuario) {
        this.idusuario = idusuario;
    }

    public String getDatacadastro() {
        return datacadastro;
    }

    public int getIdevento() {
        return idevento;
    }

    public void setIdevento(int idevento) {
        this.idevento = idevento;
    }

    public void setDatacadastro(String datacadastro) {
        this.datacadastro = datacadastro;
    }

    public boolean isEfavorito() {
        return efavorito;
    }

    public void setEfavorito(boolean efavorito) {
        this.efavorito = efavorito;
    }

    public Evento(int idevento, String imagemInicio, String nomeInico, String dataInicio, String decricao, int idusuario, String datacadastro, boolean efavorito) {
        this.imagemInicio = imagemInicio;
        this.nomeInico = nomeInico;
        this.dataInicio = dataInicio;
        this.decricao = decricao;
        this.idusuario = idusuario;
        this.datacadastro = datacadastro;
        this.efavorito = efavorito;
        this.idevento = idevento;
    }

    public Evento(String imagemInicio, String nomeInico, String dataInicio) {
        this.imagemInicio = imagemInicio;
        this.nomeInico = nomeInico;
        this.dataInicio = dataInicio;
    }

    public String getImagemInicio() {

        return imagemInicio;
    }

    public void setImagemInicio(String imagemInicio) {
        this.imagemInicio = imagemInicio;
    }

    public String getNomeInico() {
        return nomeInico;
    }

    public void setNomeInico(String nomeInico) {
        this.nomeInico = nomeInico;
    }

    public String getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(String dataInicio) {
        this.dataInicio = dataInicio;
    }
}
