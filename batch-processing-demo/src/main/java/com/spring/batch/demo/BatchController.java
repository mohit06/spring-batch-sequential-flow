package com.spring.batch.demo;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BatchController {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    @Qualifier("job1")
    private Job job;

    @PostMapping("/job1/{param}")
    public void startJob1(@PathVariable("param") String param) throws Exception{
        JobParameters params = new JobParametersBuilder().addString("PARAM",param).toJobParameters();
        jobLauncher.run(job,params);
    }
}
