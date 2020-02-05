package rs.ac.uns.ftn.ktsnwt.dto;

import javax.validation.constraints.NotNull;

public class EventDayDescriptionEditDTO {

    @NotNull(message = "You must provide event day id")
    private Long id;
    @NotNull(message = "You must provide description")
    private String description;


    public EventDayDescriptionEditDTO(){
        super();
    }
    public EventDayDescriptionEditDTO(Long id, String description) {
        this.id = id;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
