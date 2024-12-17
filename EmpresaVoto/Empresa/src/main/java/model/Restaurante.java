package model;

import javax.persistence.*;
import java.util.List;

/*Classe Restaurante
@author Tallyta
@version 1.0
@since 15/11/2024*/

@Entity
@Table(name = "RESTAURANTE")
public class Restaurante {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.TABLE , generator="restaurante_generator")
    @TableGenerator(name="restaurante_generator",
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

    public Restaurante( String nome) {
        this.nome = nome;
    }

    public Restaurante(){
    }


    @OneToMany(mappedBy = "restaurante", cascade = CascadeType.ALL)
    private List<Voto> votos;

    @Override
    public String toString() {
        return "Restaurante " +
                "id = " + id +
                ", nome = " + nome + '\'' +
                ", votos = " + (votos != null ? votos.size() : 0) ;
    }
}

