package skypro.socks.models;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;


@Entity
@Data
public class Socks {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Цвет не должен быть пустым")
    private String color;
    @Min(value = 1,message = "Минимальное значение 1")
    @Max(value = 100,message = "Максимальное значение 100")
    @Column(name = "cottonpart")
    private Integer cottonPart;
    @Positive(message = "Значение должно быть положительным")
    private Integer quantity;

}
