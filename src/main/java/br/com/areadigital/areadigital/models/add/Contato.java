package br.com.areadigital.areadigital.models.add;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@Embeddable
@NoArgsConstructor
public class Contato implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(length = 15)
	private String telefone;

	private String cPNJCPF;

}
