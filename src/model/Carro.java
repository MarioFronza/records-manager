/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Objects;


/**
 *
 * @author mario
 */
public class Carro {
    
    private String chassi;
    private String nome;
    private double valor;
    private int quantidade;
    private String cor;
    private boolean estado;
    private long left;
    private long right;

    public Carro() {
    
    }
    
    public Carro(String chassi, String nome, double valor, int quantidade, String cor, boolean estado, long left, long right) {
        this.chassi = chassi;
        this.nome = nome;
        this.valor = valor;
        this.quantidade = quantidade;
        this.cor = cor;
        this.estado = estado;
        this.left = left;
        this.right = right;
    }

    public String getChassi() {
        return chassi;
    }

    public void setChassi(String chassi) {
        this.chassi = chassi;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public long getLeft() {
        return left;
    }

    public void setLeft(long left) {
        this.left = left;
    }

    public long getRight() {
        return right;
    }

    public void setRight(long right) {
        this.right = right;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.chassi);
        hash = 97 * hash + Objects.hashCode(this.nome);
        hash = 97 * hash + (int) (Double.doubleToLongBits(this.valor) ^ (Double.doubleToLongBits(this.valor) >>> 32));
        hash = 97 * hash + this.quantidade;
        hash = 97 * hash + Objects.hashCode(this.cor);
        hash = 97 * hash + (this.estado ? 1 : 0);
        hash = 97 * hash + (int) (this.left ^ (this.left >>> 32));
        hash = 97 * hash + (int) (this.right ^ (this.right >>> 32));
        return hash;
    }
    
    

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Carro other = (Carro) obj;
        return true;
    }
    
    
    
    @Override
    public String toString() {
        return "Carro{" + "chassi=" + chassi + ", nome=" + nome + ", valor=" + valor + ", quantidade=" + quantidade + ", cor=" + cor + ", estado=" + estado + ", left=" + left + ", right=" + right + '}';
    }
    
    
    
}
