package org.acme;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/auth")
@Produces(MediaType.TEXT_PLAIN) //o text plain ou "texto puro" é a forma mais simples de representar um texto digitalmente.
@Consumes(MediaType.APPLICATION_JSON) //o json é o formato mais comum para envio de dados, nesse caso a dependência "@Consume" espera receber uma informação em formato json.
public class AuthResource {
    @Inject // dependência que pega mecanismos fornecidos por outra classe
    AuthService authService; //o AuthService está sendo injetado no AuthResource, permitindo que o AuthResource use os métodos e propriedades do AuthService.

    @POST //uma requisição de postagem
    @Path("/register")//especificar o "caminho", o endereço da requisição em URL
    @Transactional /* É uma anotação em que consiste em: todas as operações devem ser executadas
    dentro de uma transação ou nenhuma será executada, serve pra garantir a integridade dos dados.*/
    public Response register (Credentials credentials) throws Exception {
        var newUser = new User (credentials.username, credentials.password, credentials.role, credentials.cpf);
        User.persist(newUser);
        return Response.status(201).build();
    }

    @POST
    @Path("/login")
    public Response login(Credentials credentials) throws Exception {
        credentials.cpf = AESUtil.encrypt(credentials.cpf);

        User user = authService.findByCpf(credentials.cpf);
        if (user != null && authService.verifiyPassword(credentials.password, user.password)) {
            String token = authService.generateToken(user.username, user.role);

            return Response.ok(token).build();
        }
        return Response.status(401).build();
    }

}
