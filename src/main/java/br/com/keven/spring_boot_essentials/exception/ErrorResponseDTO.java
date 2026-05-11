package br.com.keven.spring_boot_essentials.exception;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class ErrorResponseDTO {
    private String message;
    private Integer status;
}
