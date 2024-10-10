package org.acme;

import io.quarkus.elytron.security.common.BcryptUtil;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AuthService {
        public User findByUsername(String username){
            return User.find("username", username).firstResult();

        }
        public boolean verifiyPassword(String password, String hashedPassword) {
            return BcryptUtil.matches(password, hashedPassword);

        }
        public String generateToken(String username, String role){

            return GenerateToken.generateToken(username, role);
            }


    }






