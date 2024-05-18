package com.spring.batch.demo;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;


@Configuration
@RequiredArgsConstructor
public class BatchConfig {

    @Bean
    public Step step1(JobRepository jobRepository, PlatformTransactionManager transactionManager){
        return new StepBuilder("step_one", jobRepository)
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                        System.out.println("STEP1 EXECUTED");
                        return RepeatStatus.FINISHED;
                    }
                }, transactionManager)
                .build();
    }

    @Bean
    public Step step2(JobRepository jobRepository, PlatformTransactionManager transactionManager) throws Exception {

        return new StepBuilder("step_two", jobRepository)
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                        System.out.println("STEP2 EXECUTED");
                        boolean isTrue = true;
                        if(isTrue){
                            throw new Exception("Exception occured!!");
                        }
                        return RepeatStatus.FINISHED;
                    }
                }, transactionManager)
                .build();
    }

    @Bean
    public Job job1(JobRepository jobRepository, PlatformTransactionManager transactionManager){
        return new JobBuilder("job1", jobRepository).preventRestart().start(step1(jobRepository, transactionManager))
                .next(step2(jobRepository, transactionManager)).build();
    }

}
