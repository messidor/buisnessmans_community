package api.v1.location;

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
public class LocationArray {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long locationId;

    @Column(nullable = false, updatable = false, unique = true)
    private Long locationCode;

    @Column(length = 100, nullable = false)
    private String locationName;


    public LocationArray( long locationCode, String locationName) {
        this.locationCode = locationCode;
        this.locationName = locationName;

    }
}
