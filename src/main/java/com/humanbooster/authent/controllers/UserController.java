package com.humanbooster.authent.controllers;

import com.humanbooster.authent.models.User;
import com.humanbooster.authent.services.UserService;
import com.humanbooster.authent.validations.OnCreate;
import com.humanbooster.authent.validations.OnUpdate;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping(value = "/", produces = "application/json")
    @Operation(summary = "Récupère tous les utilisateurs")
    @ApiResponse(responseCode = "200", description = "Liste des utilisateurs")
    List<User> getUsers() {
        List<User> users = this.userService.getAll();
        return users;
    }

//______________________________________________________________________________________________________________________

    @PostMapping(value = "/", consumes = "application/json", produces = "application/json")
    @Operation(summary = "Enregistrer un utilisateur")
    @ApiResponses(value = { // annotation swagger pour indiquer les différents status possibles lors de la soumission de la requête
            @ApiResponse(description = "Utilisateur créé", responseCode = "201"),
            @ApiResponse(description = "Un utilisateur avec cet email existe déjà!", responseCode = "409"),
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, content = @Content(
            examples = {
                    @ExampleObject(
                            name = "Utilisateur",
                            summary = "Utilisateur sans Id et sans commandes",
                            value =
                                    "{\n" +
                                            "  \"email\": \"hello@mail.fr\",\n" +
                                            "  \"firstname\": \"Tretre\",\n" +
                                            "  \"lastname\": \"TOTO\",\n" +
                                            "  \"nickname\": \"Treto\",\n" +
                                            "  \"password\": \"tretre123!\",\n" +
                                            "  \"confirmPassword\": \"tretre123!\",\n" +
                                            "  \"street_number\": \"56\",\n" +
                                            "  \"street_name\": \"place de Jaude\",\n" +
                                            "  \"zip_code\": \"63000\",\n" +
                                            "  \"city\": \"Clermont-Ferrand\",\n" +
                                            "  \"country\": \"France\",\n" +
                                            "  \"role_list\": [\n" +
                                            "    {\n" +
                                            "      \"id\": 2,\n" +
                                            "      \"roleName\": \"USER\"\n" +
                                            "    }\n" +
                                            "  ]\n" +
                                            "}"
                    )
            }
    ))
    public ResponseEntity<User> saveUser(@Validated(OnCreate.class) @RequestBody User user) {
        User userExist = this.userService.findByEmail(user.getEmail());
        if (userExist == null) {
            user = this.userService.saveUser(user);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Un utilisateur avec cet email existe déjà");
        }
        return new ResponseEntity<User>(user, HttpStatus.CREATED);
    }

//______________________________________________________________________________________________________________________

    @GetMapping(value = "{user}", produces = "application/json")
    @Operation(summary = "Récupére un utilisateur en fonction de son Id")
    @ApiResponses(value= {
            @ApiResponse(responseCode = "200", description = "Utilisateur trouvé",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = User.class))
                    }),
            @ApiResponse(responseCode = "404", description = "Cet utilisateur n'existe pas",
                    content = @Content)
    })
    public User getUser(@Parameter(description = "Id de l'utilisateur", example = "1") @PathVariable(name = "user", required = false) User user) {
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cet utilisateur n'existe pas");
        }
        return user;
    }

//______________________________________________________________________________________________________________________

    @PutMapping(value = "/{user}", consumes = "application/json", produces = "application/json")
    @Operation(summary = "Modifie les champs d'un utilisateur en fonction de son Id")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, content = @Content(
            examples = {
                    @ExampleObject(
                            name = "Utilisateur",
                            summary = "Id de l'utilisateur",
                            value =
                                    "{\n" +
                                            "  \"email\": \"hello@mail.fr\",\n" +
                                            "  \"firstname\": \"Tretre36\",\n" +
                                            "  \"lastname\": \"TOTO36\",\n" +
                                            "  \"nickname\": \"Treto\",\n" +
                                            "  \"password\": \"tretre123!\",\n" +
                                            "  \"confirmPassword\": \"tretre123!\",\n" +
                                            "  \"streetNumber\": \"85\",\n" +
                                            "  \"streetName\": \"rue du Courage\",\n" +
                                            "  \"zipcode\": \"63100\",\n" +
                                            "  \"city\": \"Clermont-Ferrand\",\n" +
                                            "  \"country\": \"France\",\n" +
                                            "  \"role_list\": [\n" +
                                            "    {\n" +
                                            "      \"id\": 2,\n" +
                                            "      \"roleName\": \"USER\"\n" +
                                            "    }\n" +
                                            "  ]\n" +
                                            "}"
                    )
            }
    ))
    public ResponseEntity<User> update(
            @Parameter(description = "Id de l'utilisateur", example = "1")
            @PathVariable(name = "user", required = false) User user,
            @Validated(OnUpdate.class) @RequestBody User userUpdate) {

        //Vérifier que cet ID existe
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cet utilisateur n'existe pas");
        }

//        //Vérifier qu'on n'a pas d'autre compte avec ce mail
//        List<User> userWithMail = this.userService.findByEmailWithoutId(userUpdate.getEmail(), user.getIdUser());
//
//        if (!userWithMail.isEmpty()) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ce username est déjà attribué à un autre utilisateur");
//        }

        //On enregistre les modifications
        userUpdate.setIdUser(user.getIdUser());
        userUpdate = userService.saveUser(userUpdate);

        return new ResponseEntity<User>(userUpdate, HttpStatus.OK);
    }

//______________________________________________________________________________________________________________________

    @DeleteMapping(value = "/{user}")
    @Operation(summary = "Supprime un utilisateur en fonction de son Id") // annotation swagger qui permet de documenter le titre de l'api
    @ApiResponses(value = { // annotation swagger pour indiquer les différents status possibles lors de la soumission de la requette
            @ApiResponse(description = "Cet utilisateur n'existe pas", responseCode = "404"),
            @ApiResponse(description = "Cet utilisateur a des commandes, suppression impossible", responseCode = "400"),
            @ApiResponse(description = "Utilisateur supprimé !", responseCode = "200")
    })
    void deleteOne(@Parameter(description = "Id de l'utilisateur", example = "1") @PathVariable(name = "user", required = false) User user) {
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Utilisateur introuvable");
        } else {
            if (user.getCommandList().isEmpty()) {
                this.userService.removeUser(user);
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Impossible de supprimer cet utilisateur, il a déjà des commandes");
            }
        }

        this.userService.removeUser(user);
    }
}
