package model;

import javax.persistence.*;
import java.util.List;

/*Classe Funcionario
@author Tallyta
@version 1.0
@since 15/11/2024*/

@Entity
@Table(name = "FUNCIONARIO")
public class Funcionario {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy= GenerationType.TABLE , generator="funcionario_generator")
    @TableGenerator(name="funcionario_generator",
            table="chave",
            pkColumnName="id",
            valueColumnName="valor",
            allocationSize=1)
    private Integer id;

    @Column(name = "nome", length = 250)
    private String nome;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Voto> getVotos() {
        return votos;
    }

    public void setVotos(List<Voto> votos) {
        this.votos = votos;
    }

    public Funcionario(String nome) {
        this.nome = nome;
    }
    public Funcionario(){

    }

    @OneToMany(mappedBy = "funcionario", cascade = CascadeType.ALL)
    private List<Voto> votos;

    @Override
    public String toString() {
        return "Funcionario " +
                "nome = " + nome + '\'' +
                ", id = " + id;
    }
}
