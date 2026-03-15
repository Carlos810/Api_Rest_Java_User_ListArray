package com.prueba.tecnica.mvp.service;

import com.prueba.tecnica.mvp.model.ModelAddress;
import com.prueba.tecnica.mvp.model.ModelUser;
import com.prueba.tecnica.mvp.utils.CryptoUtil;
import com.prueba.tecnica.mvp.utils.FilterUtil;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;

@Service
public class UserServiceMemory {
    private static final AtomicLong addressIdGenerator = new AtomicLong(1);
    public static List<ModelUser> users = new ArrayList<>();

    @PostConstruct
    public void init(){
        create(ModelUser.builder()
                .email("user1@mail.com")
                .name("user1")
                .password("123456")
                .phone("+15874")
                .tax_id("ABC120101XY1")
                .addresses(List.of(
                        ModelAddress.builder()
                                .street("Av Reforma")
                                .name("asaae2 3412132")
                                .country_code("AU")
                                .build()
                ))
                .build());

        create(ModelUser.builder()
                .email("user2@mail.com")
                .name("user2")
                .password("456789")
                .phone("+15555555557")
                .tax_id("GOMC850412AB3")
                .addresses(List.of(
                        ModelAddress.builder()
                                .street("Insurgentes")
                                .name("CDMX")
                                .country_code("UK")
                                .build()
                ))
                .build());

        create(ModelUser.builder()
                .email("user3@mail.com")
                .name("user3")
                .password("897454")
                .phone("+15555555556")
                .tax_id("XAXX010101000")
                .addresses(List.of(
                        ModelAddress.builder()
                                .street("Av Reforma")
                                .name("casa 1")
                                .country_code("MX")
                                .build(),
                        ModelAddress.builder()
                                .street("presidente Mazarik Polanco")
                                .name("casa 2")
                                .country_code("Mexico")
                                .build()
                ))
                .build());
    }

    public ModelUser create(ModelUser user){
        if(user == null){
            throw new IllegalArgumentException("User cannot be null");
        }

        user.setId(UUID.randomUUID());
        user.setCreated_at(FilterUtil.getMadagascarTime());
        if(user.getPassword() != null){
            user.setPassword(CryptoUtil.encrypt(user.getPassword()));
        }
        if(user.getAddresses() != null){
            user.getAddresses().forEach( address ->
                address.setId(addressIdGenerator.getAndIncrement())
            );
        }
        users.add(user);
        return user;
    }

    public List<ModelUser> filterAndSearch(String sortedBy, String filter){

        Stream<ModelUser> stream = users.stream();

        if(filter != null && !filter.isBlank()){
            stream = FilterUtil.applyFilter(stream, filter);
        }

        if(sortedBy != null && !sortedBy.isBlank()){
            boolean desc = sortedBy.startsWith("-");
            String field = desc ? sortedBy.substring(1) : sortedBy;

            Comparator<ModelUser> comparator = getComparator(field);
            stream = stream.sorted(desc ? comparator.reversed() : comparator);
        }

        System.out.println("FILTER RECEIVED: [" + filter + "]");

        return stream.toList();
    }

    public static Comparator<ModelUser> getComparator(String field){

        return switch(field){

            case "name" -> Comparator.comparing(ModelUser::getName);
            case "email" -> Comparator.comparing(ModelUser::getEmail);
            case "phone" -> Comparator.comparing(ModelUser::getPhone);
            case "tax_id" -> Comparator.comparing(ModelUser::getTax_id);
            case "created_at" -> Comparator.comparing(ModelUser::getCreated_at);
            case "id" -> Comparator.comparing(ModelUser::getId);

            default -> Comparator.comparing(ModelUser::getName);
        };
    }

    public ModelUser updatePatch(UUID id, ModelUser updatedUser){

        ModelUser existingUser = users.stream()
                .filter(u -> u.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("User not found"));

        Optional.ofNullable(updatedUser.getName())
                .ifPresent(existingUser::setName);

        Optional.ofNullable(updatedUser.getEmail())
                .ifPresent(existingUser::setEmail);

        Optional.ofNullable(updatedUser.getPhone())
                .ifPresent(existingUser::setPhone);

        Optional.ofNullable(updatedUser.getAddresses())
                .ifPresent(existingUser::setAddresses);

        Optional.ofNullable(updatedUser.getPassword())
                .map(CryptoUtil::encrypt)
                .ifPresent(existingUser::setPassword);

        return existingUser;
    }

    public void delete(UUID id){
        ModelUser user = users.stream().filter(item -> item.getId().equals(id))
                .findFirst()
                .orElseThrow( ()-> new RuntimeException("User not found"));
        users.remove(user);
    }
}
