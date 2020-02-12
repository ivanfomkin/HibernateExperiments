import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "subscriptions")
public class Subscription {

    @EmbeddedId
    private SubscriptionPK subscriptionPK;

    @Column(name = "subscription_date")
    private Date subscriptionDate;

    public Subscription() {
    }

    public SubscriptionPK getSubscriptionPK() {
        return subscriptionPK;
    }

    public void setSubscriptionPK(SubscriptionPK subscriptionPK) {
        this.subscriptionPK = subscriptionPK;
    }

    public Date getSubscriptionDate() {
        return subscriptionDate;
    }

    public void setSubscriptionDate(Date subscriptionDate) {
        this.subscriptionDate = subscriptionDate;
    }
}
