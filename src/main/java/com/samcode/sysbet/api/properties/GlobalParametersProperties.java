package com.samcode.sysbet.api.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "global.parameters")
public class GlobalParametersProperties {
	
	private Double comissionRate;
	private Integer scale;
	private String reportPath;
	
	public Double getComissionRate() {
		return comissionRate;
	}
	
	public void setComissionRate(Double comissionRate) {
		this.comissionRate = comissionRate;
	}
	
	public Integer getScale() {
		return scale;
	}
	
	public void setScale(Integer scale) {
		this.scale = scale;
	}

	public String getReportPath() {
		return reportPath;
	}

	public void setReportPath(String reportPath) {
		this.reportPath = reportPath;
	}
  
}
