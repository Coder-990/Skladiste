package main.java.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Transient;
import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Artikl extends Entitet{

    private Integer kolicina;
    private BigDecimal cijena;
    private String jedinicnaMjera;

    @Transient
    private BigDecimal ukupnaVrijednost;

    public Artikl(Long id, String nazivArtikla, Integer kolicina, BigDecimal cijena, String jedinicnaMjera) {
        super(id, nazivArtikla);

        this.kolicina = kolicina;
        this.cijena = cijena;
        this.jedinicnaMjera = jedinicnaMjera;
    }

    public BigDecimal izracunUkupnuVrijednostArtikla(){
        return ukupnaVrijednost.add(cijena.multiply(BigDecimal.valueOf(kolicina)));
    }
}
