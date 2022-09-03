package com.springbatch.bdremotepartitioner.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.integration.config.annotation.EnableBatchIntegration;
import org.springframework.batch.integration.partition.RemotePartitioningManagerStepBuilderFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.integration.channel.DirectChannel;

@Profile("manager")
@Configuration
@EnableBatchProcessing
@EnableBatchIntegration
public class ManagerConfig {

    private static final int GRID_SIZE = 2;

    private final JobBuilderFactory jobBuilderFactory;
    private final RemotePartitioningManagerStepBuilderFactory remotePartitioningManagerStepBuilderFactory;

    public ManagerConfig(
        final JobBuilderFactory jobBuilderFactory,
        final RemotePartitioningManagerStepBuilderFactory remotePartitioningManagerStepBuilderFactory) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.remotePartitioningManagerStepBuilderFactory = remotePartitioningManagerStepBuilderFactory;
    }

    @Bean
    public Job remotePartitioningJob(
        @Qualifier("migrarPessoaStep") Step migrarPessoaStep,
        @Qualifier("migrarDadosBancariosStep") Step migrarDadosBancariosStep) {
        return jobBuilderFactory.get("remotePartitioningJob")
            .start(migrarPessoaStep)
            .next(migrarDadosBancariosStep)
            .incrementer(new RunIdIncrementer())
            .build();
    }

    @Bean
    public Step migrarPessoaStep(
        @Qualifier("pessoaPartitioner") Partitioner pessoaPartitioner) {
        return remotePartitioningManagerStepBuilderFactory.get("migrarPessoaStep")
            .partitioner("migrarPessoaStep.manager", pessoaPartitioner)
            .gridSize(GRID_SIZE)
            .outputChannel(requests())
            .build();
    }

    @Bean
    public Step migrarDadosBancariosStep(
        @Qualifier("dadosBancariosPartitioner") Partitioner dadosBancariosPartitioner) {
        return remotePartitioningManagerStepBuilderFactory.get("migrarDadosBancariosStep")
            .partitioner("migrarDadosBancariosStep.manager", dadosBancariosPartitioner)
            .gridSize(GRID_SIZE)
            .outputChannel(requests())
            .build();
    }

    @Bean
    private DirectChannel requests() {
        return new DirectChannel();
    }

}
