import javax.persistence.*;

@Entity
@Table(name = "linkedpurchaseList")
public class LinkedPurchaseList {
    @EmbeddedId
    private LinkedPurchaseListPK id;

    public LinkedPurchaseList() {
    }

    public LinkedPurchaseList(LinkedPurchaseListPK id) {
        this.id = id;
    }

    public LinkedPurchaseListPK getId() {
        return id;
    }

    public void setId(LinkedPurchaseListPK id) {
        this.id = id;
    }
}