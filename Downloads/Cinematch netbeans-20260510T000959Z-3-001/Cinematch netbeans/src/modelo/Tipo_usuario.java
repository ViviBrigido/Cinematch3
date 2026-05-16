/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author aluno
 */
public class Tipo_usuario {
    private int ID_TipoUsuario;
    private String descricao_usuario;

    public Tipo_usuario() {
    }

    public Tipo_usuario(int ID_TipoUsuario, String descricacao_usuario) {
        this.ID_TipoUsuario = ID_TipoUsuario;
        this.descricao_usuario = descricacao_usuario;
    }

    public int getID_TipoUsuario() {
        return ID_TipoUsuario;
    }

    public void setID_TipoUsuario(int ID_TipoUsuario) {
        this.ID_TipoUsuario = ID_TipoUsuario;
    }

    public String getDescricao_usuario() {
        return descricao_usuario;
    }

    public void setDescricao_usuario(String descricacao_usuario) {
        this.descricao_usuario = descricao_usuario;
    }

    @Override
    public String toString() {
        return "Tipo_usuario{" + "ID_TipoUsuario=" + ID_TipoUsuario + ", descricao_usuario=" + descricao_usuario + '}';
    }
    
    
}
