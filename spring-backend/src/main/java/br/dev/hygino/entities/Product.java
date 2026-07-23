package br.dev.hygino.entities;

import java.time.LocalDateTime;
import java.util.Objects;

import br.dev.hygino.enums.Category;
import br.dev.hygino.enums.Color;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "tb_product")
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Size(min = 3, max = 100)
	private String description;

	@NotBlank
	@Size(min = 3, max = 100)
	private String brand;

	@NotNull
	@Enumerated(EnumType.STRING)
	private Color color;

	@NotNull
	@Enumerated(EnumType.STRING)
	private Category category;

	@NotBlank
	@Size(min = 1, max = 10)
	private String size;

	@NotNull
	@Positive
	private Double price;

	@PositiveOrZero
	@NotNull
	private Integer amount;

	private String imageUrl;

	private LocalDateTime createdAt = LocalDateTime.now();
	private LocalDateTime updatedAt = LocalDateTime.now();

	public Product() {
	}

	public Product(Long id, String description, String brand, Color color, Category category, String size, Double price,
			Integer amount, String imageUrl) {
		this.id = id;
		this.description = description;
		this.brand = brand;
		this.color = color;
		this.category = category;
		this.size = size;
		this.price = price;
		this.amount = amount;
		this.imageUrl = imageUrl;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 23 * hash + Objects.hashCode(this.id);
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Product other = (Product) obj;
		return Objects.equals(this.id, other.id);
	}

	@Override
	public String toString() {
		return "Pant{" + "id=" + id + ", brand=" + brand + ", color=" + color + ", size=" + size + ", price=" + price
				+ '}';
	}
}
