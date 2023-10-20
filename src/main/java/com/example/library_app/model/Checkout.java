package com.example.library_app.model;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.OffsetDateTime;
import java.util.*;
import java.util.function.Function;

@Table(name = "checkout")
@Entity
@Builder
@EntityListeners(AuditingEntityListener.class)
@AllArgsConstructor
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Checkout {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;

    @OneToMany(mappedBy = "checkout",cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    @Builder.Default
    Set<Checkout_Details> checkoutDetails = new HashSet<>();

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    Account account;

    @Column(name = "checkout_date")
    @CreatedDate
    OffsetDateTime checkoutDate;

    @Column(name = "returned_date")
    @CreatedDate
    OffsetDateTime returnDate;


    @CreatedDate
    OffsetDateTime createdAt;

    @LastModifiedDate
    OffsetDateTime updatedAt;


    public <R> R transform(Function<? super Checkout, ? extends R> func) {
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
        Checkout that = (Checkout) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy hibernateProxy? hibernateProxy.getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
