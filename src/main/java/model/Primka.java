package main.java.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@Data
public class Primka extends Izdatnica {

    public Primka(Long id, Poduzece poduzeceID, LocalDate datumIzdatnice) {
        super(id, poduzeceID, datumIzdatnice);
    }
}
