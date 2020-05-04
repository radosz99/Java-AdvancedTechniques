package model.app.entities;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name="cyclists")
@XmlAccessorType(XmlAccessType.FIELD)
public class CyclistList {
    @XmlElement(name = "cyclist")
    private List<Cyclist> cyclists = null;

    public List<Cyclist> getCyclists() {
        return cyclists;
    }

    public void setCyclists(List<Cyclist> cyclists) {
        this.cyclists = cyclists;
    }
}
