package com.petProject.petClinic.domain;

import com.petProject.petClinic.model.Person;
import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PropertyComparator;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlElement;
import java.util.*;

@Entity
@Table(name = "vets")
public class Vet extends Person {

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "vet_specialties",
    joinColumns = @JoinColumn(name = "vet_id"),
            inverseJoinColumns = @JoinColumn(name = "specialty_id")
    )
    private Set <Specialty> specialties;

    protected Set <Specialty> getSpecialtiesInternal(){
        if(this.specialties == null)
            this.specialties = new HashSet<>();
        return this.specialties;
    }

    public void setSpecialtiesInternal(Set <Specialty> specialties){
        this.specialties = specialties;
    }

    @XmlElement
    public List<Specialty> getSpecialties(){
        List <Specialty> sortedSpecs = new ArrayList<>(getSpecialtiesInternal());
        PropertyComparator.sort(sortedSpecs,
                new MutableSortDefinition("name", true, true));
        return Collections.unmodifiableList(sortedSpecs);
    }

    public int getSizeOfSpecialties(){
        return getSpecialtiesInternal().size();
    }

    public void addSpecialty(Specialty specialty){
        getSpecialtiesInternal().add(specialty);
    }

}
