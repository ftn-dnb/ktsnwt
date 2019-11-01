package rs.ac.uns.ftn.ktsnwt.model;

import rs.ac.uns.ftn.ktsnwt.model.enums.SectorType;

import javax.persistence.*;

@Entity
@Table(name = "sectors")
public class Sector {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "numRows")
    private int numRows;

    @Column(name = "numColumns")
    private int numColumns;

    @Column(name ="capacity")
    private int capacity;

    @Column(name = "type")
    private SectorType type;


}
