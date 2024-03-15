package br.com.areadigital.areadigital.models.add;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@Embeddable
@NoArgsConstructor
public class Endereco implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(length = 80)
	private String rua;

	@Column(length = 10)
	private String numero;

	@Column(length = 80)
	private String bairro;

	@Column(length = 10)
	private String cep;

}
