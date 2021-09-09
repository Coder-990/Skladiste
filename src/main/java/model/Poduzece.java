package main.java.model;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.Entity;
import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Poduzece extends Entitet implements Serializable {

    @NotNull
    private String oib;

    public Poduzece(Long id, String naziv, String oib) {
        super(id, naziv);
        this.oib = oib;
    }
}
