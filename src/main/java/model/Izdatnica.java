package main.java.model;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import java.time.LocalDate;

@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Izdatnica {

    @Id
    private Long id;
    @NotNull
    private Poduzece poduzeceID;
    @NotNull
    private LocalDate datumIzdatnice;

}
