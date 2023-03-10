package ru.vovai.entity;


import lombok.*;

import javax.persistence.*;

@Setter
@Getter
@EqualsAndHashCode(exclude = "id")
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "binary_content")
public class BinaryContent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private byte[] fileAsArrayOfBytes;
}
