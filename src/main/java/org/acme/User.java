package org.acme;

import io.quarkus.elytron.security.common.BcryptUtil;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import me.yanaga.opes.Cpf;

@Entity
@Table(name = "users")

public class User extends PanacheEntity {
    @Column (unique = true)
    public String username;
    public String password;
    public String role;
    public String cpf;

    // Construtor padrão
    public User () {}


    public User(String username, String password, String role, String cpf) throws Exception{

       if (!CpfValidator(cpf)){
           throw new IllegalAccessException("Cpf inválido");
       }
       this.username = username;
       this.password = BcryptUtil.bcryptHash(password);//senha encriptada com hash
        this.cpf = AESUtil.encrypt(cpf);
       this.role = role;
    }

    public String getCpf() throws Exception {
       return AESUtil.decrypt(cpf);
    }

    private boolean CpfValidator(String cpf){
       try {
           Cpf.of(cpf);
           return true;
       } catch (IllegalArgumentException e) {
           return false;
       }
    }
}
