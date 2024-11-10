package com.api.biblioteca.model;

import java.time.LocalDate;
import java.util.List;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.OneToMany;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.CascadeType;

@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String sobrenome;
    private String endereco;
    private String cidade;
    private String estado;
    private String pais;
    private LocalDate dataNascimento;

    @Enumerated(EnumType.STRING)
    private StatusCliente status;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    private List<Loan> emprestimos; 

    // Getters e Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public StatusCliente getStatus() {
        return status;
    }

    public void setStatus(StatusCliente status) {
        this.status = status;
    }

    public List<Loan> getEmprestimos() {
        return emprestimos;
    }

    public void setEmprestimos(List<Loan> emprestimos) {
        this.emprestimos = emprestimos;
    }

    public enum StatusCliente {
        ATIVO, INATIVO
    }
}
