package com.api.biblioteca.model;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID do cliente", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Schema(description = "Nome do cliente", example = "João", required = true)
    private String nome;

    @Schema(description = "Sobrenome do cliente", example = "Silva")
    private String sobrenome;

    @Schema(description = "Endereço do cliente", example = "Rua 123, Bairro XYZ")
    private String endereco;

    @Schema(description = "Cidade do cliente", example = "São Paulo")
    private String cidade;

    @Schema(description = "Estado do cliente", example = "SP")
    private String estado;

    @Schema(description = "País do cliente", example = "Brasil")
    private String pais;

    @Schema(description = "Data de nascimento do cliente", example = "1990-12-15")
    private LocalDate dataNascimento;

    @Enumerated(EnumType.STRING)
    @Schema(description = "Status do cliente", example = "ATIVO", allowableValues = {"ATIVO", "INATIVO"})
    private StatusCliente status;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    @JsonIgnore // Evita que a lista de empréstimos seja exibida quando o cliente for criado ou atualizado
    @Schema(description = "Lista de empréstimos do cliente", hidden = true)  // Excluído do Swagger
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
