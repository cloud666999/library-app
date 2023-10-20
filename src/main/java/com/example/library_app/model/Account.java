package com.example.library_app.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.OffsetDateTime;
import java.util.*;
import java.util.function.Function;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Builder
@AllArgsConstructor
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Account implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String username;
    String password;
    String name;

    @Enumerated(EnumType.STRING)
    Role role;

//    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
//    @ToString.Exclude
//    @Builder.Default
//    //cascade = CascadeType.ALL : giống như câu query tạo bảng , on delete cascade
//    // khi thay đổi dữ liệu ở bảng cha ---> dữ liệu ở bảng con cũng thay đổi
//
//    //Khi orphanRemoval được đặt là true và bạn loại bỏ một thực thể con ra khỏi
//    // mối quan hệ với thực thể cha, thì thực thể con đó sẽ bị xóa khỏi cơ sở dữ
//    // liệu.
//    Set<Checkout_Details> checkoutDetails = new HashSet<>();

//    @OneToMany(mappedBy = "account",cascade = CascadeType.ALL, orphanRemoval = true)
//    @ToString.Exclude
//    @Builder.Default
//    Set<Review> reviews = new HashSet<>();

    @CreatedDate
    OffsetDateTime createdAt;

    @LastModifiedDate
    OffsetDateTime updatedAt;


    @Builder.Default
    boolean enable = true;

    public <R> R transform(Function<? super Account, ? extends R> func) {
        Objects.requireNonNull(func);
        return func.apply(this);
    }


    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy hibernateProxy? hibernateProxy.getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy hibernateProxy? hibernateProxy.getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        var that = (Account) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy hp ? hp.getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // dùng cho việc phân quyền
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public boolean isAccountNonExpired() {
        // ban đầu là false
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // ban đầu là false
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // ban đầu là false
        return true;
    }

    @Override
    public boolean isEnabled() {
        // ban đầu là false
//        return true;
        return enable;
    }
}
