package model;

import javax.persistence.*;
import java.util.Calendar;

/*Classe Voto
@author Tallyta
@version 1.0
@since 15/11/2024*/

@Entity
@Table(name = "VOTO")
public class Voto {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "voto_generator")
    @TableGenerator(name = "voto_generator",
            table = "chave",
            pkColumnName = "id",
            valueColumnName = "valor",
            allocationSize = 1)
    private Integer id;

    @Temporal(TemporalType.DATE)
    @Column(name = "data")
    private Calendar dataVoto;

    @ManyToOne
    @JoinColumn(name = "id_funcionario", referencedColumnName = "id")
    private Funcionario funcionario;

    @ManyToOne
    @JoinColumn(name = "id_restaurante", referencedColumnName = "id")
    private Restaurante restaurante;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Calendar getDataVoto() {
        return dataVoto;
    }

    public void setDataVoto(Calendar dataVoto) {
        this.dataVoto = dataVoto;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public Restaurante getRestaurante() {
        return restaurante;
    }

    public void setRestaurante(Restaurante restaurante) {
        this.restaurante = restaurante;
    }

    public Voto(Calendar dataVoto, Funcionario funcionario, Restaurante restaurante) {
        this.dataVoto = dataVoto;
        this.funcionario = funcionario;
        this.restaurante = restaurante;
    }

    public Voto(){

    }

    @Override
    public String toString() {
        return "Voto " +
                "id = " + id +
                ", data = " + dataVoto +
                ", funcionario = " + funcionario +
                ", restaurante = " + restaurante ;
    }
}