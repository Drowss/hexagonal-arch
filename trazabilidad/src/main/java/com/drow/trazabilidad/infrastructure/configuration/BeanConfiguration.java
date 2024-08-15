package com.drow.trazabilidad.infrastructure.configuration;

import com.drow.trazabilidad.application.mapper.TraceabilityMapper;
import com.drow.trazabilidad.domain.api.ITraceabilityServicePort;
import com.drow.trazabilidad.domain.spi.ITraceabilityPersistencePort;
import com.drow.trazabilidad.domain.usecase.TraceabilityUseCase;
import com.drow.trazabilidad.infrastructure.adapter.TraceabilityJpaAdapter;
import com.drow.trazabilidad.infrastructure.out.mapper.ITraceabilityEntityMapper;
import com.drow.trazabilidad.infrastructure.out.repository.ITraceabilityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {

    private final ITraceabilityRepository traceabilityRepository;
    private final ITraceabilityEntityMapper traceabilityEntityMapper;

    @Bean
    public ITraceabilityServicePort traceabilityServicePort() {
        return new TraceabilityUseCase(traceabilityPersistencePort());
    }

    @Bean
    public ITraceabilityPersistencePort traceabilityPersistencePort() {
        return new TraceabilityJpaAdapter(traceabilityRepository, traceabilityEntityMapper);
    }
}
