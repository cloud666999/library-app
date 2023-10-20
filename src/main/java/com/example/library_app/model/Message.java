package com.example.library_app.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Objects;
import java.util.function.Function;

@Table(name = "message")
@Entity
@Builder
@EntityListeners(AuditingEntityListener.class)
@AllArgsConstructor
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="user_email")
    private String userEmail;

    @Column(name="title")
    private String title;

    @Column(name="question")
    private String question;

    @OneToOne
    @JoinColumn(name = "book_id")
    Book book;

    @Column(name="response")
    private String response;

    @Column(name="closed")
    private boolean closed;

    public <R> R transform(Function<? super Message, ? extends R> func) {
        return func.apply(this);
    }

    // mục đích của hàm này là để tối ưu việc so sánh
    // giả sử nếu 2 object getId() được 2 id  khác nhau --> nó sẽ ko cần
    // compare các trường còn lại
    // nếu là 2 id khác nhau nó sẽ tự hiểu đó là 2 object khác nhau
    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy hibernateProxy? hibernateProxy.getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy hibernateProxy? hibernateProxy.getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Message that = (Message) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy hibernateProxy? hibernateProxy.getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
