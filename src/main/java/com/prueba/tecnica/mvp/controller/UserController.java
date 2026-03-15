package com.prueba.tecnica.mvp.controller;

import com.prueba.tecnica.mvp.model.ModelUser;
import com.prueba.tecnica.mvp.service.UserServiceMemory;
import com.prueba.tecnica.mvp.validations.UserValidation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static com.prueba.tecnica.mvp.service.UserServiceMemory.users;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("api/v1/users")
public class UserController {
private UserServiceMemory _userServiceMemory;

public UserController(UserServiceMemory userServiceMemory){
    this._userServiceMemory = userServiceMemory;
}

    @GetMapping("")
    public List<ModelUser> getUsers(
            @RequestParam(required = false) String sortedBy,
            @RequestParam(required = false) String filter
    ){
        return _userServiceMemory.filterAndSearch(sortedBy, filter);
    }

     @PostMapping("")
     public ResponseEntity addUser(@Valid @RequestBody ModelUser user){
         UserValidation.validateEmailUnique(user.getEmail());
         UserValidation.validateTaxIdUnique(user.getTax_id());
         ModelUser createdUser = _userServiceMemory.create(user);
         System.out.println("TOTAL USERS: " + users.size());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdUser);
     }

    @PatchMapping("/{id}")
    public ResponseEntity<ModelUser> putUser(
            @PathVariable UUID id,
            @RequestBody ModelUser user){
        ModelUser updatedUser = _userServiceMemory.updatePatch(id, user);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteUser(@PathVariable UUID id){
         _userServiceMemory.delete(id);
         return ResponseEntity.noContent().build();
    }

}
