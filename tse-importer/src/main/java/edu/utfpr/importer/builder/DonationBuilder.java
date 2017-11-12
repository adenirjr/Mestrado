package edu.utfpr.importer.builder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;

import edu.utfpr.importer.model.Candidate;
import edu.utfpr.importer.model.CandidateId;
import edu.utfpr.importer.model.Corporate;
import edu.utfpr.importer.model.Donation;
import edu.utfpr.importer.model.Individual;

public class DonationBuilder {

	private Map<String, String> attributes;
	private Long year;

	public DonationBuilder setAttributes(final Map<String, String> keyValue) {
		this.attributes = keyValue;
		return this;
	}
	
	public DonationBuilder setYear(final Long year) {
		this.year = year;
		return this;
	}

	public Donation build() {
		final SimpleDateFormat format = new SimpleDateFormat("dd/MM/YYYYHH:mm:ss");
		final SimpleDateFormat format2 = new SimpleDateFormat("dd/MM/YYYY");
		final Donation donation = new Donation();

		donation.setCandidate(new CandidateBuilder().build());
		
		donation.setCNAE(attributes.get("Cod setor econômico do doador"));
		donation.setIncomeDesc(attributes.get("Descricao da receita") == null ? attributes.get("Descrição da receita") : attributes.get("Descricao da receita"));
		donation.setIncomeFormat(attributes.get("Especie recurso") == null ? attributes.get("Espécie recurso") : attributes.get("Especie recurso"));
		donation.setIncomeSource(attributes.get("Fonte recurso"));
		donation.setIncomeType(attributes.get("Tipo receita"));
		donation.setIncomeValue(attributes.get("Valor receita"));
		donation.setParty(attributes.get("Número partido doador"));
		donation.setState(attributes.get("Sigla UE doador"));
		donation.setTransactionNumber(attributes.get("Numero do documento") == null ? attributes.get("Número do documento") : attributes.get("Numero do documento"));
		
		final String documentNumber = attributes.get("CPF/CNPJ do doador") == null ? "" : attributes.get("CPF/CNPJ do doador");
		
		if(!"#NULO".equals(documentNumber)) {
			if(documentNumber.length() == 11) {
				donation.setDonator(new Individual(documentNumber, attributes.get("Nome do doador")));
			} else {
				donation.setDonator(new Corporate(documentNumber, attributes.get("Nome do doador")));
			}
		}
		
		try {
			donation.setDonationDate(attributes.get("Data da receita") == null ? null : format.parse(attributes.get("Data da receita")));
		} catch (ParseException e) {
			try {
				donation.setDonationDate(attributes.get("Data da receita") == null ? null : format2.parse(attributes.get("Data da receita")));
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
		}
		
		return donation;
	}

	private class CandidateBuilder {

		public Candidate build() {
			final SimpleDateFormat format = new SimpleDateFormat("dd/MM/YYYYHH:mm:ss");
			final SimpleDateFormat format2 = new SimpleDateFormat("dd/MM/YYYY");
			
			final Candidate candidate = new Candidate();
			final CandidateId id = new CandidateId();
			
			id.setIndividual(new Individual(attributes.get("CPF do candidato"), attributes.get("Nome candidato")));
			id.setYear(year);
			
			candidate.setId(id);
			candidate.setCandidateCode(attributes.get("Numero candidato") == null ? attributes.get("Número candidato") : attributes.get("Numero candidato"));
			candidate.setCandidateSeq(attributes.get("Sequencial Candidato"));
			candidate.setCity(attributes.get("Nome da UE"));
			candidate.setState(attributes.get("UF"));
			if(attributes.get("CNPJ Prestador Conta") != null && !"#NULO".equals(attributes.get("CNPJ Prestador Conta"))) {
				candidate.setCorporate(new Corporate(attributes.get("CNPJ Prestador Conta"), null));
			}
			candidate.setElectionCode(attributes.get("Cód. Eleição"));
			candidate.setElectionDesc(attributes.get("Desc. Eleição"));
			candidate.setParty(attributes.get("Sigla  Partido"));
			candidate.setRole(attributes.get("Cargo"));
			
			if(attributes.get("CPF do vice/suplente") != null && !"#NULO".equals(attributes.get("CPF do vice/suplente"))) {
				candidate.setVice(new Individual(attributes.get("CPF do vice/suplente"), ""));
			}
			
			try {
				candidate.setCreationDateTime(attributes.get("Data e hora") == null ? null : format.parse(attributes.get("Data e hora")));
			} catch (ParseException e) {
				try {
					candidate.setCreationDateTime(attributes.get("Data e hora") == null ? null : format2.parse(attributes.get("Data e hora")));
				} catch (ParseException e1) {
					e1.printStackTrace();
				}
			}
			
			return candidate;
		}
	}
}
