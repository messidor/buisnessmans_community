package api.v1.company;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Company {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long companyId;

    @Column(nullable = false, updatable = false, unique = true)
    private Long companyCode;

    @Column(length = 100, nullable = false)
    private String companyName;

    @Column(length = 100, nullable = false)
    private String companyLocation;



    public Company( Long companyCode, String companyName, String companyLocation) {
        this.companyCode = companyCode;
        this.companyName = companyName;
        this.companyLocation = companyLocation;

    }
}
