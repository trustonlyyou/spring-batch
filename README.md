# [Spring Batch Study]


## Part1 Spring Batch Start 

### 1.1 Hello Spring Batch 시작하기

1. @Configuration 선언
    - 하나의 배치 Job 을 정의하고 빈 설정
2. JobBuilderFactory
    - Job을 생성하는 빌더 팩토리
3. StepBuilderFactory
    - Step 을 생성하는 빌더 팩토리
4. Job
    - helloJob 이름으로 Job 생성
5. Step
    - helloStep 이름으로 Step 생성
6. tasklet
    - Step 안에서 단일 태스크로 수행되는 로직 구현
7. Job 구동 -> Step 을 실행 -> Tasklet 을 실행

JOB - 일, 일감 <br>
STEP - 일의 항목 <br> 
단계 TASKLET - 작업 내용


정리 : 하나의 Job 은 여러개의 Step 으로 구성되어 있다. 하나의 Step 은 Tasklet(작업 내용) 을 가지고 있다.

