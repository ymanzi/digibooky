package com.switchfully.digibooky.rental.domain;

import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class RentalRepository {
    private final Map<UUID,Rental> rentalByUUIDRepository;

    public RentalRepository() {
        this.rentalByUUIDRepository = new ConcurrentHashMap<>();
    }
    public Rental save(Rental rental){
        rentalByUUIDRepository.put(rental.getRentalId(), rental);
        return rental;
    }
    public Rental getRentalById(UUID rentalId){
        Rental rental = rentalByUUIDRepository.get(rentalId);
        return rental;
    }
    public Collection<Rental> getAllRentals(){
        return rentalByUUIDRepository.values();
    }
    public Rental deleteRental(Rental rental){
        rentalByUUIDRepository.remove(rental.getRentalId());
        return rental;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RentalRepository that = (RentalRepository) o;
        return Objects.equals(rentalByUUIDRepository, that.rentalByUUIDRepository);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rentalByUUIDRepository);
    }
}
