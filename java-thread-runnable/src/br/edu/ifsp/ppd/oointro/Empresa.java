package br.edu.ifsp.ppd;

import java.util.ArrayList;
import java.util.List;

public class Empresa {

    private String nome;
    private Endereco endereco;
    private final List<Funcionario> funcionarios = 
                                    new ArrayList<Funcionario>();

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Endereco getEndereco() {
        return this.endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public List<Funcionario> getFuncionarios() {
        return this.funcionarios;
    }

    public void addFuncionario(Funcionario funcionario) {
        this.funcionarios.add(funcionario);
    }

    public int countFuncionario() {
        return this.funcionarios.size();
    }
}
