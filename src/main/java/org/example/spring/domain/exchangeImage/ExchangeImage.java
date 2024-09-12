package org.example.spring.domain.exchangeImage;

import java.sql.Timestamp;

import org.example.spring.domain.exchange.Exchange;
import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "exchange_image")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ExchangeImage {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "exchange_image_id")
	private Long id;

	@JoinColumn(name = "exchange_id", nullable = false)
	@ManyToOne(fetch = FetchType.LAZY)
	private Exchange exchange;

	@Column(name = "url", nullable = false)
	private String url;

	@Column(name = "created_at", nullable = false, updatable = false)
	@CreationTimestamp
	private Timestamp createdAt;

	public void associateExchange(Exchange exchange) {
		this.exchange = exchange;
	}

	public void disassociateExchange() {
		this.exchange = null;
	}
}
