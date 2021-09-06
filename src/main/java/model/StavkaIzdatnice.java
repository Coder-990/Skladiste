package main.java.model;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Data
public class StavkaIzdatnice {

    @NotNull
    private Long id;
    @NotNull
    private Izdatnica izdatnicaId;
    @NotNull
    private Artikl artiklId;
    @NotNull
    private Integer kolicina;


}
