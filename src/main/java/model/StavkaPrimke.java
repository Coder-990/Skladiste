package main.java.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@Data
public class StavkaPrimke extends StavkaIzdatnice{

    public StavkaPrimke(Long id, Izdatnica izdatnicaId, Artikl artiklId, Integer kolicina) {
        super(id, izdatnicaId, artiklId, kolicina);
    }
}
