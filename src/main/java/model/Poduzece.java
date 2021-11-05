package main.java.model;

import lombok.*;

import javax.persistence.Entity;
import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Poduzece extends Entitet implements Serializable {

    private String oib;

    @Builder(builderMethodName = "poduzeceBuilder")
    public Poduzece(Long id, String naziv, String oib) {
        super(id, naziv);
        this.oib = oib;
    }
}
