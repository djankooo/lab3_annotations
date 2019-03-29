package pl.pwr;

import javax.validation.constraints.*;

public class Device {

    @NotNull
    @DaneOsoboweField(name = "Id")
    @Min(value = 0,message= "ID nie moze byc ujemne!")
    private int id;

    @NotNull
    @Size(min = 1, max = 100, message = "Nazwa urzadzenia ma nieodpowiednią długość!")
    @DaneOsoboweField(name = "Name")
    private String name;

    @NotNull
    @Size(min = 1, max = 100, message = "Pole owner ma nieodpowiednią długość!")
    @DaneOsoboweField(name = "Owner")
    private String Owner;

    public Device() { }

    public int getId() { return id; }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwner() {
        return Owner;
    }

    public void setOwner(String owner) {
        Owner = owner;
    }
}
