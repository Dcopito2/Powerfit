package com.nocountry.powerfit.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cart")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @OneToMany(mappedBy = "cart")
//    private List<Product> products;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "product_cart", joinColumns = @JoinColumn(name = "cart_id"),
            inverseJoinColumns = @JoinColumn(name = "products_id"))
    private List<Product> products = new ArrayList<>();

    @OneToOne(mappedBy = "cart")
    private User user;

    @OneToOne
    private Bill bill;

    @Column(name = "amount", nullable = false, updatable = true)
    private Double amount = 0.0;

    @Column(name = "quantity", nullable = false, updatable = true)
    private Integer quantity = 0;

    public void addProduct(Product product) {
        this.products.add(product);
        quantity += 1;
        amount += product.getPrice();
    }

    public boolean removeProduct(Product product) {
        quantity -= 1;
        amount -= product.getPrice();
        return (this.products.remove(product));
    }

}
