package com.example.springbatch.part2;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class JobRunner implements ApplicationRunner {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job job;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        JobParameters jobParameter = new JobParametersBuilder()
                        .addString("name", "user2")
                        .toJobParameters();
        // BATCH_JOB_EXECUTION_PARAMS : 해당 테이블에 저

        jobLauncher.run(job, jobParameter);
        // 해당 Job 을 한번더 실행하면 동일한 JobInstance 를 뱉기 때문에 예외 발생
        // 따라서 JobParameter 를 다르게 해야한다.
    }
}
