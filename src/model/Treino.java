package model;
import java.time.LocalDate;


public class Treino{
    
    private int id;
    private int idAluno;
    private String treino;
    private String descricao;
    private int duracaoMinutos;
    private LocalDate dataInicio;

   
    public Treino (){
    }

    
    public Treino(int id, int idAluno, String treino, String descricao, int duracaoMinutos, LocalDate dataInicio){
     this.id = id;
     this.idAluno = idAluno;
     this.treino = treino;
     this.descricao = descricao;
     this.duracaoMinutos = duracaoMinutos;
     this.dataInicio = dataInicio;
    }
    

    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id = id;
    }
    public int getIdAluno(){
        return idAluno;
    }
    public void setIdAluno(int idAluno){
        this.idAluno = idAluno;
    }
    public String getTreino(){
        return treino;
    }
    public void setTreino(String treino){
        this.treino = treino;
    }
    public String getDescricao(){
        return descricao;
    }
    public void setDescricao(String descricao){
        this.descricao = descricao;
    }
    public int getDuracaoMinutos(){
        return duracaoMinutos;
    }
    public void setDuracaoMinutos(int duracaoMinutos){
        this.duracaoMinutos = duracaoMinutos;
    }
    public LocalDate getDataInicio(){
        return dataInicio;
    }
    public void setDataInicio(LocalDate dataInicio){
        this.dataInicio = dataInicio;
    }
}

