package pl.dors.radek.followme.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "USER_STATUS")
public class UserStatus implements Serializable {

    private Long id;

    @Column(name = "STATUS")
    private int status;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Right getRight() {
        return Right.parse(this.status);
    }

    public void setRight(Right right) {
        this.status = right.getValue();
    }

    public enum Right {
        SENT(1), RECEIVED(2), ACTIVE(3);

        private int value;

        Right(int value) {
            this.value = value;
        }

        public static Right parse(int id) {
            Right right = null;
            for (Right item : Right.values()) {
                if (item.getValue() == id) {
                    right = item;
                    break;
                }
            }
            return right;
        }

        public int getValue() {
            return value;
        }
    }

}