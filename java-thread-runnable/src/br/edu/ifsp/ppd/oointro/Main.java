package br.edu.ifsp.ppd;

public class Main {

    public static void main(String ... args) {

        Endereco enderecoFuncionario = new Endereco();
        enderecoFuncionario.setCEP("11665-350");

        Endereco enderecoEmpresa = new Endereco();
        enderecoEmpresa.setCEP("12310-660");

        Funcionario funcionario = new Funcionario();
        funcionario.setNome("Lucas Venezian Povoa");
        funcionario.setEndereco(enderecoFuncionario);

        Empresa empresa = new Empresa();
        empresa.setNome("K4D");
        empresa.setEndereco(enderecoEmpresa);
        empresa.addFuncionario(funcionario);

        System.out.println("Empresa: " + empresa.getNome());
        System.out.println("Endereço: " + empresa.getEndereco().getCEP());
        System.out.println("Funcionários: " + empresa.countFuncionario());
    }
}
