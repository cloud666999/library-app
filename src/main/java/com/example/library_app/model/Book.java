package com.example.library_app.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;

@Table(name = "book")
@Entity
@Builder
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;

    @Column(name = "name", unique = true)
    String name;

//    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
//    @ToString.Exclude
//    @Builder.Default
//    Set<Review> reviews = new HashSet<>();


    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    @Builder.Default
    Set<Checkout_Details>  checkoutDetails = new HashSet<>();

    @Column(name = "author")
    String author;


    @Column(name = "description")
    String description;

//    @OneToOne(mappedBy = "book",cascade = CascadeType.ALL)
//    @JoinColumn(name = "message_id")
//    Message message;

    @Column(name = "copies")
    Integer copies;

    @Column(name = "copies_available")
    Integer copies_available;

    @Column(name = "category")
    String category;

    @Column(name = "img")
    String img;


    @CreatedDate
    OffsetDateTime createdAt;

    @LastModifiedDate
    OffsetDateTime updatedAt;




    public <R> R transform(Function<? super Book, ? extends R> func) {
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
        Book that = (Book) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy hibernateProxy? hibernateProxy.getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }

}
