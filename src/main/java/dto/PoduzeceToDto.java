package main.java.dto;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import main.java.model.Poduzece;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PoduzeceToDto {

    @Id
    @GeneratedValue
    @Column(name = "IDFirme")
    private long id;

    @NotNull
    @Column(name = "OIBFirme")
    private String oib;

    @Column(name = "NazivFirme")
    @NotNull
    private String naziv;


    public Poduzece getPoduzece() {
        return Poduzece.poduzeceBuilder()
                .oib(this.oib)
                .naziv(this.naziv)
                .build();


    }
}
