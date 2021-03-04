package forthcafe;

import javax.persistence.*;
import org.springframework.beans.BeanUtils;

import forthcafe.external.Delivery;
import forthcafe.external.DeliveryService;
import forthcafe.external.Message;
import forthcafe.external.MessageService;

import java.util.List;

// aggregate = JPA entity, Value Object
@Entity
@Table(name="Pay_table")
public class Pay {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String ordererName;
    private String menuName;
    private Long menuId;
    private Double price;
    private Integer quantity;
    private String status;


    @PrePersist
    public void onPrePersist(){

        this.setStatus("Pay");

        Payed payed = new Payed();
        BeanUtils.copyProperties(this, payed);
        payed.publishAfterCommit();

        //Following code causes dependency to external APIs
        // it is NOT A GOOD PRACTICE. instead, Event-Policy mapping is recommended.

        Message message = new Message();
        // mappings goes here
        BeanUtils.copyProperties(this, message);
        PayApplication.applicationContext.getBean(MessageService.class).message(message);

        // delay test시 주석해제
        //try {
        //        Thread.currentThread().sleep((long) (400 + Math.random() * 220));
        //} catch (InterruptedException e) {
        //        e.printStackTrace();
        //}
    }

    @PostUpdate
    public void onPostUpdate() {
        // kafka publish
        PayCancelled payCancelled = new PayCancelled();
        BeanUtils.copyProperties(this, payCancelled);
        payCancelled.setStatus("payCancelled");
        payCancelled.publish();

        // req/res 패턴 처리 
        Delivery delivery = new Delivery();
        BeanUtils.copyProperties(payCancelled, delivery);
        // feignclient 호출
        PayApplication.applicationContext.getBean(DeliveryService.class).delivery(delivery);
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOrdererName() {
        return ordererName;
    }

    public void setOrdererName(String ordererName) {
        this.ordererName = ordererName;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }



}
